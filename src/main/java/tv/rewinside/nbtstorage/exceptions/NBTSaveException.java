package tv.rewinside.nbtstorage.exceptions;


public class NBTSaveException extends Error {
	
	public NBTSaveException(Throwable t) {
		super(t);
	}
	
	public NBTSaveException(String s) {
		super(s);
	}
	
	public NBTSaveException(String s, Throwable t) {
		super(s, t);
	}
	
}
