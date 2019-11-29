import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import files.Dataset;
import probability.BloomFilter;
import weather.RegisteredWeather;
import weather.Station;

public class TestBloomFilter 
{
	public static void main(String[] args) throws IOException
	{
		
		Dataset dataset = new Dataset();
		dataset.importFiles();
		dataset.importStations();
		dataset.importRegisteredWeathers();
		BloomFilter bf = new BloomFilter(1000, 0.01);
		List<String> list = new ArrayList<String>();
		

		//Use registered weathers of first station only
		for(RegisteredWeather rw : dataset.getStations().get(0).getRegisteredWeathers())
		{
			bf.add(rw.generateHash());
			list.add(rw.generateHash());
		}
	
		
		String registeredWeatherToFind = "1/8/2019#0.6#22.8";
		System.out.println("Iteration: " + iterationSearch(registeredWeatherToFind, list) + " - Bloom Filter: " + bloomFilterSearch(registeredWeatherToFind, bf));
	}
	
	public static boolean iterationSearch(String element, List<String> dataset)
	{
		long beforeTime = System.currentTimeMillis();
		for(int i = 0; i < dataset.size(); i++)
		{
			if(dataset.get(i).equals(element))
			{
				System.out.println("Checked after: " + (System.currentTimeMillis() - beforeTime) + "ms (Iterative method)");
				return true;
			}
		}
		System.out.println("Checked after: " + (System.currentTimeMillis() - beforeTime) + "ms (Iterative method)");
		return false;
	}
	
	public static boolean bloomFilterSearch(String element, BloomFilter bf)
	{
		long beforeTime = System.currentTimeMillis();
		boolean contains = bf.contains(element);
		System.out.println("Checked after: " + (System.currentTimeMillis() - beforeTime) + "ms (Bloom filter method)");
		return contains;
	}
}	
