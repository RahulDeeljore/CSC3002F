package molecule;

public class Carbon extends Thread {
	
	private static int carbonCounter =0;
	private int id;
	private Methane sharedMethane;
	
	public Carbon(Methane methane_obj) {
		Carbon.carbonCounter+=1;
		id=carbonCounter;
		this.sharedMethane = methane_obj;
	}
	
	public void run() {

	try {
			sharedMethane.mutex.acquire(); // allow only 1 thread to work
			sharedMethane.addCarbon(); // add carbon to react

			// once carbon added, check if there are enough hydrogen atoms
			if (sharedMethane.getHydrogen() >= 4) 

			{

				System.out.println("---Group ready for bonding---"); // there are enough reactants, start bonding

                                sharedMethane.carbonQ.release(1); // release lock
				sharedMethane.removeCarbon(1);  // decrement carbon counter

				sharedMethane.hydrogensQ.release(4); // releae hydrogen lock
				sharedMethane.removeHydrogen(4); //  dercrement hydrogen counter
				
				
			} 


			else
			{
				sharedMethane.mutex.release(); // not enough hydrogen atoms to react, release lock
			}

			sharedMethane.carbonQ.acquire(); // acquire lock for carbon 
			sharedMethane.bond("C" + this.id); // add carbon to react
			sharedMethane.barrier.b_wait(); // wait on barrier
			sharedMethane.mutex.release(); // release mutex lock

		} 
	catch (InterruptedException ex) 
	{

			/* not handling this */
	}
		// System.out.println(" ");
			}


}
