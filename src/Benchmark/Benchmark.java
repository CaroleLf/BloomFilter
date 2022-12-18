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

        // Measure the time it takes to insert and query items of different sizes for ArrayList
        int valInitial = 10;
        for (int i = 0; i < 4; i++) {
            valInitial = 10 * valInitial;
            measureInsertTimeArrayList(valInitial);
        }
        valInitial = 10;
        for (int i = 0; i < 4; i++) {
            valInitial = 10 * valInitial;
            measureQueryTimeArrayList(valInitial);
        }
        valInitial = 10;
        for (int i = 0; i < 4; i++) {
            valInitial = 10 * valInitial;
            measureInsertTimeLinked(valInitial);
        }
        valInitial = 10;
        for (int i = 0; i < 4; i++) {
            valInitial = 10 * valInitial;
            measureQueryTimeLinked(valInitial);
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

    private static void generateResultExcelFalsePositive( int k, double valeurs, double percent ) throws IOException {
        sortieFalsePositive.write( k + " , " + valeurs +  " , "  + percent + "\n");

    }

    private static void generateResultExcelTime(String type, double temps, int nbAjouts) throws IOException {
        sortieInsertTime.write( type + "," + nbAjouts  + " , " +temps/1000000 +  "\n");
    }

    private static void generateResultExcelTimeQuery( String type,double temps, int nbAjouts) throws IOException {
        sortieQueryTime.write( type + "," + nbAjouts  + " , " +temps/1000000 +  "\n");
    }

    private static void delete(File f)
    {
        if (f.exists()) {
            f.delete();
        }
    }




    private static void measureFalsePositiveRateArrayList(double percent,int k) throws IOException {
            BloomArrayList array = new BloomArrayList(1000000, k);
            int numItems = (int) ((array.getSize() * percent) / 100);
            System.out.println("Nombre d'élements ajoutés par rapport à " + array.getSize() + " / " + numItems  +"avec k function :" + k);
            // Generate a random set of items to insert into the bloom filter
            Set<String> items = generateRandomItems(null, (int) numItems);
            // Insert the items into the bloom filter
            for (String item : items) {
                array.add(item);
            }
            // Generate a large number of items that are not in the bloom filter
            int numQueries = 100000;
            Set<String> queries = generateRandomItems(items, (int) numQueries);
            // Query the items from the bloom filter
            int falsePositives = 0;
            for (String query : queries) {
                if (array.contains(query)) {
                    falsePositives++;
                }
            }

            // Calculate the false positive rate
            double falsePositiveRate = ((double) falsePositives*100)  / numQueries;
            // Record the results
            System.out.println("Pourcentage ajouté: " + percent);
            System.out.println("False positive rate: " + falsePositiveRate);
            generateResultExcelFalsePositive(k,  falsePositiveRate, percent);
        }




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

    private static void measureInsertTimeArrayList(int numItems) throws IOException {

        Set<String> items = generateRandomItems(null,numItems);
        long startTime = System.nanoTime();
        for(String item : items){
            bloomArrayList.add(item);
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        generateResultExcelTime("ArrayList",elapsedTime,numItems);
        System.out.println("Insert time ArrayList: " + elapsedTime/1000000 + " ms, for " + numItems + " items");
    }

    private static void measureQueryTimeArrayList(int numItems) throws IOException {
            Set<String> items = generateRandomItems(null,numItems);
            long startTime = System.nanoTime();
            for (String item : items) {
                bloomArrayList.contains(item);
            }
            long endTime = System.nanoTime();
            long time =  endTime - startTime;
            generateResultExcelTimeQuery("ArrayList", time,numItems);
            System.out.println("Query time ArrayList: " + time/1000000 + " ms for " + numItems + " items");
        }

    private static void measureInsertTimeLinked(int numItems) throws IOException {

        Set<String> items = generateRandomItems(null,numItems);
        long startTime = System.nanoTime();
        for(String item : items){
            LinkedList.add(item);
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        generateResultExcelTime("LinkedList", elapsedTime,numItems);
        System.out.println("Insert time LinkedList: " + elapsedTime/1000000 + " ms, for " + numItems + " items");
    }

    private static void measureQueryTimeLinked(int numItems) throws IOException {
        // measure the time it takes to query the list
        Set<String> items = generateRandomItems(null,numItems);
        long startTime = System.nanoTime();
        for (String item : items) {
            LinkedList.contains(item);
        }
        long endTime = System.nanoTime();
        long time =  endTime - startTime;
        generateResultExcelTimeQuery("LinkedList", time,numItems);
        System.out.println("Query time LinkedList: " + time/1000000 + " ms for " + numItems + " items");
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


