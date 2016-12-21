import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SyncProduceCostume {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventStorage storage=new EventStorage();
		Producer producer=new Producer(storage);
		Thread thread1=new Thread(producer);
		Consumer consumer=new Consumer(storage);
		Thread thread2=new Thread(consumer);
		thread2.start();
		thread1.start();
	}

	public static class EventStorage {
		private int maxSize;
		private List<Date> storage;

		public EventStorage() {
			maxSize = 10;
			storage = new LinkedList<>();
		}

		public synchronized void set() {
			while (storage.size() == maxSize) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			storage.add(new Date());
			System.out.printf("Set: %d", storage.size());
			notifyAll();
		}
	


		public synchronized void get() {
			while (storage.size() == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.printf("Get: %d: %s\n", storage.size(),
					((LinkedList<?>) storage).poll());
			notifyAll();
		}

	}

	public static class Producer implements Runnable {
		private EventStorage storage;

		public Producer(EventStorage storage) {
			this.storage = storage;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				storage.set();
			}
		}
	}

	public static class Consumer implements Runnable {
		private EventStorage storage;

		public Consumer(EventStorage storage) {
			this.storage = storage;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				storage.get();
			}
		}
	}
}