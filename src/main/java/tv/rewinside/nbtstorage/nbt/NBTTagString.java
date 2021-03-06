package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {

	private String data;

	public NBTTagString() {
		this.data = "";
	}

	public NBTTagString(String s) {
		this.data = s;
		if (s == null) {
			throw new IllegalArgumentException("Empty string not allowed");
		}
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeUTF(this.data);
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		this.data = datainput.readUTF();
		nbtreadlimiter.allocate((long) (16 * this.data.length()));
	}

	@Override
	public NBTType getType() {
		return NBTType.STRING;
	}
	
	@Override
	public String getData() {
		return this.data;
	}
	
	@Override
	public boolean isEmpty() {
		return this.data.isEmpty();
	}

	@Override
	public NBTBase clone() {
		return new NBTTagString(this.data);
	}
	
	@Override
	public boolean equals(Object object) {
		if (!super.equals(object)) {
			return false;
		} else {
			NBTTagString nbttagstring = (NBTTagString) object;

			return this.data == null && nbttagstring.data == null || this.data != null && this.data.equals(nbttagstring.data);
		}
	}
	
	@Override
	public String toString() {
		return "\"" + this.data.replace("\"", "\\\"") + "\"";
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ this.data.hashCode();
	}
}
