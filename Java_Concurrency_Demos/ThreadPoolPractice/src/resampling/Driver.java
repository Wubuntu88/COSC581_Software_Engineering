package resampling;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Driver {

	public static void main(String[] args) {
		int N_ELEMS = 1000;
		double mean = 10.0;
		double stdev = 10.0;
		double[] gaussianDistribution = 
				GaussianDistributionGenerator.generateGaussianDistribution(N_ELEMS, mean, stdev);
		
		int NUMBER_OF_SAMPLES = 10000;
		int SAMPLES_PER_ITERATION = 20;
		
		int N_THREADS = 4;
		ExecutorService execService = Executors.newFixedThreadPool(N_THREADS);
		
		List<GenerateSampleMean> meanFinderMinions = new ArrayList<>();
		for(int i = 0; i < NUMBER_OF_SAMPLES; i++){
			GenerateSampleMean callable = new GenerateSampleMean(gaussianDistribution, SAMPLES_PER_ITERATION);
			meanFinderMinions.add(callable);
		}
		
		try {
			List<Future<Double>> means = execService.invokeAll(meanFinderMinions);
			ArrayList<Double> the_means = new ArrayList<>();
			for(Future<Double> future: means){
				try {
					the_means.add((Double) future.get());
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			execService.shutdown();
			Driver.printMeansToFile("means.txt", the_means);
			System.out.println("All Done");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void printMeansToFile(String fileName, List<Double> means){
		try {
			PrintWriter pw = new PrintWriter(fileName);
			StringBuffer sb = new StringBuffer("");
			for(Double mean: means){
				sb.append(String.format("%.2f\n", mean));
			}
			sb.deleteCharAt(sb.length() - 1);
			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}


















