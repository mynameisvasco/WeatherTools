package metrics;

import java.time.LocalDateTime;

public class Date 
{
	public static int[] MONTHS_31 = new int[] {1,3,5,7,8,10,12};
	public static int[] MONTHS_30 = new int[] {4,6,9,11};
	public static String[] MONTHS_NAME = {"January", "February", "March", "April", "May", "June", "July", "August", "October", "November", "December"};
	private int day;
	private int month;
	private int year;
	
	public Date(String date)
	{
		int day = Integer.valueOf(date.split("/")[0]);
		int month = Integer.valueOf(date.split("/")[1]);
		int year = Integer.valueOf(date.split("/")[2]);
		
		assert day > 0 && day < monthNumberOfDays(month, year) : "Dia inválido";
		assert month > 0 && month <= 12 : "Mês inválido";
		assert year >  0 : "Ano inválido";
		
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public Date(int day, int month, int year)
	{
		assert day > 0 && day < monthNumberOfDays(month, year) : "Dia inválido";
		assert month > 0 && month <= 12 : "Mês inválido";
		assert year >  0 : "Ano inválido";
		
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public int getDay()
	{
		return this.day;
	}
	
	public int getMonth()
	{
		return this.month;
	}
	
	public int getYear()
	{
		return this.year;
	}

	public String toString()
	{
		return this.day + "/" + this.month + "/" + this.year;
	}
	
	public static int monthNumberOfDays(int month, int year)
	{
		if(month == 2)
		{
			if(isBissextus(year))
			{
				return 29;
			}
			else
			{
				return 28;
			}
		}
		for(int i = 0; i < MONTHS_31.length; i++)
		{
			if(MONTHS_31[i] == month)
			{
				return 31;
			}
		}
		
		for(int k = 0; k < MONTHS_30.length; k++)
		{
			if(MONTHS_31[k] == month)
			{
				return 30;
			}
		}
		
		return 0;
	}
	
	public static boolean isBissextus(int year)
	{
		if ((year % 400 == 0) || ((year % 4 == 0) && !(year % 100 == 0)))
		{   
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean equals(Date d)
	{
		//Ignoring day becuase this app works with month and year only
		if(this.getMonth() == d.getMonth() && this.getYear() == d.getYear())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Date today()
	{
		LocalDateTime localDate = LocalDateTime.now();

		return new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
	}
	
	public static String[] yearsUntil2070()
	{
		String[] years = new String[71];
		
		for(int i = 0; i < 71; i++)
		{
			if( i < 9)
			{				
				years[i] = "200" + i;
			}
			else
			{
				years[i] = "20" + i;
			}
		}
		
		return years;
	}
	
	public static int getMonthInt(String month)
	{
		for(int i = 0; i < MONTHS_NAME.length; i++)
		{
			if(MONTHS_NAME[i].equals(month))
			{
				return i + 1;
			}
		}
		
		return 1;
	}
}
