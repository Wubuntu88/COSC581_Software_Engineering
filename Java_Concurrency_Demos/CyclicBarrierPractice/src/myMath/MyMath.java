package myMath;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

public class MyMath {
	private class StdevMinion implements Runnable {
		private int fromIndex;
		private int toIndex;

		public StdevMinion(int fromIndex, int toIndex) {
			this.fromIndex = fromIndex;
			this.toIndex = toIndex;
		}

		@Override
		public void run() {
			// calculate total
			int localTotal = 0;
			for(int i = fromIndex; i < toIndex; i++){
				localTotal += numbers[i];
			}
			total.addAndGet(localTotal);
			try {
				barrierBeforeTotal.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			//now the total and mean are known; next we calculate the sum of squared errors
			
			double localSumOfSquaredDeviations = 0.0;
			for(int i = fromIndex; i < toIndex; i++){
				localSumOfSquaredDeviations += Math.pow(numbers[i] - mean, 2);
			}
			sumOfSquaredDeviations.add(localSumOfSquaredDeviations);
			try {
				barrierBeforeSquaredDeviations.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class SetMean implements Runnable {
		@Override
		public void run() {
			mean = total.doubleValue() / numbers.length;
		}
	}
	
	private class SetStDev implements Runnable {
		/**
		 * At this point the sumOfSquaredDeviations is known.
		 * we just have to calculate the standard deviation
		 */
		@Override
		public void run() {
			stdev = Math.sqrt(sumOfSquaredDeviations.doubleValue() / numbers.length);
		}
	}

	private AtomicInteger total = new AtomicInteger(0);
	private double mean = 0;
	private DoubleAdder sumOfSquaredDeviations = new DoubleAdder();
	private double stdev = 0;
	private int[] numbers;

	Runtime runtime = Runtime.getRuntime();
	int cores = runtime.availableProcessors();
	
	private final int NUMBER_OF_THREADS = cores;
	private CyclicBarrier barrierBeforeTotal = 
			new CyclicBarrier(NUMBER_OF_THREADS + 1, new SetMean());
	private CyclicBarrier barrierBeforeSquaredDeviations = 
			new CyclicBarrier(NUMBER_OF_THREADS + 1, new SetStDev());
	
	
	public double stdev(int[] numbers) {
		this.numbers = numbers;
		
		System.out.println("cores: " + this.cores);
		
		if(cores <= 3){
			int mid = this.numbers.length / 2;
			StdevMinion minion1 = this.new StdevMinion(0, mid);
			StdevMinion minion2 = this.new StdevMinion(mid, numbers.length);
			new Thread(minion1).start();
			new Thread(minion2).start();
		}else {
			int quarter = this.numbers.length / 4;
			int mid = this.numbers.length / 2;
			int threeQuarters = quarter + mid;
			StdevMinion minion1 = this.new StdevMinion(0, quarter);
			StdevMinion minion2 = this.new StdevMinion(quarter, mid);
			StdevMinion minion3 = this.new StdevMinion(mid, threeQuarters);
			StdevMinion minion4 = this.new StdevMinion(threeQuarters, numbers.length);
			new Thread(minion1).start();
			new Thread(minion2).start();
			new Thread(minion3).start();
			new Thread(minion4).start();
		}
		
		try {
			barrierBeforeTotal.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		//now we know the mean; calculation of sum of squared errors is next
		try {
			barrierBeforeSquaredDeviations.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		//now we know the standard deviation; return it
		
		return stdev;
	}

}
