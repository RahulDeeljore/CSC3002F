package BarrierS;
import java.util.Random;

public class BThread extends Thread {
	private int id;
	private Barrier sharedBarrier;
	private Random snooze_time;
	
	BThread(int n,Barrier sBarrier) {
		this.id = n;
		this.sharedBarrier= sBarrier;
		this.snooze_time = new Random();
	}
	
	public void run() {
	    try {
	    	sleep(snooze_time.nextInt(10000)); //sleep a random time
	    	System.out.println("Thread "+id+" waiting at barrier.");
	    	sharedBarrier.b_wait();
	    	System.out.println("Thread "+id+" passed barrier.");
	    }
	    catch (InterruptedException ex) { /* not handling this  */}
	    
	}

}	