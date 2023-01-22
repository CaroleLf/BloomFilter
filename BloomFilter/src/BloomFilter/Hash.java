package BloomFilter;

import java.lang.Math;



public class Hash {
    /**
     * First hash function
     * @param element String to add
     * @param bloomfilterSize size bloomfilter
     * @return
     */
    public static int hash1(Object element, int bloomfilterSize) {

       return Math.abs((element.hashCode()+19)%bloomfilterSize);
    }

    /**
     * Second hash function
     * @param element String to add
     * @param bloomfilterSize size bloomfilter
     * @return
     */
    int hash2(Object element,int bloomfilterSize) {
        return Math.abs((element.hashCode()+125)%bloomfilterSize);
    }
    /**
     * Third hash function
     * @param element String to add
     * @param bloomfilterSize size bloomfilter
     * @return
     */
    public int hash3(Object element, int bloomfilterSize) {
        return Math.abs((element.hashCode()+39)%bloomfilterSize);
    }
    /**
     * Four hash function
     * @param element String to add
     * @param bloomfilterSize size bloomfilter
     * @return
     */
    public int hash4(Object element, int bloomfilterSize) {
        return Math.abs((element.hashCode()+90)%bloomfilterSize);
    }

    /**
     * Five hash function
     * @param element String to add
     * @param bloomfilterSize size bloomfilter
     * @return
     */
    static public int hash5(Object element, int bloomfilterSize){
        return Math.abs((element.hashCode()+25)%bloomfilterSize);

    }
}
