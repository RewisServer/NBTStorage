package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import tv.rewinside.nbtstorage.exceptions.NBTLoadException;

public class NBTTagIntArray extends NBTBase {

	private int[] data;

	NBTTagIntArray() {
	}

	public NBTTagIntArray(int[] aint) {
		this.data = aint;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeInt(this.data.length);

		for (int i = 0; i < this.data.length; ++i) {
			dataoutput.writeInt(this.data[i]);
		}

	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		int j = datainput.readInt();
		if (j >= 1 << 24) throw new NBTLoadException("Error while loading IntArray");

		nbtreadlimiter.allocate((long) (32 * j));
		this.data = new int[j];

		for (int k = 0; k < j; ++k) {
			this.data[k] = datainput.readInt();
		}

	}

	@Override
	public NBTType getType() {
		return NBTType.INT_ARRAY;
	}
	
	@Override
	public int[] getData() {
		return this.data;
	}

	@Override
	public NBTBase clone() {
		int[] aint = new int[this.data.length];

		System.arraycopy(this.data, 0, aint, 0, this.data.length);
		return new NBTTagIntArray(aint);
	}
	
	@Override
	public boolean equals(Object object) {
		return super.equals(object) ? Arrays.equals(this.data, ((NBTTagIntArray) object).data) : false;
	}
	
	@Override
	public String toString() {
		String s = "[";
		int[] aint = this.data;
		int i = aint.length;

		for (int j = 0; j < i; ++j) {
			int k = aint[j];

			s = s + k + ",";
		}

		return s + "]";
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.data);
	}
}
