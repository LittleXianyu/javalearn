package PhaserlDemo;

import java.util.concurrent.Phaser;

public class LikeCountDownLatch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Phaser phaser = new Phaser(1);        //相当于CountDownLatch(1) 
        for(int i = 0 ; i < 3 ; i++){
            Task_05 task = new Task_05(phaser);
            Thread thread = new Thread(task,"PhaseTest_" + i);
            thread.start();
        }     
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main: 等待 3秒  phaser 到达arraive");
        phaser.arrive();        //countDownLatch.countDown()
	}
	 static class Task_05 implements Runnable{
	        private final Phaser phaser;        
	        Task_05(Phaser phaser){
	            this.phaser = phaser;
	        }  
	        @Override
	        public void run() {
	        	System.out.println(Thread.currentThread().getName() + "阻塞---任务...phaser.getPhase()： "+phaser.getPhase());
	            phaser.awaitAdvance(phaser.getPhase());        //countDownLatch.await()
	            System.out.println(Thread.currentThread().getName() + "执行任务...");
	        }
	    }
}
