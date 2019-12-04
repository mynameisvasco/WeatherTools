package probability;

import org.apache.commons.codec.digest.MurmurHash3;

public class MinHash
{
	private int[] seeds;
	private int hashesNum;
	private int[][] signatures;
	
	public MinHash(int hashesNum, int stationsNum)
	{
		this.hashesNum = hashesNum;
		this.seeds = new int[this.hashesNum];
		this.signatures = new int[stationsNum][this.hashesNum];
		this.generateSeeds();
	}
	
	public double similarity(int id1, int id2)
	{

			int[] signatures1 = this.signatures[id1];
			int[] signatures2 = this.signatures[id2];	
			
			int intersection = 0;
			
			for(int i = 0; i < this.hashesNum; i++)
			{
				if(signatures1[i] == signatures2[i])
				{
					intersection++;
				}
			}
			

			return (double) intersection/this.hashesNum;
		
	}
	
	public void initSignatureTable(Double[][] elements)
	{
		for(int i = 0; i < elements.length; i++)
		{
			for(int k = 0; k < this.seeds.length; k++)
			{
				int min = Integer.MAX_VALUE;
				
				for(Double d : elements[i])
				{
					if(d == null) continue;
					int hashValue = hash(d, this.seeds[k]);
					if(hashValue < min)
					{
						min = hashValue;
					}
				}
				
				this.signatures[i][k] = min;
				
			}
			
		}
	}
	
	public int hash(Double element, int seed)
	{
		return Math.abs(MurmurHash3.hash32(element.toString().getBytes(), element.toString().getBytes().length, seed));
	}
	
	private void generateSeeds()
	{
		for(int i = 0; i < this.hashesNum; i++)
		{
			this.seeds[i] = (int) (Math.random() * (Integer.MAX_VALUE - 1));
		}
	}
}