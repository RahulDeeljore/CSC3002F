package dishWashS;

public class CleaningDishes {

	public static void main(String[] args) {
		int no_dishes = Integer.parseInt(args[0]); //size of barrier
		int rack_size = Integer.parseInt(args[1]); // number of threads
		int washerSlackness = Integer.parseInt(args[2]);
		int dryerSlackness = Integer.parseInt(args[3]); 
		
		WetDishRack the_dish_rack = new WetDishRack(rack_size);
		Washer bob = new Washer(no_dishes,the_dish_rack,washerSlackness);
		Dryer anne = new Dryer(no_dishes,the_dish_rack,dryerSlackness);
		anne.start();
		bob.start();
	}

}
