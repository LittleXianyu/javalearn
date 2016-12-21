package PhaserlDemo;

import java.util.concurrent.Phaser;

public class LikebarrierAction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Phaser phaser = new Phaser(4){

			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				// TODO Auto-generated method stub
				return super.onAdvance(phase, registeredParties);
			}
            /**
             * registeredParties:线程注册的数量
             * phase:进入该方法的线程数，
             */
      
        };
        
        for(int i = 0 ; i < 3 ; i++){
            Task_03 task = new Task_03(phaser);
            Thread thread = new Thread(task,"task_" + i);
            thread.start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            phaser.arriveAndDeregister();    //主线程一直等待
        
        System.out.println("主线程任务已经结束....");
	}
	static class Task_03 implements Runnable{
        private final Phaser phaser;
        
        public Task_03(Phaser phaser){
            this.phaser = phaser;
        }
        
        @Override
        public void run() {
            
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始执行任务...");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + "结束执行任务...");
            
        }
    }
}
