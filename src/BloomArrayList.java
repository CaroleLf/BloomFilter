import java.util.ArrayList;

import static java.lang.Math.abs;

public class BloomArrayList extends Hash implements IBloomFilter {

        private ArrayList<Boolean> array;
        private int size;
        private int numHashFunctions;

        private String name = "ArrayList";




        public BloomArrayList(int size, int nbHash) {

            this.size = size;
            numHashFunctions = nbHash;
            this.array = new ArrayList<>();
            for (int i = 0; i < this.size; i++) {
                array.add(false);
            }



        }

        @Override
        public void add(String element) {
            switch (numHashFunctions) {
                case 1:
                    array.set(super.hashModulo(element, element.length(),array.size()), true);
                    break;
                case 2:
                    array.set(super.hashModulo(element, element.length(),array.size()), true);
                    array.set(super.HashXOR(element, element.length()), true);
                    break;
                case 3:
                    array.set(super.hashModulo(element, element.length(),array.size()), true);
                    array.set(super.HashXOR(element, element.length()), true);
                    array.set(super.additiveHash(element, element.length()), true);
                case 4:
                    array.set(super.hashModulo(element, element.length(),array.size()), true);
                    array.set(super.HashXOR(element, element.length()), true);
                    array.set(super.additiveHash(element, element.length()), true);
                    array.set(super.multiplyShiftHash(element, element.length()), true);

            }
        }

    public void add(Object object) {
        if (object == null) {
            System.err.println("There was an error whilst adding an element to the filter; object can't be null.");
            return;
        }
        for (int hashID=1; hashID<=numHashFunctions; hashID++) {
            array.set(abs(super.hash(object, hashID)) % this.size, true);
        }
    }






    @Override
    public String getName() {
        return name;
    }

    @Override
        public boolean contains(String element) {
                boolean inOrNot = true;
                int nbhash = this.numHashFunctions;
                int hash = super.hashModulo(element, element.length(),array.size());
                if (!array.get(hash) && nbhash>=1) {
                    inOrNot =  false;
                }
                hash = super.HashXOR(element,element.length());
                if (!array.get(hash) && nbhash>=2) {
                    inOrNot =  false;
                }
                hash = super.additiveHash(element,element.length());
                if (!array.get(hash) && nbhash>=3) {
                    inOrNot =  false;
                }
                hash = super.multiplyShiftHash(element,element.length());
                if (!array.get(hash)  && nbhash==4) {
                    inOrNot =  false;
                }
                return inOrNot;
        }

    public int getSize(){
            return size;
    }

    public int getNumHashFunctions(){
            return numHashFunctions;
    }
}
