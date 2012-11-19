package gissystem.tests.helpers;

import gissystem.controllers.DatabaseController;
import gissystem.controllers.HashTableController;
import gissystem.controllers.QuadTreeController;
import gissystem.helpers.io.ConsoleLogger;
import gissystem.interfaces.IDataAccessController;
import gissystem.interfaces.ILogger;

public class DataAccessControllerStub implements IDataAccessController {
	private ILogger logger;
	private String nextCommand;
	private QuadTreeController qtController;
	private HashTableController htController;
	private DatabaseController dbController;
	
	public DataAccessControllerStub() {
		this( new ConsoleLogger() );
	}
	
	public DataAccessControllerStub( ILogger logger ) {
		this( logger, null );
	}
	
	public DataAccessControllerStub( ILogger logger, String nextCommand ) {
		this.logger = logger;
		this.nextCommand = nextCommand;
		this.qtController = new QuadTreeController();
		this.htController = new HashTableController();
		this.dbController = new DatabaseController();
	}
	
	@Override
	public String getNextCommandLine() {
		return this.nextCommand;
	}

	@Override
	public String findInDatabase(Long offset) {
		return null;
	}

	@Override
	public QuadTreeController getQuadTreeController() {
		return this.qtController;
	}

	@Override
	public HashTableController getHashTableController() {
		return this.htController;
	}

	@Override
	public ILogger getLogger() {
		return this.logger;
	}

	@Override
	public DatabaseController getDatabaseController() {
		// TODO Auto-generated method stub
		return this.dbController;
	}

}
