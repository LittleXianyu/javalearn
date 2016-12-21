import java.awt.Event;
import java.util.Deque;


public class WriterTask {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static class Task1 implements Runnable{
		private Deque<Event> deque;
		public Task1 (Deque<Event> deque){
		this.deque=deque;
		}
		@Override
		public void run() {
		
		}
	}
}
