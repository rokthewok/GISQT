package gissystem.interfaces;

import gissystem.controllers.HashTableController;
import gissystem.controllers.QuadTreeController;

public interface IDataAccessController {
	public String getNextCommandLine();
	public String findInDatabase( Integer offset );
	public QuadTreeController getQuadTreeController();
	public HashTableController getHashTableController();
	public ILogger getLogger();
}
