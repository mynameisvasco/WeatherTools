package probability;

import java.util.stream.LongStream;

import org.apache.commons.codec.digest.MurmurHash3;

public class MinHash
{

    private int[] seeds;
    private int hashesNum; //numHash: number of hash functions needed 

    public MinHash(int hashes) 
    {
        this.hashesNum = hashes;
        this.seeds = new int[hashesNum];
        generateSeeds();
    }

    public double similarity(double[] values1, double[] values2) {

    	long[] hashedValues1 = hash(values1);
    	long[] hashedValues2 = hash(values2);
    	
    	int intersection = 0;
        
    	for(int i = 0; i < hashedValues1.length; i++)
    	{
    		int k = i; // Because scope in lambda
    		if(LongStream.of(hashedValues2).anyMatch(x -> x == hashedValues1[k]) && hashedValues1[k] != 0)
    		{
    			intersection++;
    		}
    	}
 
    	System.out.println(intersection);
    	
        return (double) (intersection/(hashedValues1.length + hashedValues2.length));
    }

    public long[] hash(double[] values) 
    {
    	long[] hashedValues = new long[values.length];
    	long minValue = Long.MAX_VALUE;
    	int hashPosition = 0;
		for(Double d : values)
		{    
			for(int i = 0; i < this.seeds.length; i++)
			{			
    			long hashedValue = Math.abs(MurmurHash3.hash32(d.toString().getBytes(), d.toString().length(), this.seeds[i]));
    			if(hashedValue < minValue)
    			{
    				minValue = hashedValue;
    				hashedValues[hashPosition] = hashedValue;
    			}
    		}
			hashPosition++;
    	}
		
		return hashedValues;
    }
    
    private void generateSeeds() 
    {
        for (int i = 0; i < hashesNum; i++) 
        {
            seeds[i] = Math.abs((int)(Math.random()*Integer.MAX_VALUE));
        }
    }
}