import java.util.Date;



public class SyncLearn {

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
		private int i=0;
		@Override
		public void run() {
			while(i<10){
				synchronized (this) {
					System.out.println("Thread name :"+Thread.currentThread().getId()+ " i: "+i++);
				}
				
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
