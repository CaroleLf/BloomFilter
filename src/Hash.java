public abstract class Hash {
    public static int hashModulo(String element, int size, int bloomfilterSize) {
        int hash = 0;
        for (int i = 0; i < element.length(); i++) {
            hash = (hash * size + element.charAt(i)) % bloomfilterSize;
        }
        return hash;
    }


    int HashXOR(String element, int size) {
        int hash = 0;
        for (int i = 0; i < element.length(); i++) {
            hash = (hash ^ element.charAt(i)) % size;
        }
        return hash;
    }

    public int multiplyShiftHash(String element, int size) {
        final int constant = 31;
        int sum = 0;
        for (char c : element.toCharArray()) {
            sum += (int) c;
        }
        return (sum * constant) >>> (32 - Integer.numberOfLeadingZeros(size));
    }

    public int additiveHash(String element, int size) {
        int sum = 0;
        for (char c : element.toCharArray()) {
            sum += (int) c;
        }
        return sum % size;
    }

    static public int hash(Object o, int n){
        if(o instanceof Integer && (int)o < 0){
            return o.hashCode() * 379 * n;
        }
        return o.hashCode() * n;
    }
}
