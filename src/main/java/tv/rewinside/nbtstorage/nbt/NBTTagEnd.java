package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase {

	NBTTagEnd() {
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
	}

	@Override
	public byte getTypeId() {
		return (byte) 0;
	}

	@Override
	public String toString() {
		return "END";
	}

	@Override
	public NBTBase clone() {
		return new NBTTagEnd();
	}
}
