package practice;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * code from: https://www.javacodegeeks.com/2013/01/java-thread-pool-example-using-executors-and-threadpoolexecutor.html
*/
public class SimpleThreadPool {

	public static void main(String[] args) {
		final int NUMBER_OF_TASKS = 10;
		ExecutorService executor = Executors.newFixedThreadPool(5);

		for (int i = 0; i < NUMBER_OF_TASKS; i++) {
			Runnable worker = new WorkerThread("" + i);
			executor.execute(worker);
		}
		executor.shutdown();
		try {
			boolean completedBeforeTimeout = executor.awaitTermination(10, TimeUnit.SECONDS);
			String message = completedBeforeTimeout ? "Threads Finished Before Timeout" :
														"Timeout before threads finished";
			System.out.println(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Interrupted");
		}
		System.out.println("Finished all threads");
	}

}