package gissystem;

import gissystem.controllers.GisSystemController;

/**
 * Entry point for the program.
 * @author John Quentin Ruffer
 *
 */
public class GisSystem {
	
	public static void main( String [] args ) {
		GisSystemController controller = new GisSystemController();
		
		controller.doSetup( args );
		controller.doWork();
	}
}
