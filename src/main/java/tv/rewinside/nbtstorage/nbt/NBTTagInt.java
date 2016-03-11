package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase.NBTNumber {

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
		nbtreadlimiter.a(32L);
		this.data = datainput.readInt();
	}

	@Override
	public byte getTypeId() {
		return (byte) 3;
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

	@Override
	public long c() {
		return (long) this.data;
	}

	@Override
	public int d() {
		return this.data;
	}

	@Override
	public short e() {
		return (short) (this.data & '\uffff');
	}

	@Override
	public byte f() {
		return (byte) (this.data & 255);
	}

	@Override
	public double g() {
		return (double) this.data;
	}

	@Override
	public float h() {
		return (float) this.data;
	}
}
