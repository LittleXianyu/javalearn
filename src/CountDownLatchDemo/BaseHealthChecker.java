package CountDownLatchDemo;

import java.util.concurrent.CountDownLatch;

public abstract class BaseHealthChecker implements Runnable{
	private CountDownLatch _latch;
    private String _serviceName;
    private boolean _serviceUp;
    public BaseHealthChecker(String serviceName, CountDownLatch latch)
    {
        super();
        this._latch = latch;
        this._serviceName = serviceName;
        this._serviceUp = false;
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
            verifyService();
            _serviceUp = true;
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            _serviceUp = false;
        } finally {
            if(_latch != null) {
                _latch.countDown();
            }
        }
	}
	 public String getServiceName() {
	        return _serviceName;
	    }
	 
	    public boolean isServiceUp() {
	        return _serviceUp;
	    }
	    //This methos needs to be implemented by all specific service checker
	    public abstract void verifyService();
    
    
}
