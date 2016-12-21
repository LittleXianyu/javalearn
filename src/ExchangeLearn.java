import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

import org.omg.CORBA.CurrentHolder;


public class ExchangeLearn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Exchanger<List<String>> exchanger = new Exchanger<List<String>>();
		new Thread(new Produce(exchanger)).start();
		new Thread(new Consumer(exchanger)).start();
	}
	public static class Produce implements Runnable{
		private List<String> buffer;
		private Exchanger<List<String>> exchanger;
		public Produce(Exchanger<List<String>> _exchanger){
			exchanger=_exchanger;
			buffer=new ArrayList<String>();
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int i=0;i<10;i++){
				buffer.add(new String("produce "+i));
			}		
			try {
				Thread.sleep(1000);
				buffer = exchanger.exchange(buffer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Produce交换后：大小：  "+buffer.size());
		}
		
	}
	public static class Consumer implements Runnable{
		private List<String> buffer;
		private Exchanger<List<String>> exchanger;
		public Consumer(Exchanger<List<String>> _exchanger){
			exchanger=_exchanger;
			buffer=new ArrayList<String>();
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub	
			try {
				buffer = exchanger.exchange(buffer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Consumer交换后：大小：  "+buffer.size());
			for(int i=0;i<buffer.size();i++){
				System.out.println("Consumer交换后data :  "+buffer.get(i));
			}
		}	
	}

}
