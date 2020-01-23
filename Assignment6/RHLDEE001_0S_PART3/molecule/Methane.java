package molecule;

import java.util.concurrent.Semaphore;


public class Methane {
	
		public BarrierReusable barrier; // all atoms must be at the barrier
		public Semaphore mutex;  // mutually exclusive access to bonding
		public Semaphore carbonQ; //  
		public Semaphore hydrogensQ;  //
		
		private int H; //counter for available hydrogens
		private int C; //counter for available carbons

		Methane() {
			this.barrier=new BarrierReusable(5); //5 atoms in methane
			this.mutex=new Semaphore(1); //Open to start
			
			this.carbonQ = new Semaphore(0); // Closed to start
			this.hydrogensQ = new Semaphore(0); //Closed to start
			
		    this.H=0;
		    this.C=0;
		    
		}
		
		public void addHydrogen()  throws InterruptedException {
			
			H++;
			
		}
		public void removeHydrogen(int n)  throws InterruptedException {
			
			H=H-n;
			
		}

		public int getHydrogen() { return H;}
		
		public void addCarbon()  throws InterruptedException {
			
			C++;
			
		}
		public void removeCarbon(int n)  throws InterruptedException {
			
			C=C-n;
			
		}
		public int getCarbon() {return C;}
		
		public void bond(String atom) throws InterruptedException {
			
			System.out.println("...Bonding...."+atom);
			if ((H==4) && (C==1)) {
				H=0;
				C=0;
	    	 }
			
			
		}
		
		
	
}
