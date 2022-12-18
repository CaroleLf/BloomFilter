
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Benchmark {

    // Create a bloom filter and configure it with the desired settings
    private static BloomArrayList bloomArrayList = new BloomArrayList(100000, 1);
    private static BloomLinkedList LinkedList = new BloomLinkedList(100000, 4);
    private static BloomArray bloomArray = new BloomArray(100000, 4);

    private static Random r = new Random();

    private static File file ;

    public static void main(String[] args) throws IOException {
        file = new File("result.txt");
        if (file.exists()) {
            file.delete();
        }
        System.out.println("Measure False Positive Rate ArrayList : ");
        for(int i = 1;i<5;i++){
            measureFalsePositiveRateArrayList(1,i);
            measureFalsePositiveRateArrayList(5,i);
            measureFalsePositiveRateArrayList(10,i);
            measureFalsePositiveRateArrayList(25,i);
        }

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

    }


    private static void generateResultExcel(String data) throws IOException {
        BufferedWriter sortie = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
        sortie.write(data + "\n");
        sortie.close();
        System.out.println("Ajout fini!");

    }



    private static void measureFalsePositiveRateArrayList(double percent,int k) throws IOException {
            BloomArrayList array = new BloomArrayList(100000, k);
            int numItems = (int) ((array.getSize() * percent) / 100);
            System.out.println("Nombre d'élements ajoutés par rapport à " + array.getSize() + " / " + numItems  +"avec k function :" + k);
            // Generate a random set of items to insert into the bloom filter
            Set<String> items = generateRandomItems(null, (int) numItems);
            // Insert the items into the bloom filter
            for (String item : items) {
                array.add(item);
            }
            // Generate a large number of items that are not in the bloom filter
            int numQueries = 10000;
            Set<String> queries = generateRandomItems(items, (int) numQueries);
            // Query the items from the bloom filter
            int falsePositives = 0;
            for (String query : queries) {
                if (array.contains(query)) {
                    falsePositives++;
                }
            }

            // Calculate the false positive rate
            double falsePositiveRate = (double) falsePositives / numQueries;
            // Record the results
            System.out.println("Pourcentage ajouté: " + percent);
            System.out.println("False positive rate: " + falsePositiveRate*100);
            generateResultExcel("Taux de faux positive pour : " + array.getNumHashFunctions()  + " function(s) de hash, taux : " + Double.toString(falsePositiveRate));
        }




    private static Set<String> generateRandomItems(Set<String> existant,int numberQueries) {
        Random rand = new Random();
        HashSet<String> set = new HashSet<>();
            int j = 1;
            while(j<numberQueries){
                String str="";
                for(int i = 0 ; i < 10 ; i++){
                    char c = (char)(rand.nextInt(26) + 97);
                    str += c;
                }
                if(existant!=null){
                    if(!existant.contains(str)) {
                        set.add(str);
                        j++;

                    }
                    else{
                        System.out.println(j);
                    }
                }
                else{
                    set.add(str);
                    j++;
                }
        }
        return set;
    }

    private static void measureInsertTimeArrayList(int numItems) {

        Set<String> items = generateRandomItems(null,numItems);
        long startTime = System.nanoTime();
        for(String item : items){
            bloomArrayList.add(item);
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        System.out.println("Insert time ArrayList: " + elapsedTime/1000000 + "ms, for " + numItems + " items");
    }

    private static void measureQueryTimeArrayList(int numItems) {
            // measure the time it takes to query the list
            Set<String> items = generateRandomItems(null,numItems);
            long startTime = System.nanoTime();
            for (String item : items) {
                bloomArrayList.contains(item);
            }
            long endTime = System.nanoTime();
            long time =  endTime - startTime;
            System.out.println("Query time ArrayList: " + time/1000000 + "ms for " + numItems + " items");
        }

    private static void measureInsertTimeLinked(int numItems) {

        Set<String> items = generateRandomItems(null,numItems);
        long startTime = System.nanoTime();
        for(String item : items){
            LinkedList.add(item);
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        System.out.println("Insert time LinkedList: " + elapsedTime/1000000 + "ms, for " + numItems + " items");
    }
    private static void measureQueryTimeLinked(int numItems) {
        // measure the time it takes to query the list
        Set<String> items = generateRandomItems(null,numItems);
        long startTime = System.nanoTime();
        for (String item : items) {
            LinkedList.contains(item);
        }
        long endTime = System.nanoTime();
        long time =  endTime - startTime;
        System.out.println("Query time LinkedList: " + time/1000000 + "ms for " + numItems + " items");
    }

    private static void measureInsertTimeArray(int numItems) {
        Set<String> items = generateRandomItems(null,numItems);
        long startTime = System.nanoTime();
        for(String item : items){
            bloomArray.add(item);
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        System.out.println("Insert time Array: " + elapsedTime/1000000 + "ms, for " + numItems + " items");
    }
    private static void measureQueryTimeArray(int numItems) {
        // measure the time it takes to query the list
        Set<String> items = generateRandomItems(null,numItems);
        long startTime = System.nanoTime();
        for (String item : items) {
            bloomArray.contains(item);
        }
        long endTime = System.nanoTime();
        long time =  endTime - startTime;

        System.out.println("Contains time Array: " + time/1000000 + "ms for " + numItems + " items");
    }
    }


