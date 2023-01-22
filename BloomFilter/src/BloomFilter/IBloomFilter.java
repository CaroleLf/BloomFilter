package BloomFilter;

public interface IBloomFilter {

    public boolean contains(String element);
    public void add(String element);
    public String getName();

}
