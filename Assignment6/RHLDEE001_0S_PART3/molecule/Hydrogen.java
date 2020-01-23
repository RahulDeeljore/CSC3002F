package molecule;

public class Hydrogen extends Thread {

	private static int carbonCounter =0;
	private int id;
	private Methane sharedMethane;
	
	
	public Hydrogen(Methane methane_obj) {
		Hydrogen.carbonCounter+=1;
		id=carbonCounter;
		this.sharedMethane = methane_obj;
		
	}
	
	public void run() {
		try {
			sharedMethane.mutex.acquire(); // allow only 1 thread to work
			sharedMethane.addHydrogen();   // increment hydrogen counter
			
			// check if there are enough elements to react
			if (sharedMethane.getHydrogen() >= 4 && sharedMethane.getCarbon() >= 1) {

				System.out.println("---Group ready for bonding---"); 

				sharedMethane.hydrogensQ.release(4); // release the 4 locks
				sharedMethane.removeHydrogen(4);     // decrement hydrogen counter 

				sharedMethane.carbonQ.release(1);    // release 1 carbon lock
				sharedMethane.removeCarbon(1);       // decrement carbon counter

			}

			else{
				sharedMethane.mutex.release(); // release mutex lock because not enough hydrogen atoms available
			    }

			sharedMethane.hydrogensQ.acquire(); // add a new hydrogen atom

			sharedMethane.bond("H" + this.id);

			sharedMethane.barrier.b_wait();  // wait on barrier
			

		} catch (InterruptedException ex) {
			/* not handling this */}
		// System.out.println(" ");
}

	
	  

}
