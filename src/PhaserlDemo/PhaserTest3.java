package PhaserlDemo;
import java.util.concurrent.Phaser;  

public class PhaserTest3 {  
  
    public static void main(String args[]) throws Exception {  
        //  
        final int count = 5;  
        final int phaseToTerminate = 3;  
        final Phaser phaser = new Phaser(count) {  
            @Override  
            protected boolean onAdvance(int phase, int registeredParties) {  
                System.out.println("advance : "+this.toString());  
                return phase >= phaseToTerminate || registeredParties == 0;  
            }  
        };  
        System.out.println("before register + "+phaser.getRegisteredParties());  
        for(int i = 0; i < count-1; i++) {  
            System.out.println("starting thread, id: " + i);  
            final Thread thread = new Thread(new Task(i, phaser));  
            thread.start();  
        }  
        new Thread(new Task1(100,phaser)).start();
      phaser.register();
      System.out.println("after register + "+phaser.getRegisteredParties());  
      while (!phaser.isTerminated())
         phaser.arriveAndAwaitAdvance();
        System.out.println("main end。。。");

    }  
    public static class Task1 implements Runnable {  
        //  
        private final int id;  
        private final Phaser phaser;  
  
        public Task1(int id, Phaser phaser) {  
            this.id = id;  
            this.phaser = phaser;  
        }

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {  
                Thread.sleep(2000);  
            } catch(InterruptedException e) {  
                // NOP  
            }  
                System.out.println("Task1 thread: "+Thread.currentThread().getId()+" in Task.run(), phase: " + phaser.toString());  
                int s=phaser.arriveAndDeregister(); 
		}  
    }
    public static class Task implements Runnable {  
        //  
        private final int id;  
        private final Phaser phaser;  
  
        public Task(int id, Phaser phaser) {  
            this.id = id;  
            this.phaser = phaser;  
        }  
          
        @Override  
        public void run() {  
             	
        	try {  
                Thread.sleep(500);  
            } catch(InterruptedException e) {  
                // NOP  
            }  
                System.out.println("thread: "+Thread.currentThread().getId()+" in Task.run(), phase: " + phaser.getPhase() + ", id: " + this.id);  
                int s=phaser.arriveAndAwaitAdvance(); 
                System.out.println("phaser.arriveAndAwaitAdvance: "+ s);
                
                try {  
                    Thread.sleep(500);  
                } catch(InterruptedException e) {  
                    // NOP  
                } 
                System.out.println("thread: "+Thread.currentThread().getId()+"---------------第二件事"+" phaser.arriveAndAwaitAdvance: "+ s);
                s=phaser.arriveAndAwaitAdvance(); 
                try {  
                    Thread.sleep(500);  
                } catch(InterruptedException e) {  
                    // NOP  
                } 
                System.out.println("thread: "+Thread.currentThread().getId()+"---------------第三件事"+" phaser.arriveAndAwaitAdvance: "+ s);
                s=phaser.arriveAndAwaitAdvance(); 
                try {  
                    Thread.sleep(500);  
                } catch(InterruptedException e) {  
                    // NOP  
                } 
                System.out.println("thread: "+Thread.currentThread().getId()+"---------------第四件事"+" phaser.arriveAndAwaitAdvance: "+ s);
                s=phaser.arriveAndAwaitAdvance(); 
                if(!phaser.isTerminated()){
                	 try {  
                         Thread.sleep(500);  
                     } catch(InterruptedException e) {  
                         // NOP  
                     } 
                     System.out.println("thread: "+Thread.currentThread().getId()+"---------------第五件事"+" phaser.arriveAndAwaitAdvance: "+ s);
                     s=phaser.arriveAndAwaitAdvance(); 
                }
               

                
            
        }  
    }  
} 
