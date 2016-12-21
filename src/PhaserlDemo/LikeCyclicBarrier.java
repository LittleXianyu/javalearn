package PhaserlDemo;

import java.util.concurrent.Phaser;

public class LikeCyclicBarrier {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Phaser phaser = new Phaser(5);
        Thread[] th= new Thread[5];
        for(int i = 0 ; i < 5 ; i++){
            Task_01 task_01 = new Task_01(phaser);
            Thread thread = new Thread(task_01, "PhaseTest_" + i);
            thread.start();
            th[i]=thread;
        }
        for(int i = 0 ; i < 5 ; i++)
        try {
        	th[i].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("main");
	}
	static class Task_01 implements Runnable{
        private final Phaser phaser;
        
        public Task_01(Phaser phaser){
            this.phaser = phaser;
        }
        
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "执行任务完成，等待其他任务执行......");
            //等待其他任务执行完成
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName() + "继续执行任务...");
        }
    }
}
