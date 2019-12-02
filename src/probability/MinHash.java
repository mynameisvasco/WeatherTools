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
    	long[] tmp = new long[hashedValues1.length];
    	int iTmp = 0;
    	int intersection = 0;
    	
    	for(int i = 0; i < hashedValues1.length; i++)
    	{
    		int k = i; // Because scope in lambda
    		if(LongStream.of(hashedValues2).anyMatch(x -> x == hashedValues1[k]))
    		{
    			if(!LongStream.of(tmp).anyMatch(x -> x == hashedValues1[k]))
    			{
        			intersection++;	
        			tmp[iTmp] = hashedValues1[k];
        			iTmp++;
    			}
    		}
    	}
    	
    	double similarity = (double) intersection/(findUnion(values1, values2).length);
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
	private static double[] findUnion(double[]... arrays) 
	{
		int maxSize = 0;
		int counter = 0;
 
		for (double[] array : arrays)
			maxSize += array.length;
		double[] temp = new double[maxSize];
 
		for (double[] array : arrays)
			for (double i : array)
				if (!findDuplicated(temp, counter, i))
					temp[counter++] = i;
 
		double[] result = new double[counter];
		for (int i = 0; i < counter; i++)
			result[i] = temp[i];
 
		return result;
	}
 
	private static boolean findDuplicated(double[] array, int counter, double value) 
	{
		for (int i = 0; i < counter; i++)
			if (array[i] == value)
				return true;
		return false;
	}
}