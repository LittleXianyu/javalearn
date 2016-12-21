import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LackLearn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyLockCondition mlc=new MyLockCondition();
		for(int i=0;i<15;i++){
			Thread th=new Thread(new setRunnable(mlc, i+""));
			th.start();
		}
		for(int i=0;i<15;i++){
			Thread th=new Thread(new getRunnable(mlc));
			th.start();
		}
	}
	public static class setRunnable implements Runnable{
		private MyLockCondition mlc;
		private String str;
		public setRunnable(MyLockCondition m,String s){
			mlc=m;
			str=s;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mlc.set(str);
			System.out.println("setRunnable--:  ");
		}
		
	}
	public static class getRunnable implements Runnable{
		private MyLockCondition mlc;
		public getRunnable(MyLockCondition m){
			mlc=m;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			System.out.println("getRunnable--:  " +mlc.get());
		}
		
	}
	public static class MyLockCondition {
		final Lock lock = new ReentrantLock();// 锁对象
		final Condition isFull = lock.newCondition();// 写线程条件
		final Condition isEmpty = lock.newCondition();// 读线程条件
		final ArrayList<String> items = new ArrayList<String>(10);// 缓存队列

		public void set(String o) {
			lock.lock();
			try {
				while (items.size() == 10) {
					System.out.println("wait set fun "+"Thread: "+Thread.currentThread().getId()+"item size: "+ items.size());
					isFull.await();
				}
				items.add(o);
				Thread.sleep(1000);
				System.out.println("set fun "+"Thread: "+Thread.currentThread().getId()+"item size: "+ items.size());
				isEmpty.signalAll();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		public String get(){
			lock.lock();
			String s=new String();
			try {
				while (items.size() == 0) {
					isEmpty.await();
				}
				s=items.get(0);
				items.remove(0);
				Thread.sleep(100);
				System.out.println("get fun "+"Thread: "+Thread.currentThread().getId()+"item size: "+ items.size()+"  getsname :"+ s);
				isFull.signalAll();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			return s;
		}
	}

}
