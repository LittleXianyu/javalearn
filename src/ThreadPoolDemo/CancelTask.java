package ThreadPoolDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CancelTask {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService executorService= Executors.newCachedThreadPool();
		Future<String> result = executorService.submit(new Task());
		try {
			Thread.currentThread().sleep(500);
			result.cancel(true);
			System.out.println(result.isCancelled());
			System.out.println(result.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static class Task implements Callable<String>{
		public Task(){}
		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			Thread.currentThread().sleep(2000);
			return "task";
		}
		
	}

}
