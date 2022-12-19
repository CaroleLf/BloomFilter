package BloomFilter;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class BloomArrayList implements IBloomFilter {

        private ArrayList<Boolean> array;
        private int size;
        private int numHashFunctions;
        private String name = "ArrayList";
        private Hash myHash;


    /**
     * Constructor ArrayList filter
     * @param size
     * @param nbHash
     */
        public BloomArrayList(int size, int nbHash) {
            myHash = new Hash();
            this.size = size;
            numHashFunctions = nbHash;
            this.array = new ArrayList<>();
            for (int i = 0; i < this.size; i++) {
                array.add(false);
            }



        }

        @Override
        /**
         * Method add the element in filter depending on the number of hash functions used
         */
        public void add(String element) {
            switch (numHashFunctions) {
                case 1:
                    array.set(myHash.hash1(element,array.size()), true);

                    break;
                case 2:
                    array.set(myHash.hash1(element, array.size()), true);
                    array.set(myHash.hash2(element, array.size()), true);

                    break;
                case 3:
                    array.set(myHash.hash1(element, array.size()), true);
                    array.set(myHash.hash2(element, array.size()), true);
                    array.set(myHash.hash3(element, array.size()), true);

                    break;
                case 4:
                    array.set(myHash.hash1(element, array.size()), true);
                    array.set(myHash.hash2(element, array.size()), true);
                    array.set(myHash.hash3(element, array.size()), true);
                    array.set(myHash.hash4(element, array.size()), true);
                    break;
                case 5:
                    array.set(myHash.hash1(element, array.size()), true);
                    array.set(myHash.hash2(element, array.size()), true);
                    array.set(myHash.hash3(element, array.size()), true);
                    array.set(myHash.hash4(element, array.size()), true);
                    array.set(myHash.hash5(element, array.size()), true);
            }
        }

    @Override
    public String getName() {
        return name;
    }

    @Override
    /**
     * Check if the elements is in the filter depending on the number of hash functions used
     */
        public boolean contains(String element) {
                int nbhash = this.numHashFunctions;
                int hash = myHash.hash1(element,array.size());
                if (!array.get(hash) && nbhash>=1) {
                    return false;
                }
                hash = myHash.hash2(element,array.size());
                if (!array.get(hash) && nbhash>=2) {
                    return  false;
                }
                hash = myHash.hash3(element,array.size());
                if (!array.get(hash) && nbhash>=3) {
                    return false;
                }
                hash = myHash.hash4(element,array.size());
                if (!array.get(hash) && nbhash>=4) {
                    return false;
                }
                hash = myHash.hash5(element,array.size());
                if (!array.get(hash) && nbhash==5) {
                    return false;
                }
                return true;
        }

    public int getSize(){
            return size;
    }

    public int getNumHashFunctions(){
            return numHashFunctions;
    }
}
