package gissystem.interfaces;

import gissystem.controllers.DatabaseController;
import gissystem.controllers.HashTableController;
import gissystem.controllers.QuadTreeController;

public interface IDataAccessController {
	public String getNextCommandLine();
	public String findInDatabase( Long offset );
	public QuadTreeController getQuadTreeController();
	public HashTableController getHashTableController();
	public DatabaseController getDatabaseController();
	public ILogger getLogger();
}
