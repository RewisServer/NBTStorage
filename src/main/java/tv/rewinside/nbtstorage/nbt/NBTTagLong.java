package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase.NBTNumber {

	private long data;

	NBTTagLong() {
	}

	public NBTTagLong(long i) {
		this.data = i;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeLong(this.data);
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		nbtreadlimiter.allocate(64L);
		this.data = datainput.readLong();
	}

	@Override
	public NBTType getType() {
		return NBTType.LONG;
	}
	
	@Override
	protected Number getNumber() {
		return this.data;
	}

	@Override
	public String toString() {
		return "" + this.data + "L";
	}

	@Override
	public NBTBase clone() {
		return new NBTTagLong(this.data);
	}

	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			NBTTagLong nbttaglong = (NBTTagLong) object;

			return this.data == nbttaglong.data;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ (int) (this.data ^ this.data >>> 32);
	}
}
