package gissystem.interfaces;

import gissystem.controllers.DataAccessController;

public interface ICommand {
	public void execute( DataAccessController controller );
}
