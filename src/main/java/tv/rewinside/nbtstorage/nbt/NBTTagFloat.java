package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import tv.rewinside.nbtstorage.nbt.NBTBase.NBTNumber;

public class NBTTagFloat extends NBTNumber {

	private float data;

	NBTTagFloat() {
	}

	public NBTTagFloat(float f) {
		this.data = f;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeFloat(this.data);
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		nbtreadlimiter.allocate(32L);
		this.data = datainput.readFloat();
	}

	@Override
	public NBTType getType() {
		return NBTType.FLOAT;
	}
	
	@Override
	protected Number getNumber() {
		return this.data;
	}

	@Override
	public String toString() {
		return "" + this.data + "f";
	}

	@Override
	public NBTBase clone() {
		return new NBTTagFloat(this.data);
	}

	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			NBTTagFloat nbttagfloat = (NBTTagFloat) object;

			return this.data == nbttagfloat.data;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ Float.floatToIntBits(this.data);
	}
}
