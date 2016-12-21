package CountDownLatchDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ApplicationStartupUtil {
	//List of service checkers
    private static List<BaseHealthChecker> _services;
 
    //This latch will be used to wait on
    private static CountDownLatch _latch;
    private ApplicationStartupUtil()
    {_latch = new CountDownLatch(3);
    }
    private final static ApplicationStartupUtil INSTANCE = new ApplicationStartupUtil();
    public static ApplicationStartupUtil getInstance()
    {
        return INSTANCE;
    }
    public static void await(){
        //Now wait till all services are checked
        try {
			_latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static CountDownLatch getlatch(){
    	return _latch;
    }
    public static boolean getResult(){
    	for(final BaseHealthChecker v : _services)
        {
            if( ! v.isServiceUp())
            {
                return false;
            }
        }
        return true;
    }
    public static boolean checkExternalServices() throws Exception
    {
        //Initialize the latch with number of service checkers
 
        //All add checker in lists
        _services = new ArrayList<BaseHealthChecker>();
        _services.add(new NetworkHealthChecker(_latch));
        _services.add(new CacheHealthChecker(_latch));
        _services.add(new DatabaseHealthChecker(_latch));
 
        //Start service checkers using executor framework
        Executor executor = Executors.newFixedThreadPool(_services.size());
 
        for(final BaseHealthChecker v : _services)
        {
            executor.execute(v);
        }
 

 
        //Services are file and now proceed startup
        for(final BaseHealthChecker v : _services)
        {
            if( ! v.isServiceUp())
            {
                return false;
            }
        }
        return true;
    }
}
