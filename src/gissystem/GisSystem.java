package gissystem;

import gissystem.controllers.GisSystemController;

public class GisSystem {
	
	public static void main( String [] args ) {
		GisSystemController controller = new GisSystemController();
		
		controller.doSetup( args );
		controller.doWork();
	}
}
