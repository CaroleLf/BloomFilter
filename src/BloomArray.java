import java.util.ArrayList;

import static java.util.Objects.hash;

public class BloomArray extends Hash implements IBloomFilter {

    private boolean[] array;
    private int size;
    private int numHashFunctions;

    private String name = "Array";

    public BloomArray(int size,int numhash) {

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
                array[super.hashModulo(element, element.length(),array.length)] = true;
                break;
            case 2:
                array[super.hashModulo(element, element.length(),array.length)] = true;
                array[super.HashXOR(element, element.length())] = true;

                break;
            case 3:
                array[super.hashModulo(element, element.length(),array.length)] = true;
                array[super.HashXOR(element, element.length())] = true;
                array[super.additiveHash(element, element.length())] = true;
                break;
            case 4:
                array[super.hashModulo(element, element.length(),array.length)] = true;
                array[super.HashXOR(element, element.length())] = true;
                array[super.additiveHash(element, element.length())] = true;
                array[super.multiplyShiftHash(element, element.length())] = true;
                System.out.println("Modulo" + super.hashModulo(element, element.length(),array.length));
                System.out.println("Modulo" + super.HashXOR(element, element.length()));
                System.out.println("Modulo" + super.additiveHash(element, element.length()));
                System.out.println("Modulo" + super.multiplyShiftHash(element, element.length()));

                break;
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
        int hash = super.hashModulo(element, element.length(),array.length);

        if (array[hash] = false && nbhash>=1) {
            inOrNot =  false;
        }
        hash = super.HashXOR(element,element.length());
        if (array[hash] = false&& nbhash>=2 ) {
            inOrNot =  false;
        }
        hash = super.additiveHash(element,element.length());
        if (array[hash] = false && nbhash>=3) {
            inOrNot =  false;
        }
        hash = super.multiplyShiftHash(element,element.length());
        if (array[hash] = false && nbhash==4) {
            inOrNot =  false;
        }
        return inOrNot;
    }


}
