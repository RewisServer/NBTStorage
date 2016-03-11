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
		nbtreadlimiter.a(64L);
		this.data = datainput.readLong();
	}

	@Override
	public byte getTypeId() {
		return (byte) 4;
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

	@Override
	public long c() {
		return this.data;
	}

	@Override
	public int d() {
		return (int) (this.data & -1L);
	}

	@Override
	public short e() {
		return (short) ((int) (this.data & 65535L));
	}

	@Override
	public byte f() {
		return (byte) ((int) (this.data & 255L));
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
