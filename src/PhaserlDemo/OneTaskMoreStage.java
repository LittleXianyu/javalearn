package PhaserlDemo;

import java.util.concurrent.Phaser;

import PhaserlDemo.MoreTaskMoreStage.Task1;

public class OneTaskMoreStage {

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
				else if(phase+Integer.MAX_VALUE==1){
					System.out.println("advace 第二阶段结束 ，isTerminated is false 整个阶段已结束"); 
					return true;
				}
				else return registeredParties==0;
			}
			
		};
		for(int i=0;i<2;i++)
			new Thread(new Task1(i,phaser)).start();
		while(!phaser.isTerminated()){
			phaser.arrive();
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
			phaser.arriveAndAwaitAdvance();	
		}		
	}
}
