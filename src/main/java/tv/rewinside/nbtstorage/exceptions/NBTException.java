package tv.rewinside.nbtstorage.exceptions;


public class NBTException extends Error {

	public NBTException(String s) {
		super(s);
	}
	
	public NBTException(Throwable t) {
		super(t);
	}
	
	public NBTException(String s, Throwable t) {
		super(s, t);
	}
	
}
