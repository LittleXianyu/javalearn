package PhaserlDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class MoreTaskMoreStage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Phaser phaser = new Phaser(3){

			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				// TODO Auto-generated method stub
				System.out.println("advance  "+this.toString()); 
				if(phase==0){
					System.out.println("advance 第一阶段结束 ,isTerminated is false 整个阶段还未结束"); 
					return false;
				}
				else if(phase==1){
					System.out.println("advace 第二阶段结束 ，isTerminated is false 整个阶段已结束"); 
					return true;
				}
				else return registeredParties==0;
			}
			
		};
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		for(int i=0;i<3;i++){
			executorService.execute(new Task1(i,phaser));
//			new Thread(new Task1(i,phaser)).start();
			try {  
                Thread.sleep(1000);  
            } catch(InterruptedException e) {  
                // NOP  
            } 
		}
		phaser.awaitAdvance(phaser.getPhase());	//main线程一同等待
		System.out.println("main : 第一阶段阻塞结束");  
		System.out.println("main  "+phaser.toString()); 
		phaser.register();//第二阶段任务增加了一个，共4个，任务线程3个，main一个
		for(int i=0;i<3;i++)
			new Thread(new Task2(i,phaser)).start();		
		while(!phaser.isTerminated()){
			phaser.arriveAndAwaitAdvance();	
			System.out.println("main :第二阶段结束");  
		}
	}
	public static class Task1 implements Runnable{
		private final int id;  
        private final Phaser phaser;  
        public Task1(int _id,Phaser _phaser){
        	id=_id;
        	phaser=_phaser;
        }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("任务1：  starting thread, id: " + id+"  模拟执行2秒，后阻塞等待该阶段全部任务完成");  
			try {  
                Thread.sleep(2000);  
            } catch(InterruptedException e) {  
                // NOP  
            } 
			phaser.arrive();	
			System.out.println("任务1：  不阻塞");  
			System.out.println("任务1：  "+phaser.toString()); 
		}		
	}
	public static class Task2 implements Runnable{
		private final int id;  
        private final Phaser phaser;  
        public Task2(int _id,Phaser _phaser){
        	id=_id;
        	phaser=_phaser;
        }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("任务2，    starting thread, id: " + id+"  模拟执行1秒，后阻塞等待该阶段全部任务完成");  
			try {  
                Thread.sleep(1000);  
            } catch(InterruptedException e) {  
                // NOP  
            } 
			phaser.arriveAndAwaitAdvance();
		}	
	}
}
