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
		BloomFilter bf = new BloomFilter(dataset.getStations().size(), 0.01);
		List<Double> list = new ArrayList<Double>();
		
		for(Station s : dataset.getStations())
		{
			for(RegisteredWeather rw : s.getRegisteredWeathers())
			{
				bf.add(rw.getAverageTemperature().getValue());
				list.add(rw.getAverageTemperature().getValue());
			}
		}
		
		double i = 0;
		while(i < 30)
		{
			i = Math.round(i * 10);
			i = i / 10;
			System.out.println(i + "-> Iteration: " + iterationSearch(i, list) + " - Bloom Filter: " + bloomFilterSearch(i, bf));
			System.out.println("----------------------------");
			i += 0.1;
		}
	}
	
	public static boolean iterationSearch(double element, List<Double> dataset)
	{
		long beforeTime = System.currentTimeMillis();
		for(int i = 0; i < dataset.size(); i++)
		{
			if(dataset.get(i) == element)
			{
				System.out.println("Checked after: " + (System.currentTimeMillis() - beforeTime) + "ms (Iterative method)");
				return true;
			}
		}
		System.out.println("Checked after: " + (System.currentTimeMillis() - beforeTime) + "ms (Iterative method)");
		return false;
	}
	
	public static boolean bloomFilterSearch(double element, BloomFilter bf)
	{
		long beforeTime = System.currentTimeMillis();
		boolean contains = bf.contains(element);
		System.out.println("Checked after: " + (System.currentTimeMillis() - beforeTime) + "ms (Bloom filter method)");
		return contains;
	}
}	
