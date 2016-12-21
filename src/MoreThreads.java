import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MoreThreads {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Runnable runnable=new MyRunnable();
		Thread thread1=new Thread(runnable);
		Thread thread2=new Thread(runnable);
		thread1.start();
		thread2.start();
	}
	public static class MyRunnable implements Runnable{

		private Date startDate;
		public static ThreadLocal<Integer> i=new ThreadLocal<Integer>();
		@Override
		public void run() {
			// TODO Auto-generated method stub
//			startDate=new Date();
//			System.out.printf("Starting Thread: %s : %s\n",Thread. currentThread().getId(),startDate);
//			try {
//			TimeUnit.SECONDS.sleep(2000);
//			} catch (InterruptedException e) {
//			e.printStackTrace();
//			}
//			System.out.printf("Thread Finished: %s : %s\n",Thread. currentThread().getId(),startDate);
			
			 i.set(0);
			while(i.get()<10){
				i.set(i.get()+1);
				System.out.println("Thread name :"+Thread.currentThread().getId()+ " i: "+i.get());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	}

}
