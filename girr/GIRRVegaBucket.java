package girr;

import containers.IRisk;
import containers.LinearRiskBucket;

public class GIRRVegaBucket 
	extends LinearRiskBucket<GIRRVegaRiskFactor,GIRRVegaRiskFactorKey>
	implements  IRisk<GIRRVegaBucket, GIRRVegaBucketKey>{

		
		protected GIRRVegaBucketKey key;
		protected GIRRCalculator calculator;
		
		public GIRRVegaBucket(GIRRVegaBucketKey _key){
			
			key=_key;
			calculator=key.Getriskinterbucketkey().Getglobalriskkey().GetFactory().Getgirrcalculator();
			
		}
		
		@Override
		public GIRRVegaBucketKey Key() {
			
			return key;
		}

		@Override
		public Double GetCorrelation(GIRRVegaBucket other) {

			return calculator.GetVegaGamma();
		}

		


	}
