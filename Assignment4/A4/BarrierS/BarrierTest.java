package BarrierS;

// class to run barrier simulation
public class BarrierTest {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]); //size of barrier
		int t = Integer.parseInt(args[1]); // number of threads
		
		Barrier sharedBarrier = new Barrier(n);
		
		BThread[] threads =new BThread[t];
		
		System.out.println("Starting simulation with "+ t +" threads, barrier size "+n);

		for (int i=0;i<t;i++) {
			threads[i]=new BThread(i, sharedBarrier); // call constructor
			threads[i].start(); // start thread
		}
		System.out.println("Parent thread completed");

	}

}
