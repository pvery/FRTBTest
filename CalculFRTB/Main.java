package CalculFRTB;

import java.io.File;
import java.io.IOException;
import excellimport.SensitivitiesImport;
import tests.Tests;



public class Main {



	public static void main(String[] args) throws IOException {
		//test();
		importData();
		

	}
	
	public static void test() throws SecurityException, IOException{
		
		Market market = new Market();
		Tests tests = new Tests(market);
		
		tests.test();
		
	}
	
	
	public static void importData() throws IOException
	{
		Market market = new Market();
		
		String filename = new File("sensitivities.xlsx").getAbsolutePath();
		
		
		SensitivitiesImport sensitivitiesimport = new SensitivitiesImport(filename,market);
		sensitivitiesimport.importSheet();
		
		market.ComputeCapitalStandAlone();
		

		System.out.print("End !!! "); 
		
		
	}

	
	
	
}
