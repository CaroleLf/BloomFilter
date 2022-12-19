package BloomFilter;

import java.util.LinkedList;

public class BloomLinkedList implements IBloomFilter {

    private final LinkedList<Boolean> linkedList;
    private final int numHashFunctions;

    private final Hash myHash;


    private final String name = "LinkedList";
    /**
     * Constructor of arrayFilter
     * @param size
     * @param hashFunct
     */
    public BloomLinkedList(int size, int hashFunct) {
        myHash = new Hash();
        this.linkedList = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            linkedList.add(false);
        }
        numHashFunctions = hashFunct;


    }

    /**
     * Method add the element in filter depending on the number of hash functions used
     */
    @Override
    public void add(String element) {
        switch (numHashFunctions) {
            case 1 -> linkedList.set(myHash.hash1(element, linkedList.size()), true);
            case 2 -> {
                linkedList.set(myHash.hash1(element, linkedList.size()), true);
                linkedList.set(myHash.hash2(element, linkedList.size()), true);
            }
            case 3 -> {
                linkedList.set(myHash.hash1(element, linkedList.size()), true);
                linkedList.set(myHash.hash2(element, linkedList.size()), true);
                linkedList.set(myHash.hash3(element, linkedList.size()), true);
            }
            case 4 -> {
                linkedList.set(myHash.hash1(element, linkedList.size()), true);
                linkedList.set(myHash.hash2(element, linkedList.size()), true);
                linkedList.set(myHash.hash3(element, linkedList.size()), true);
                linkedList.set(myHash.hash4(element, linkedList.size()), true);
            }
            case 5 -> {
                linkedList.set(myHash.hash1(element, linkedList.size()), true);
                linkedList.set(myHash.hash2(element, linkedList.size()), true);
                linkedList.set(myHash.hash3(element, linkedList.size()), true);
                linkedList.set(myHash.hash4(element, linkedList.size()), true);
                linkedList.set(myHash.hash5(element, linkedList.size()), true);
            }
        }
    }

    /**
     * Return the name of the filter
     * @return name of the filter
     */
    @Override
    public String getName() {
        String name = "LinkedList";
        return name;
    }

    /**
     * Check if the elements is in the filter depending on the number of hash functions used
     */
    @Override
    public boolean contains(String element) {
        int nbhash = this.numHashFunctions;
        int hash = myHash.hash1(element, linkedList.size());
        if (!linkedList.get(hash) && nbhash >= 1) {
            return false;
        }
        hash = myHash.hash2(element,linkedList.size());
        if (!linkedList.get(hash) && nbhash >= 2) {
            return false;
        }
        hash = myHash.hash3(element, linkedList.size());
        if (!linkedList.get(hash) && nbhash >= 3) {
            return false;
        }
        hash = myHash.hash4(element, linkedList.size());
        if (!linkedList.get(hash) && nbhash >= 4) {
            return false;
        }
        hash = myHash.hash5(element,linkedList.size());
        if (!linkedList.get(hash) && nbhash==5) {
            return false;
        }
        return true;
    }



}





