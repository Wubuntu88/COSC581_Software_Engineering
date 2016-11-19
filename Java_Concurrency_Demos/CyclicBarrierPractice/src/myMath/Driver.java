package myMath;
public class Driver {

	public static void main(String[] args) {
		int ITEMS = 10000000;
		int[] numbers = new int[ITEMS];
		for(int i = 0; i < numbers.length; i++){
			numbers[i] = i;
		}
		
		MyMath myMath = new MyMath();
		long start1 = System.currentTimeMillis();
		double stdev1 = myMath.stdev(numbers);
		long stop1 = System.currentTimeMillis();
		String toPrint1 = String.format("%25s%.9f", "stdev with threading: ", stdev1);
		System.out.println(toPrint1);
		
		long start2 = System.currentTimeMillis();
		double stdev2 = stdev(numbers);
		long stop2 = System.currentTimeMillis();
		String toPrint2 = String.format("%25s%.9f", "stdev without threading: ", stdev2);
		System.out.println(toPrint2);
		
		long multithreadedMillis = stop1 - start1;
		long singlyThreadedMillis = stop2 - start2;
		System.out.println("Multithreaded Milliseconds: " + multithreadedMillis);
		System.out.println("Singlythreaded Milliseconds: " + singlyThreadedMillis);
	}
	
	public static double stdev(int[] numbers){
		int total = 0;
		double mean = 0;
		for(Integer num: numbers){
			total += num;
		}
		mean = (double) total / numbers.length;
		
		double sumOfSquaredDeviations = 0;
		for(Integer num: numbers){
			sumOfSquaredDeviations += Math.pow(num - mean, 2);
		}
		return Math.sqrt(sumOfSquaredDeviations / numbers.length);
	}
}