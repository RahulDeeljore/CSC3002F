package BarrierS;
import java.util.concurrent.Semaphore;

public class Barrier 
{
	
        // class variables
        private int n; //var for barrier size
        private Semaphore barrier = new Semaphore(n);
	private Semaphore mutex = new Semaphore(1);
	private int count = 0;
	
       
        // constructor
	Barrier(int n) 
        {
        this.n = n;
	}
	
	public void b_wait() throws InterruptedException
        {

	 try {
		//implementing mutual exclusion
		mutex.acquire();
		count = count +1;
		mutex.release();

		if(count >= n) { // if count >= n, release barrier
		
            
		barrier.release(n); //release all
             
			         }
		else{	
	        barrier.acquire();// else thread acquires permit
		    }

	      } 

          catch(InterruptedException e) {}
                
	}

}
