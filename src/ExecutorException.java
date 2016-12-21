import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class ExecutorException {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10, new MyFactory());
         List<MyTask> lm=new ArrayList<MyTask>();
        for(int i=0;i<15;i++){
            MyTask myTask = new MyTask(i);
            lm.add(myTask);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
            executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
        }
        try {
			executor.invokeAny(lm);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        executor.shutdown();
        
	}
	static class MyFactory implements ThreadFactory{
		
		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			Thread tt=new Thread(r);
			System.out.println("创建线程--");
			tt.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					// TODO Auto-generated method stub
					System.out.println(t.getName()+"  my exception :"+e.getMessage());
				}
			});
			return tt;
		}
		
	}
	static class MyTask implements Callable<String> {
	
	    private int taskNum;
	    public MyTask(int num) {
	        this.taskNum = num;
	    }
		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			System.out.println("正在执行task "+taskNum);
            try {
				Thread.currentThread().sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  
        System.out.println("task "+taskNum+"执行完毕");
        return taskNum+"--";
		}
	     
	   
}

}
