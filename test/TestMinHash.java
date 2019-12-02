import java.io.IOException;
import java.util.Arrays;

import probability.MinHash;

public class TestMinHash 
{
	public static double MIN_SIMILARITY = 0.7;
	public static int NUM_STATIONS = 1000;
	public static void main(String[] args) throws IOException
	{	
		MinHash minHash = new MinHash(10);
		
		double[][] temperatures = new double[NUM_STATIONS][12];
		
		for(int i = 0; i < NUM_STATIONS; i++)
		{
			for(int k = 0; k < 12; k++)
			{
				temperatures[i][k] = randomTemp();
			}
		}
		
		long beforeTime = System.currentTimeMillis();
		
		for(int i = 0; i < temperatures.length; i++)
		{
			for(int k = 0; k < temperatures.length; k++)
			{
				if(i == k) continue;
				double similarity = minHash.similarity(temperatures[i], temperatures[k]);
				if(similarity > MIN_SIMILARITY)
				{					
					System.out.println("—--------------------------------------");
					System.out.println(i + " e " + k + " -> Jaccard: " + similarity);
					System.out.println(Arrays.toString(temperatures[i]) + " | " + Arrays.toString(temperatures[k]));
				}
			}
		}
		
		System.out.println("\n" + NUM_STATIONS + " stations processed in " + ( (System.currentTimeMillis() - beforeTime) * 0.001) + " seconds");
	}
	
	public static double randomTemp()
	{
		double random = (Math.random() * (25 + 0)) - 0;
		random = Math.round(random);
		return random;
	}
}