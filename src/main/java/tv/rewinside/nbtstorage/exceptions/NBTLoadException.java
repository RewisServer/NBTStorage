package tv.rewinside.nbtstorage.exceptions;


public class NBTLoadException extends Error {

	public NBTLoadException(Throwable t) {
		super(t);
	}
	
	public NBTLoadException(String s) {
		super(s);
	}
	
	public NBTLoadException(String s, Throwable t) {
		super(s, t);
	}
	
}
