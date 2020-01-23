package dishWashS;

import java.util.Random;

public class Dryer extends Thread {
	private int dirty_dishes;
	private WetDishRack shared_rack;
	private Random drying_time;
	private int sleep;
	
	Dryer(int n, WetDishRack the_rack, int sleep) {
		this.dirty_dishes = n;
		this.shared_rack = the_rack;
		this.drying_time = new Random();
		this.sleep=sleep;
	}
	
	public void run() {
	    try {
	    	int counter=0;
	    	while (dirty_dishes>0) {
	    		counter =shared_rack.removeDish();
	    		System.out.println("---Dryer removed dish #"+counter+" from rack");
	    		sleep(drying_time.nextInt(sleep)); //wait a bit
	    		System.out.println("---Dryer is done with dish #"+counter);
	    		dirty_dishes--;
	    	}
	    }
	    catch (InterruptedException ex) { /* not handling this  */}
	    System.out.println("---Dryer is DONE.");
	}

}	