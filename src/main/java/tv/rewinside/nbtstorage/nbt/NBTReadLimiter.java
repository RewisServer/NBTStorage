package tv.rewinside.nbtstorage.nbt;

public class NBTReadLimiter {

	public static final NBTReadLimiter NO_LIMIT = new NBTReadLimiter(0L) {
		@Override
		public void allocate(long bits) {
		}
	};
	private final long byteLimit;
	private long allocBytes;

	public NBTReadLimiter(long bitLimit) {
		this.byteLimit = bitLimit;
	}

	public void allocate(long bits) {
		this.allocBytes += bits / 8L;
		if (this.allocBytes > this.byteLimit) {
			throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.allocBytes + "bytes where max allowed: " + this.byteLimit);
		}
	}
}
