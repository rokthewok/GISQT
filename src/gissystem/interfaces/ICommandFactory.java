package gissystem.interfaces;

public interface ICommandFactory {
	public ICommand build( String rawCommand );
}
