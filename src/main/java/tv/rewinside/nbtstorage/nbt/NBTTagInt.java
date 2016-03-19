package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import tv.rewinside.nbtstorage.nbt.NBTBase.NBTNumber;

public class NBTTagInt extends NBTNumber {

	private int data;

	NBTTagInt() {
	}

	public NBTTagInt(int i) {
		this.data = i;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeInt(this.data);
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		nbtreadlimiter.allocate(32L);
		this.data = datainput.readInt();
	}

	@Override
	public NBTType getType() {
		return NBTType.INT;
	}
	
	@Override
	protected Number getNumber() {
		return this.data;
	}

	@Override
	public String toString() {
		return "" + this.data;
	}

	@Override
	public NBTBase clone() {
		return new NBTTagInt(this.data);
	}

	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			NBTTagInt nbttagint = (NBTTagInt) object;

			return this.data == nbttagint.data;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ this.data;
	}
}
