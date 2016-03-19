package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import tv.rewinside.nbtstorage.nbt.NBTBase.NBTNumber;

public class NBTTagDouble extends NBTNumber {

	private double data;

	NBTTagDouble() {
	}

	public NBTTagDouble(double d0) {
		this.data = d0;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeDouble(this.data);
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		nbtreadlimiter.allocate(64L);
		this.data = datainput.readDouble();
	}

	@Override
	public NBTType getType() {
		return NBTType.DOUBLE;
	}
	
	@Override
	protected Number getNumber() {
		return this.data;
	}

	@Override
	public NBTBase clone() {
		return new NBTTagDouble(this.data);
	}
	
	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			NBTTagDouble nbttagdouble = (NBTTagDouble) object;

			return this.data == nbttagdouble.data;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "" + this.data + "d";
	}
	
	@Override
	public int hashCode() {
		long i = Double.doubleToLongBits(this.data);

		return super.hashCode() ^ (int) (i ^ i >>> 32);
	}

}
