package Benchmark;

import BloomFilter.BloomArray;
import BloomFilter.BloomArrayList;
import BloomFilter.BloomLinkedList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Benchmark {

    private static final BloomArrayList bloomArrayList = new BloomArrayList(1000000, 5);
    private static final BloomLinkedList LinkedList = new BloomLinkedList(1000000, 5);
    private static final BloomArray bloomArray = new BloomArray(1000000, 5);

    static BufferedWriter sortieFalsePositive;
    static BufferedWriter sortieQueryTime;
    static BufferedWriter sortieInsertTime;

    /**
     * Constructeur du benchmark
     * @throws IOException
     */
    public Benchmark() throws IOException {
        File fileFalsePositive = new File("resultFalsePositive.csv");
        File fileQueryTime = new File("resultQueryTime.csv");
        File fileInsertTime = new File("resultInsertTime.csv");
        delete(fileFalsePositive);
        delete(fileQueryTime);
        delete(fileInsertTime);
        sortieQueryTime = new BufferedWriter(new FileWriter(fileQueryTime.getAbsoluteFile(), true));
        sortieInsertTime= new BufferedWriter(new FileWriter(fileInsertTime.getAbsoluteFile(), true));
        sortieFalsePositive = new BufferedWriter(new FileWriter(fileFalsePositive.getAbsoluteFile(), true));
        sortieFalsePositive.append("k");
        sortieFalsePositive.append(',');
        sortieFalsePositive.append("Valeurs");
        sortieFalsePositive.append(',');
        sortieFalsePositive.append("Percent");
        sortieFalsePositive.append("\n");
        sortieInsertTime.append("Type");
        sortieInsertTime.append(',');
        sortieInsertTime.append("nombreAjouts");
        sortieInsertTime.append(',');
        sortieInsertTime.append("Valeurs");
        sortieInsertTime.append("\n");
        sortieQueryTime.append("Type");
        sortieQueryTime.append(',');
        sortieQueryTime.append("nombreAjouts");
        sortieQueryTime.append(',');
        sortieQueryTime.append("temps");
        sortieQueryTime.append("\n");
    }

    public static void main(String[] args) throws IOException {
        Benchmark b = new Benchmark();
        System.out.println("Measure False Positive Rate ArrayList : ");
        for(int i = 1;i<=5;i++){
            measureFalsePositiveRateArrayList(1,i);
            measureFalsePositiveRateArrayList(5,i);
            measureFalsePositiveRateArrayList(10,i);
            measureFalsePositiveRateArrayList(15,i);
            measureFalsePositiveRateArrayList(20,i);
            measureFalsePositiveRateArrayList(40,i);
            measureFalsePositiveRateArrayList(50,i);
            measureFalsePositiveRateArrayList(80,i);
        }
        sortieFalsePositive.close();
        System.out.println("Measure Insert time ArrayList : ");


        int valInitial = 10;
        for (int i = 0; i < 4; i++) {
            valInitial = 10 * valInitial;
            measureInsertTimeList(bloomArrayList.getName(),valInitial);
        }
        valInitial = 10;
        for (int i = 0; i < 4; i++) {
            valInitial = 10 * valInitial;
            measureQueryTimeList(bloomArrayList.getName(),valInitial);
        }
        valInitial = 10;
        for (int i = 0; i < 4; i++) {
            valInitial = 10 * valInitial;
            measureInsertTimeList(LinkedList.getName(),valInitial);
        }
        valInitial = 10;
        for (int i = 0; i < 4; i++) {
            valInitial = 10 * valInitial;
            measureQueryTimeList(LinkedList.getName(),valInitial);
        }
        valInitial = 10;
        for (int i = 0; i < 4; i++) {
            valInitial = 10 * valInitial;
            measureInsertTimeArray(valInitial);
        }
        valInitial = 10;
        for (int i = 0; i < 4; i++) {
            valInitial = 10 * valInitial;
            measureQueryTimeArray(valInitial);
        }
        sortieQueryTime.close();
        sortieInsertTime.close();
    }

    /**
     * Method that evaluates the false positive
     * rate in a file with the number of hash functions
     * used and the percentage added
     * @param k number hash functions
     * @param valeurs rateFalsePositive
     * @param percent percent added
     * @throws IOException
     */
    private static void generateResultExcelFalsePositive( int k, double valeurs, double percent ) throws IOException {
        sortieFalsePositive.write( k + " , " + valeurs +  " , "  + percent + "\n");

    }
    /**
     * Method that measures the addition time for a certain number added     * @param type string of the type of bloom filter
     * @param temps time for the Insertion
     * @param nbAjouts number add in the filter
     * @throws IOException
     */
    private static void generateResultExcelTime(String type, double temps, int nbAjouts) throws IOException {
        sortieInsertTime.write( type + "," + nbAjouts  + " , " +temps/1000000 +  "\n");
    }

    /**
     * Method that measures the addition time for a certain number search in the filter
     ** @param type string of the type of bloom filter
     * @param temps time for the Insertion
     * @param nbAjouts number add in the filter
     * @throws IOException
     */

    private static void generateResultExcelTimeQuery( String type,double temps, int nbAjouts) throws IOException {
        sortieQueryTime.write( type + "," + nbAjouts  + " , " +temps/1000000 +  "\n");
    }

    /**
     * delete the file f if it exists
     * @param f File tested
     */
    private static void delete(File f)
    {
        if (f.exists()) {
            f.delete();
        }
    }

    /**
     * Method mesure the rate of falsePositive for an array List
     * @param percent added
     * @param k number hash functions use
     * @throws IOException
     */
    private static void measureFalsePositiveRateArrayList(double percent,int k) throws IOException {
            BloomArrayList array = new BloomArrayList(1000000, k);
            int numQueries = 100000;
            int numItems = (int) ((array.getSize() * percent) / 100);
            System.out.println("Number of items added compared to " + array.getSize() + " to" + numItems  +" with k function : " + k);
            // Generate a random set of items to insert into the bloom filter
            Set<String> items = generateRandomItems(null, (int) numItems);
            // Insert the items into the bloom filter
            for (String item : items) {
                array.add(item);
            }
            // Generate a large number of items that are not in the bloom filter
            Set<String> queries = generateRandomItems(items, (int) numQueries);
            int falsePositives = 0;
            for (String query : queries) {
                if (array.contains(query)) {
                    falsePositives++;
                }
            }
            // Calculate the false positive rate
            double falsePositiveRate = ((double) falsePositives*100)  / numQueries;
            System.out.println("Percent added: " + percent);
            System.out.println("False positive rate: " + falsePositiveRate);
            // Record the results
            generateResultExcelFalsePositive(k,  falsePositiveRate, percent);
        }


    /**
     * Generate a set of random string
     * @param existant can be null or not, if not null the method look if the new generated string already exists in this set
     * @param numberQueries number of strings I have to add in the set
     * @return a set of random string
     */
    private static Set<String> generateRandomItems(Set<String> existant,int numberQueries) {
        Random rand = new Random();
        HashSet<String> set = new HashSet<>();
            int j = 1;
            while(j<numberQueries){
                StringBuilder str= new StringBuilder();
                for(int i = 0 ; i < 10 ; i++){
                    char c = (char)(rand.nextInt(26) + 97);
                    str.append(c);
                }
                if(existant!=null){
                    if(!existant.contains(str.toString())) {
                        set.add(str.toString());
                        j++;

                    }
                    else{
                        System.out.println(j);
                    }
                }
                else{
                    set.add(str.toString());
                    j++;
                }
        }
        return set;
    }

    /**
     * Measure time to insert numItems elements
     * @param bloomFilName name of the bloom filter to test
     * @param numItems number of items to add
     * @throws IOException
     */
    private static void measureInsertTimeList(String bloomFilName, int numItems) throws IOException {
        Set<String> items = generateRandomItems(null,numItems);
        long startTime = 0;
        if(bloomFilName == "ArrayList"){
            startTime = System.nanoTime();
            for(String item : items){
                bloomArrayList.add(item);
            }
        }
        else if(bloomFilName == "LinkedList"){
            startTime = System.nanoTime();
            for(String item : items){
                LinkedList.add(item);
            }
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        if(bloomFilName == "ArrayList"){
            generateResultExcelTime("ArrayList",elapsedTime,numItems);
            System.out.println("Insert time ArrayList: " + elapsedTime/1000000 + " ms, for " + numItems + " items");
        }
        else if(bloomFilName == "LinkedList") {
            generateResultExcelTime("LinkedList",elapsedTime,numItems);
            System.out.println("Insert time LinkedList: " + elapsedTime/1000000 + " ms, for " + numItems + " items");

        }

        }

    /**
     * Method measure the time of the request to numItems
     * @param bloomFilName name of the bloom filter to test
     * @param numItems number of items to test
     * @throws IOException
     */

    private static void measureQueryTimeList(String bloomFilName, int numItems) throws IOException {
            Set<String> items = generateRandomItems(null,numItems);
            long startTime = 0;
            if(bloomFilName == "ArrayList") {
                startTime = System.nanoTime();
                for (String item : items) {
                    bloomArrayList.contains(item);
                }
                long endTime = System.nanoTime();
                long time =  endTime - startTime;
                generateResultExcelTimeQuery("ArrayList", time,numItems);
                System.out.println("Query time ArrayList: " + time/1000000 + " ms for " + numItems + " items");
            }
            else if(bloomFilName == "LinkedList"){
                startTime = System.nanoTime();
                for (String item : items) {
                    LinkedList.contains(item);
                }
                long endTime = System.nanoTime();
                long time =  endTime - startTime;
                generateResultExcelTimeQuery("LinkedList", time,numItems);
                System.out.println("Query time LinkedList: " + time/1000000 + " ms for " + numItems + " items");
            }


        }



    private static void measureInsertTimeArray(int numItems) throws IOException {
        Set<String> items = generateRandomItems(null,numItems);
        long startTime = System.nanoTime();
        for(String item : items){
            bloomArray.add(item);
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        generateResultExcelTime("Array", elapsedTime,numItems);
        System.out.println("Insert time Array: " + elapsedTime/1000000 + " ms, for " + numItems + " items");
    }

    private static void measureQueryTimeArray(int numItems) throws IOException {
        // measure the time it takes to query the list
        Set<String> items = generateRandomItems(null,numItems);
        long startTime = System.nanoTime();
        for (String item : items) {
            bloomArray.contains(item);
        }

        long endTime = System.nanoTime();
        long time =  endTime - startTime;
        generateResultExcelTimeQuery("Array", time,numItems);
        System.out.println("Contains time Array: " + time/1000000 + " ms for " + numItems + " items");
    }
}


