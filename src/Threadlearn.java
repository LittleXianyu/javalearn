
public class Threadlearn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread one=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("name :"+Thread.currentThread().getName()+Thread.currentThread().getState());
			}
		});
		one.setName("xianyu thread");
		one.setPriority(Thread.MAX_PRIORITY);
		System.out.println("State :"+one.getState()+ "ID: "+one.getId());
		one.start();
		try {
			one.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("main函数结束");
		
	}

}
