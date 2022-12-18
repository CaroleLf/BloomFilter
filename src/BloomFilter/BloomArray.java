package BloomFilter;

import java.util.ArrayList;

import static java.util.Objects.hash;

public class BloomArray implements IBloomFilter {

    private boolean[] array;
    private int size;
    private int numHashFunctions;

    private String name = "Array";

    private Hash myHash;

    public BloomArray(int size,int numhash) {
        myHash = new Hash();
        this.size = size;
        this.array = new boolean[size];
        for (int i = 0; i < this.size; i++) {
            array[i] = false;
        }
        numHashFunctions = numhash;



    }

    @Override
    public void add(String element) {
        switch (numHashFunctions) {
            case 1:
                array[myHash.hash1(element, array.length)] = true;
                break;
            case 2:
                array[myHash.hash1(element, array.length)] = true;
                array[myHash.hash2(element, array.length)] = true;

                break;
            case 3:
                array[myHash.hash1(element, array.length)] = true;
                array[myHash.hash2(element, array.length)] = true;
                array[myHash.hash3(element, array.length)] = true;
                break;
            case 4:
                array[myHash.hash1(element, array.length)] = true;
                array[myHash.hash2(element, array.length)] = true;
                array[myHash.hash3(element, array.length)] = true;
                array[myHash.hash4(element, array.length)] = true;
                break;
            case 5:
                array[myHash.hash1(element, array.length)] = true;
                array[myHash.hash2(element, array.length)] = true;
                array[myHash.hash3(element, array.length)] = true;
                array[myHash.hash4(element, array.length)] = true;
                array[myHash.hash5(element, array.length)] = true;
        }

    }

    @Override
    public String getName() {
        return name;
    }



    @Override
    public boolean contains(String element) {
        int nbhash = this.numHashFunctions;
        int hash = myHash.hash1(element, array.length);

        if (array[hash] = false && nbhash>=1) {
            return false;
        }
        hash = myHash.hash2(element,array.length);
        if (array[hash] = false&& nbhash>=2 ) {
            return false;
        }
        hash = myHash.hash3(element,array.length);
        if (array[hash] = false && nbhash>=3) {
            return false;
        }
        hash = myHash.hash4(element,array.length);
        if (array[hash] = false && nbhash>=4) {
            return false;
        }
        hash = myHash.hash5(element,array.length);
        if (array[hash] = false && nbhash>=5) {
            return false;
        }
        return true;
    }

}
