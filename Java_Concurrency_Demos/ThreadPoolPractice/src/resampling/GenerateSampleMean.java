package resampling;

import java.util.Random;
import java.util.concurrent.Callable;

public class GenerateSampleMean implements Callable<Double> {

	private double[] toSampleFrom;
	private int sizeOfArray;
	private int numberOfSamples;
	private Random rng;
	
	public GenerateSampleMean(double[] toSampleFrom, int numberOfSamples) {
		super();
		this.toSampleFrom = toSampleFrom;
		this.sizeOfArray = toSampleFrom.length;
		this.numberOfSamples = numberOfSamples;
		this.rng = new Random();
	}

	@Override
	public Double call() throws Exception {
		if(this.numberOfSamples < 1){
			throw new Exception("number of samples cannot be less than 1");
		}
		double total = 0;
		for(int i = 0; i < this.numberOfSamples; i++){
			int randomIndex = this.rng.nextInt(this.sizeOfArray);
			double randomlySelectedNumber = toSampleFrom[randomIndex];
			total += randomlySelectedNumber;
		}
		double mean = total / this.numberOfSamples;
		return mean;
	}

}
