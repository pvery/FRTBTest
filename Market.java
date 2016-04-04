package CalculFRTB;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


import containers.RiskFactory;
import containers.RiskMap;



public final class Market {
	
	public RiskMap riskfactors;
	public RiskFactory factory;
	public Logger myLog;
	
	public Market() throws SecurityException, IOException{
		
		riskfactors = new RiskMap();
		factory = new RiskFactory(this);
		
		BuildLog();

	}
	
	protected void BuildLog() throws SecurityException, IOException{
		
		myLog = Logger.getLogger("Market");
		myLog.setLevel(Level.ALL); 
		myLog.setUseParentHandlers(false); 
		
		FileHandler fh = new FileHandler("monlog%u.log");

		//ch.setLevel(Level.INFO); 
		myLog.addHandler(fh);
	
	}
	
	public boolean ComputeCapitalStandAlone(){
		
		riskfactors.ComputeStandAlone();
		factory.GetGlobalRisk();
		
		return true;
	}

	public boolean LogCapitalStandAlone(){
		
		riskfactors.LogCapitalStandAlone(myLog);
		
		return true;
	}
	
}
