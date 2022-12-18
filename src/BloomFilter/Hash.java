package BloomFilter;

import java.lang.Math;



public class Hash {
    public static int hash1(Object element, int bloomfilterSize) {

       return Math.abs((element.hashCode()+19)%bloomfilterSize);
    }


    int hash2(Object element,int bloomfilterSize) {
        return Math.abs((element.hashCode()+125)%bloomfilterSize);
    }

    public int hash3(Object element, int bloomfilterSize) {
        return Math.abs((element.hashCode()+39)%bloomfilterSize);
    }

    public int hash4(Object element, int bloomfilterSize) {
        return Math.abs((element.hashCode()+90)%bloomfilterSize);
    }

    static public int hash5(Object element, int bloomfilterSize){
        return Math.abs((element.hashCode()+25)%bloomfilterSize);

    }
}
