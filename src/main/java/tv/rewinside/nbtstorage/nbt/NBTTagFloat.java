package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase.NBTNumber {

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
		nbtreadlimiter.a(32L);
		this.data = datainput.readFloat();
	}

	@Override
	public byte getTypeId() {
		return (byte) 5;
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

	@Override
	public long c() {
		return (long) this.data;
	}

	@Override
	public int d() {
		return MathHelper.d(this.data);
	}

	@Override
	public short e() {
		return (short) (MathHelper.d(this.data) & '\uffff');
	}

	@Override
	public byte f() {
		return (byte) (MathHelper.d(this.data) & 255);
	}

	@Override
	public double g() {
		return (double) this.data;
	}

	@Override
	public float h() {
		return this.data;
	}
}
