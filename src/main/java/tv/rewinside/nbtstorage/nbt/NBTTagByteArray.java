package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import tv.rewinside.nbtstorage.exceptions.NBTLoadException;

public class NBTTagByteArray extends NBTBase {

	private byte[] data;

	NBTTagByteArray() {
	}

	public NBTTagByteArray(byte[] abyte) {
		this.data = abyte;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeInt(this.data.length);
		dataoutput.write(this.data);
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		int j = datainput.readInt();
		if (j < 1 << 24) throw new NBTLoadException("Error while loading ByteArray");

		nbtreadlimiter.a((long) (8 * j));
		this.data = new byte[j];
		datainput.readFully(this.data);
	}

	@Override
	public byte getTypeId() {
		return (byte) 7;
	}

	@Override
	public String toString() {
		return "[" + this.data.length + " bytes]";
	}

	@Override
	public NBTBase clone() {
		byte[] abyte = new byte[this.data.length];

		System.arraycopy(this.data, 0, abyte, 0, this.data.length);
		return new NBTTagByteArray(abyte);
	}

	@Override
	public boolean equals(Object object) {
		return super.equals(object) ? Arrays.equals(this.data, ((NBTTagByteArray) object).data) : false;
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.data);
	}

	public byte[] c() {
		return this.data;
	}
}
