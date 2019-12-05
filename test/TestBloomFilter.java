import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import probability.BloomFilter;

public class TestBloomFilter 
{
	public static int NUM_WEATHERS = 100;
	public static void main(String[] args) throws IOException
	{
		BloomFilter bf = new BloomFilter(NUM_WEATHERS, 0.01);
		List<String> list = new ArrayList<String>();
		
		//Use registered weathers of first station only
		for(int i = 0; i < NUM_WEATHERS; i++)
		{
			String s = generateWeatherHash();
			bf.add(s);
			list.add(s);
		}
	
		//Testing false negative
		int falseNegatives = 0;
		for(String s : list) 
		{
			if(!bf.contains(s))
			{
				falseNegatives++;
			}
		}
		
		//Testing false positives
		int falsePositive = 0;
		
		for(int i = 0; i < 100000; i++)
		{			
			String registeredWeatherToFind = generateWeatherHash();
			boolean iteration = iterationSearch(registeredWeatherToFind, list);
			boolean bloom = bloomFilterSearch(registeredWeatherToFind, bf);
			if(bloom && !iteration)
			{
				falsePositive++;
			}
				
			System.out.println("Iteration: " + iteration + " - Bloom Filter: " + bloom);
		}
		

		System.out.println(falseNegatives + " false negatives found.");
		System.out.println("False positives: " + (double) falsePositive / 100000);
		System.out.println("Neste teste é comparado o modo iterativo, que confima de forma absoluta se o elemento pertence de facto ao conjunto ou não, e o modo bloom filter"
				+ "\nque testa o módulo Bloom Filter desenvolvido, que verica também se o elemente percetence ao mesmo.");
	}
	
	public static String generateWeatherHash()
	{
		int d = 1;
		int m = (int)(Math.random() * ((12 - 1) + 1)) + 1;
		int year = (int)(Math.random() * ((2019 - 2018) + 1)) + 2018;
		double temp = Math.floor((int)(Math.random() * ((30 + 10) + 1)) - 10);
		double rainfall = Math.floor((int)(Math.random() * ((5 + 0) + 1)) - 0);
		
		return d + "#" + m + "#" + year + "#" + rainfall + "#" + temp;
	}
	public static boolean iterationSearch(String element, List<String> dataset)
	{
		for(int i = 0; i < dataset.size(); i++)
		{
			if(dataset.get(i).equals(element))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean bloomFilterSearch(String element, BloomFilter bf)
	{
		boolean contains = bf.contains(element);
		return contains;
	}
}	
