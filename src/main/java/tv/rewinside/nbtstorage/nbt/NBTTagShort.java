package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase.NBTNumber {

	private short data;

	public NBTTagShort() {
	}

	public NBTTagShort(short short0) {
		this.data = short0;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeShort(this.data);
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		nbtreadlimiter.a(16L);
		this.data = datainput.readShort();
	}

	@Override
	public byte getTypeId() {
		return (byte) 2;
	}

	@Override
	public String toString() {
		return "" + this.data + "s";
	}

	@Override
	public NBTBase clone() {
		return new NBTTagShort(this.data);
	}

	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			NBTTagShort nbttagshort = (NBTTagShort) object;

			return this.data == nbttagshort.data;
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
		return this.data;
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
