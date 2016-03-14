package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase.NBTNumber {

	private byte data;

	NBTTagByte() {
	}

	public NBTTagByte(byte b0) {
		this.data = b0;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeByte(this.data);
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		nbtreadlimiter.allocate(8L);
		this.data = datainput.readByte();
	}

	@Override
	public NBTType getType() {
		return NBTType.BYTE;
	}

	@Override
	protected Number getNumber() {
		return this.data;
	}

	@Override
	public String toString() {
		return "" + this.data + "b";
	}

	@Override
	public NBTBase clone() {
		return new NBTTagByte(this.data);
	}

	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			NBTTagByte nbttagbyte = (NBTTagByte) object;

			return this.data == nbttagbyte.data;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ this.data;
	}
}
