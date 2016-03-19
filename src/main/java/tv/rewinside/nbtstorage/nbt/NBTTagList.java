package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tv.rewinside.nbtstorage.exceptions.NBTException;

public class NBTTagList extends NBTBase {

	private List<NBTBase> list = new ArrayList<>();
	private NBTType type = NBTType.END;

	public NBTTagList() {
	}
	
	public NBTTagList(NBTType type, List<NBTBase> values) {
		this.type = type;
		this.list = values;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		if (!this.list.isEmpty()) {
			this.type = ((NBTBase) this.list.get(0)).getType();
		} else {
			this.type = NBTType.END;
		}

		dataoutput.writeByte(this.type.toByte());
		dataoutput.writeInt(this.list.size());

		for (int i = 0; i < this.list.size(); ++i) {
			((NBTBase) this.list.get(i)).write(dataoutput);
		}

	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		if (i > 512) {
			throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
		} else {
			nbtreadlimiter.allocate(8L);
			this.type = NBTType.fromByte(datainput.readByte());
			int j = datainput.readInt();
			nbtreadlimiter.allocate(j * 8); // CraftBukkit

			if ((this.type == NBTType.END) && (j > 0)) {
				throw new RuntimeException("Missing type on ListTag");
			}
			nbtreadlimiter.allocate(32L * j);

			this.list = new ArrayList<>(j);

			for (int k = 0; k < j; k++) {
				NBTBase nbtbase = this.type.newInstance();

				nbtbase.load(datainput, i + 1, nbtreadlimiter);
				this.list.add(nbtbase);
			}

		}
	}

	@Override
	public NBTType getType() {
		return NBTType.LIST;
	}
	
	@Override
	public List<NBTBase> getData() {
		return this.list;
	}

	public void add(NBTBase nbtbase) {
		if (this.type == NBTType.END) {
			this.type = nbtbase.getType();
		} else if (this.type != nbtbase.getType()) {
			throw new NBTException("Adding mismatching type to NBTTagList");
		}

		this.list.add(nbtbase);
	}

	public void a(int i, NBTBase nbtbase) {
		if (i >= 0 && i < this.list.size()) {
			if (this.type == NBTType.END) {
				this.type = nbtbase.getType();
			} else if (this.type != nbtbase.getType()) {
				throw new NBTException("Adding mismatching type to NBTTagList");
			}

			this.list.set(i, nbtbase);
		} else {
			throw new NBTException("Adding mismatching type to NBTTagList");
		}
	}

	public NBTBase remove(int i) {
		return (NBTBase) this.list.remove(i);
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public NBTTagCompound getCompound(int index) {
		if (index >= 0 && index < this.list.size()) {
			NBTBase nbtbase = (NBTBase) this.list.get(index);

			return nbtbase.getType() == NBTType.COMPOUND ? (NBTTagCompound) nbtbase : new NBTTagCompound();
		} else {
			return new NBTTagCompound();
		}
	}

	public int[] getIntArray(int index) {
		if (index >= 0 && index < this.list.size()) {
			NBTBase nbtbase = (NBTBase) this.list.get(index);

			return nbtbase.getType() == NBTType.INT_ARRAY ? ((NBTTagIntArray) nbtbase).getData() : new int[0];
		} else {
			return new int[0];
		}
	}

	public double getDouble(int index) {
		if (index >= 0 && index < this.list.size()) {
			NBTBase nbtbase = (NBTBase) this.list.get(index);

			return nbtbase.getType() == NBTType.DOUBLE ? ((NBTTagDouble) nbtbase).getDouble() : 0.0D;
		} else {
			return 0.0D;
		}
	}

	public float getFloat(int index) {
		if (index >= 0 && index < this.list.size()) {
			NBTBase nbtbase = (NBTBase) this.list.get(index);

			return nbtbase.getType() == NBTType.FLOAT ? ((NBTTagFloat) nbtbase).getFloat() : 0.0F;
		} else {
			return 0.0F;
		}
	}

	public String getString(int index) {
		if (index >= 0 && index < this.list.size()) {
			NBTBase nbtbase = (NBTBase) this.list.get(index);

			return nbtbase.getType() == NBTType.STRING ? ((NBTTagString) nbtbase).getData() : nbtbase.toString();
		} else {
			return "";
		}
	}

	public NBTBase get(int index) {
		return (NBTBase) (index >= 0 && index < this.list.size() ? (NBTBase) this.list.get(index) : new NBTTagEnd());
	}

	public int size() {
		return this.list.size();
	}

	@Override
	public NBTBase clone() {
		NBTTagList nbttaglist = new NBTTagList();

		nbttaglist.type = this.type;
		Iterator iterator = this.list.iterator();

		while (iterator.hasNext()) {
			NBTBase nbtbase = (NBTBase) iterator.next();
			NBTBase nbtbase1 = nbtbase.clone();

			nbttaglist.list.add(nbtbase1);
		}

		return nbttaglist;
	}

	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			NBTTagList nbttaglist = (NBTTagList) object;

			if (this.type == nbttaglist.type) {
				return this.list.equals(nbttaglist.list);
			}
		}

		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder stringbuilder = new StringBuilder("[");

		for (int i = 0; i < this.list.size(); ++i) {
			if (i != 0) {
				stringbuilder.append(',');
			}

			stringbuilder.append(i).append(':').append(this.list.get(i));
		}

		return stringbuilder.append(']').toString();
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ this.list.hashCode();
	}
}
