import java.util.ArrayList;
import java.util.LinkedList;

import static java.util.Objects.hash;

public class BloomLinkedList extends Hash implements IBloomFilter {

    private LinkedList<Boolean> linkedList;
    private int size;
    private int numHashFunctions;
    private String name = "LinkedList";


    public BloomLinkedList(int size, int hashFunct) {
        this.size = size;
        this.linkedList = new LinkedList<>();
        for (int i = 0; i < this.size; i++) {
            linkedList.add(false);
        }
        numHashFunctions = hashFunct;


    }

    @Override
    public void add(String element) {
        switch (numHashFunctions) {
            case 1:
                linkedList.set(super.hashModulo(element, element.length(),linkedList.size()), true);
                break;
            case 2:
                linkedList.set(super.hashModulo(element, element.length(),linkedList.size()), true);
                linkedList.set(super.HashXOR(element, element.length()), true);
                break;
            case 3:
                linkedList.set(super.hashModulo(element, element.length(),linkedList.size()), true);
                linkedList.set(super.HashXOR(element, element.length()), true);
                linkedList.set(super.additiveHash(element, element.length()), true);
                break;
            case 4:
                linkedList.set(super.hashModulo(element, element.length(),linkedList.size()), true);
                linkedList.set(super.HashXOR(element, element.length()), true);
                linkedList.set(super.additiveHash(element, element.length()), true);
                linkedList.set(super.multiplyShiftHash(element, element.length()), true);
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
        int hash = super.hashModulo(element, element.length(),linkedList.size());
        if (!linkedList.get(hash) && nbhash >= 1) {
            inOrNot = false;
        }
        hash = super.HashXOR(element, element.length());
        if (!linkedList.get(hash) && nbhash >= 2) {
            inOrNot = false;
        }
        hash = super.additiveHash(element, element.length());
        if (!linkedList.get(hash) && nbhash >= 3) {
            inOrNot = false;
        }
        hash = super.multiplyShiftHash(element, element.length());
        if (!linkedList.get(hash) && nbhash >= 4) {
            inOrNot = false;
        }
        return inOrNot;
    }


}





