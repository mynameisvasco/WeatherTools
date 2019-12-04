package probability;

import java.util.BitSet;
import org.apache.commons.codec.digest.MurmurHash3;

public class BloomFilter 
{
	private int n; // number of position
	private double p; // your acceptable false positive rate {0..1} (e.g. 0.01 → 1%)
	private int m; // number of bits
	private int k; // number of hash functions
	private int[] seeds;
	private BitSet array;
	
	public BloomFilter(int n, double p)
	{
		this.n = n;
		this.p = p;
		this.m = (int) Math.abs(Math.ceil((this.n * Math.log(this.p) / Math.log(1/Math.pow(2,  Math.log(2))))));
	    k = (int) Math.round(Math.log(2) * this.m / this.n); //Best K
		this.array = new BitSet(this.m);
		this.seeds = generateSeeds();
	}
	
	public <T> int getPosition(T element, int seed)
	{
		byte[] elementBytes = element.toString().getBytes();
		int index = MurmurHash3.hash32(elementBytes, 0, elementBytes.length, seed);
		
		return Math.abs(index % this.m);
	}
	
	public <T> void add(T element)
	{
		for(int i = 0; i < this.k; i++)
		{			
			this.array.set(getPosition(element, this.seeds[i]), true);
		}
	}
	
	public <T> boolean contains(T element)
	{
		for(int i = 0; i < this.k; i++)
		{			
			if(!this.array.get(getPosition(element, this.seeds[i])))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public int[] generateSeeds()
	{
		int[] seeds = new int[this.k];
		
		for(int i = 0; i < this.k; i++)
		{
			seeds[i] = (int) (Math.random() * 10007) + 1 ;
		}

		return seeds;
	}
}


