package practice;

public class WorkerThread implements Runnable {

	private String task;

	public WorkerThread(String s) {
		this.task = s;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " Start. Task = " + task);
		processCommand();
		System.out.println(Thread.currentThread().getName() + " End.");
	}

	private void processCommand() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return this.task;
	}
}