import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ExecutorLearn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Callable<Integer> callable = new Callable<Integer>() {
//            public Integer call() throws Exception {
//            	System.out.println("正在执行callable ");
//    	        try {
//    	            Thread.currentThread().sleep(4000);
//    	        } catch (InterruptedException e) {
//    	            e.printStackTrace();
//    	        }
//    	        System.out.println("callable执行完毕");
//                return 111;
//            }
//        };
//        ExecutorService executor1=Executors.newFixedThreadPool(10);
//        Future<Integer> future =executor1.submit(callable);
//        try {
//			System.out.println(future.get());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List ll=new ArrayList<Future>();
		List mm=new ArrayList<MyCallable>();
		for(int i=0;i<15;i++){
			MyCallable myTask=new MyCallable(i);
			mm.add(myTask);
//			Future<Integer> future=
//			executor.submit(myTask);
//			ll.add(future);
//			try {
//	            Thread.currentThread().sleep(1000);
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        }
//				System.out.println(" 线程池中的线程数目："+executor.+",队列中等待执行的任务数目："+
//				executor.getQueue().size()+",已执行完的别的任务的数目："+executor.getCompletedTaskCount());
			
		}
		try {
			List<Future<Integer>> results = executor.invokeAll(mm);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	static class MyThreadPool extends ThreadPoolExecutor{

		public MyThreadPool(int corePoolSize, int maximumPoolSize,
				long keepAliveTime, TimeUnit unit,
				BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
			// TODO Auto-generated constructor stub
		}
		protected void afterExecute(Runnable r, Throwable t) { 
//			System.out.println("结束执行的任务 "+r.toString());
		}
		
	}
	static class MyCallable implements Callable<Integer>{
		private int taskNum;
	     
	    public MyCallable(int num) {
	        this.taskNum = num;
	    }
		@Override
		public Integer call() throws Exception {
			// TODO Auto-generated method stub
			System.out.println("正在执行callable "+ taskNum);
	        try {
	            Thread.currentThread().sleep(4000);
	        } catch (InterruptedException e) {
	        	System.out.println("被迫停止  "+taskNum);
	        }
	        System.out.println("callable执行完毕  "+taskNum);
            return taskNum;
		}
		
	}
	static class MyTask implements Runnable{
		private int taskNum;
	     
	    public MyTask(int num) {
	        this.taskNum = num;
	    }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("正在执行task "+taskNum);
	        try {
	            Thread.currentThread().sleep(4000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        System.out.println("task "+taskNum+"执行完毕");
		}
		public String toString(){
			return "task--"+ taskNum;
			
		}
		
	}

}
