package gissystem.interfaces;

import gissystem.interfaces.IDataAccessController;

public interface ICommand {
	public void execute( IDataAccessController controller );
}
