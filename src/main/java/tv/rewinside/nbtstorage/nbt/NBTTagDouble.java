package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase.NBTNumber {

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
		nbtreadlimiter.a(64L);
		this.data = datainput.readDouble();
	}

	@Override
	public byte getTypeId() {
		return (byte) 6;
	}

	@Override
	public String toString() {
		return "" + this.data + "d";
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
	public int hashCode() {
		long i = Double.doubleToLongBits(this.data);

		return super.hashCode() ^ (int) (i ^ i >>> 32);
	}

	@Override
	public long c() {
		return (long) Math.floor(this.data);
	}

	@Override
	public int d() {
		return MathHelper.floor(this.data);
	}

	@Override
	public short e() {
		return (short) (MathHelper.floor(this.data) & '\uffff');
	}

	@Override
	public byte f() {
		return (byte) (MathHelper.floor(this.data) & 255);
	}

	@Override
	public double g() {
		return this.data;
	}

	@Override
	public float h() {
		return (float) this.data;
	}
}
