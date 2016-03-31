package girr;

import java.util.Map;

import staticdata.IRCurveType;
import staticdata.IRTenor;
public class GIRRCalculator {
	
	protected double x;
	protected double theta;
	protected double gamma;
	protected double lh;
	protected double rw;
	protected double alpha;
	protected Map<IRTenor,Double> weights;
	
	
	protected double rwk;
	
	public GIRRCalculator(double _x,double _theta,double _gamma,double _lh,	double _rw,	double _alpha, Map<IRTenor,Double> _weights){
		x=_x;
		theta=_theta;
		gamma=_gamma;
		lh=_lh;
		rw=_rw;
		alpha = _alpha;
		weights=_weights;
		
		rwk=Math.min(rw*Math.sqrt(lh/10.0),1.0);
		
	}
	
	
	public double GetDeltaGamma(){
		
		return gamma;
	}
	
	public double GetVegaGamma(){
		
		return 0.5*gamma;
	}
	
	public double GetCurvatureGamma(){
		
		return gamma*gamma;
	}
	
	public double GetDeltaWeight(IRTenor irtenor){
		
		return weights.get(irtenor);
	}
	
	public double GetVegaWeight(){
		
		return rwk;
	}
	
	public double GetDeltaCorrelation(double tenor1, double tenor2,IRCurveType ircurvetype1,IRCurveType ircurvetype2){
		
		double correl=  Math.max(0.4,Math.exp(-this.theta*Math.abs(tenor1-tenor2)/Math.min(tenor1, tenor2))) ;
		
		if ( ! ircurvetype1.equals(ircurvetype2)) {
			correl*=x;
		}
		return correl;
	}

	
	public double GetVegaCorrelation(double tenor1, double tenor2,double optionmaturity1, double optionmaturity2){
		
		double correloptionmaturity = Math.exp(-this.alpha*Math.abs(optionmaturity1-optionmaturity2)/Math.min(optionmaturity1, optionmaturity2));
		double correlunderlyingmaturity = Math.exp(-this.alpha*Math.abs(tenor1-tenor2)/Math.min(tenor1, tenor2));
		
		return Math.min(correloptionmaturity*correlunderlyingmaturity, 1.0);
	}
	
	public double GetCurvatureCorrelation(){

		
		return 1.0;
	}
	
}
