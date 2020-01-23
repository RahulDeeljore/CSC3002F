package dishWashS;
import java.util.concurrent.Semaphore;

public class WetDishRack
{
	private int rackSize;//size of dish rack	
	private Semaphore mutex; // for mutual exclusion
	private Semaphore dishes;// washer wait when rack is full	
 	private Semaphore spaces;// dryer wait when rack is empty
	private int [] rack;// array to store dish_id
	private int in, out;		

	WetDishRack(int rackSize)
	{
	    this.rackSize = rackSize;
		mutex = new Semaphore(1);
		dishes = new Semaphore(0);
		spaces = new Semaphore(rackSize);
		rack = new int [rackSize];
		in = 0;
		out = 0;
	}
	public void addDish(int dish_id)  throws InterruptedException
	{
		try{
		spaces.acquire();// add dish when there is a free space	in rack
		mutex.acquire();// mutex allows only 1 thread to do work			
		rack[in] = dish_id;// adding id to array
		in = (in + 1) % rackSize;// to prevent arrayoutofbounds error
		mutex.release();			
		dishes.release();
		}
		
		catch (InterruptedException e) {
		}
	}

	
	public int removeDish() throws InterruptedException
	{	try{
		dishes.acquire();//add dish to be dried			
		mutex.acquire();// mutex allows only 1 thread to do work			
		int dish_id = rack[out];// adding id to array
		out = (out + 1) % rackSize; // to prevent arrayoutofbounds error
		mutex.release();			
		spaces.release();			
		return dish_id;
		}
		
		catch (InterruptedException e) {
		throw e;
		}
	}
}

