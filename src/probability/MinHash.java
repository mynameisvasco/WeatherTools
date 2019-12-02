package probability;

import java.util.ArrayList;
import java.util.List;
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

    public double similarity(double[] values1, double[] values2) 
    {

    	long[] hashedValues1 = hash(values1);
    	long[] hashedValues2 = hash(values2);
    	
    	int intersection = 0;
    	
    	for(int i = 0; i < hashedValues1.length; i++)
    	{
    		int k = i; // Because scope in lambda
    		if(LongStream.of(hashedValues2).anyMatch(x -> x == hashedValues1[k]))
    		{
    			intersection++;
    		}
    	}
    	
    	double similarity = (double) intersection/(findUnion(hashedValues1, hashedValues2).length);
    	similarity = Math.round(similarity * 1000);
    	similarity = similarity / 1000;
    	
        return similarity;
    }

    public long[] hash(double[] values) 
    {
    	long[] hashedValues = new long[values.length];
    	int hashPosition = 0;
		for(Double d : values)
		{    
	    	long minValue = Long.MAX_VALUE;
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
    
    public double[] removeZeros(double[] test) 
    {
        List<Double> list = new ArrayList<Double>();
        
        for(double d : test)
        {
        	if(d != 0) list.add(d);
        }
        
        double[] array = new double[list.size()];
        
        int pos = 0;
        for(Double d : list)
        {
        	array[pos] = d;
        	pos++;
        }
        
        return array;
    }
    
    /* Union of multiple arrays */
	private static long[] findUnion(long[]... arrays) {
		int maxSize = 0;
		int counter = 0;
 
		for (long[] array : arrays)
			maxSize += array.length;
		long[] temp = new long[maxSize];
 
		for (long[] array : arrays)
			for (long i : array)
				if (!findDuplicated(temp, counter, i))
					temp[counter++] = i;
 
		long[] result = new long[counter];
		for (int i = 0; i < counter; i++)
			result[i] = temp[i];
 
		return result;
	}
 
	private static boolean findDuplicated(long[] array, int counter, long value) {
		for (int i = 0; i < counter; i++)
			if (array[i] == value)
				return true;
		return false;
	}
}