package resampling;

import java.util.Random;

public class GaussianDistributionGenerator {
	public static double[] generateGaussianDistribution(int nElems, double mean, double stdev){
		Random rng = new Random();
		double[] gaussianDistribution = new double[nElems];
		for(int i = 0; i < gaussianDistribution.length; i++){
			gaussianDistribution[i] = rng.nextGaussian() * stdev + mean;
		}
		return gaussianDistribution;
	}
}
