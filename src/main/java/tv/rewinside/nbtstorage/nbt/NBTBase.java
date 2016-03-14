package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {

	protected NBTBase() {
	}

	abstract void write(DataOutput dataoutput) throws IOException;

	abstract void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException;

	public abstract NBTType getType();
	
	public abstract Object getData();

	public boolean isEmpty() {
		return false;
	}

	@Override
	public abstract NBTBase clone();

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof NBTBase)) {
			return false;
		} else {
			NBTBase nbtbase = (NBTBase) object;

			return this.getType() == nbtbase.getType();
		}
	}

	@Override
	public abstract String toString();

	@Override
	public int hashCode() {
		return this.getType().toByte();
	}

	public abstract static class NBTNumber extends NBTBase {

		protected abstract Number getNumber();
		
		@Override
		public Number getData() {
			return this.getNumber();
		}
		
		public byte getByte() {
			return (byte) this.getNumber();
		}

		public short getShort() {
			return (short) this.getNumber();
		}

		public int getInt() {
			return (int) this.getNumber();
		}

		public long getLong() {
			return (long) this.getNumber();
		}

		public float getFloat() {
			return (float) this.getNumber();
		}

		public double getDouble() {
			return (double) this.getNumber();
		}
	}
}
