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
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
//如果方法中有finally语句块，那么return语句又是如何执行的呢？
//例如下面这段代码：
//try{
//return expression;
//}finally{
//do some work;
//}
//首先我们知道，finally语句是一定会执行，但他们的执行顺序是怎么样的呢？他们的执行顺序如下：
//1、执行：expression，计算该表达式，结果保存在操作数栈顶；
//2、执行：操作数栈顶值（expression的结果）复制到局部变量区作为返回值；
//3、执行：finally语句块中的代码；
//4、执行：将第2步复制到局部变量区的返回值又复制回操作数栈顶；
//5、执行：return指令，返回操作数栈顶的值；
public class ExecutorFindFiles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadPoolExecutor executor=(ThreadPoolExecutor)Executors.newFixedThreadPool(10);
		List<Myfindtask> mlist=new ArrayList<Myfindtask>();
		File filedir = new File("D:/");

		for(File f:filedir.listFiles()){
			if(f.isDirectory()){
				System.out.println(f.getPath());
				Myfindtask mf=new Myfindtask("12345.txt",f.getPath());
				mlist.add(mf);			
			}	
		}
		try {
			String s=executor.invokeAny(mlist);
			System.out.println(" 线程池中的线程数目："+executor.getPoolSize()+",队列中等待执行的任务数目："+
					executor.getQueue().size()+",已执行完的别的任务的数目："+executor.getCompletedTaskCount());
			if(s==null)
				System.out.println("没有找到文件位置：");
			else
			System.out.println("找到文件位置："+s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		executor.shutdown();
	}
	static class Myfindtask implements Callable<String>{
		public Myfindtask(String filename,String path){
			mfilename=filename;
			mpath=path;
		}
		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			String s=findfile(mpath);
			if(s!=null)
				return s;
			else 
				throw new Exception("没找到");  
		}
		
		public String mfilename;
		public String mpath;
		public String findfile(String path){
			File root = new File(path);
		    File[] files = root.listFiles();
		    for(File f:files){
		    	 try {
	    	            Thread.currentThread().sleep(4000);
	    	        } catch (InterruptedException e) {
	    	            e.printStackTrace();
	    	        }
		    	if(f.getName().equals(mfilename.toString()))return f.getPath();
		    }
		    return null;
		}
		
	}
	
}
