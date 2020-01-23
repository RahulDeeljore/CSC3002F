package molecule;

import molecule.Carbon;
import molecule.Methane;
import molecule.Hydrogen;

public class RunSimulation {

	/**
	 This class is sent the number of 
	 */
	public static void main(String[] args) {
		int no_hydrogens = Integer.parseInt(args[0]);
		int no_carbons = Integer.parseInt(args[1]);
		
		System.out.println("Starting simulation with "+no_hydrogens+" H and "+no_carbons + " C");
		
		Carbon[] carbons = new Carbon[no_carbons];
		Hydrogen[] hydrogens = new Hydrogen[no_hydrogens];
		Methane sharedMethane = new Methane();
		
		for (int i=0;i<no_carbons;i++) {
			carbons[i]=new Carbon(sharedMethane); // call constructor
			carbons[i].start(); // start thread
		}
		for (int i=0;i<no_hydrogens;i++) {
			hydrogens[i]= new Hydrogen(sharedMethane);
			hydrogens[i].start();
		}
		
		


	}

}
