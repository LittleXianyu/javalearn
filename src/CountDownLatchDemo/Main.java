package CountDownLatchDemo;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean result = false;
        try {
        	new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ApplicationStartupUtil.getlatch().await();
						System.out.println("External services validation completed !! Result was :: "+ ApplicationStartupUtil.getResult());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
            result = ApplicationStartupUtil.checkExternalServices();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
	}

}
