import java.io.IOException;

import probability.MinHash;

public class TestMinHash 
{
	public static void main(String[] args) throws IOException
	{	
		MinHash minHash = new MinHash(100);
		
		double[][] temperatures = new double[100][12];
		
		for(int i = 0; i < temperatures.length; i++)
		{
			for(int k = 0; k < 12; k++)
			{
				
				temperatures[i][k] = randomTemp();
			}
		}
		
		for(int i = 0; i < temperatures.length; i++)
		{
			for(int k = 0; k < temperatures.length; k++)
			{
				if(i == k) continue;
				System.out.println(i + " e " + k + " -> Jaccard: " + minHash.similarity(temperatures[i], temperatures[k]));
			}
		}
	}
	
	public static double randomTemp()
	{
		double random = (Math.random() * (25 + 0)) - 0;
		random = Math.round(random * 10);
		return random / 10;
	}
}
