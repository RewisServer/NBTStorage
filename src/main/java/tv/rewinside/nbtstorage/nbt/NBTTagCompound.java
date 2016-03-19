package tv.rewinside.nbtstorage.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import tv.rewinside.nbtstorage.exceptions.NBTLoadException;
import tv.rewinside.nbtstorage.exceptions.NBTReadException;

public class NBTTagCompound extends NBTBase {

	private final Map<String, NBTBase> map;

	public NBTTagCompound() {
		this.map = new HashMap<>();
	}
	
	public NBTTagCompound(Map<String, NBTBase> map) {
		this.map = map;
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		for (String key : this.map.keySet()) {
			NBTBase nbtbase = (NBTBase) this.map.get(key);
			writeTag(key, nbtbase, dataoutput);
		}

		dataoutput.writeByte(NBTType.END.toByte());
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		if (i > 512) {
			throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
		} else {
			this.map.clear();

			NBTType type;

			while ((type = NBTType.fromByte(readByte(datainput, nbtreadlimiter))) != NBTType.END) {
				String key = readString(datainput, nbtreadlimiter);

				nbtreadlimiter.allocate((long) (16 * key.length()));
				NBTBase nbtbase = readTag(type, key, datainput, i + 1, nbtreadlimiter);

				this.map.put(key, nbtbase);
			}

		}
	}

	public Set<String> keySet() {
		return this.map.keySet();
	}

	@Override
	public NBTType getType() {
		return NBTType.COMPOUND;
	}
	
	@Override
	public NBTTagCompound getData() {
		return this;
	}

	public void set(String key, NBTBase value) {
		this.map.put(key, value);
	}

	public void setByte(String key, byte value) {
		this.map.put(key, new NBTTagByte(value));
	}

	public void setShort(String key, short value) {
		this.map.put(key, new NBTTagShort(value));
	}

	public void setInt(String key, int value) {
		this.map.put(key, new NBTTagInt(value));
	}

	public void setLong(String key, long value) {
		this.map.put(key, new NBTTagLong(value));
	}

	public void setFloat(String key, float value) {
		this.map.put(key, new NBTTagFloat(value));
	}

	public void setDouble(String key, double value) {
		this.map.put(key, new NBTTagDouble(value));
	}

	public void setString(String key, String value) {
		this.map.put(key, new NBTTagString(value));
	}

	public void setByteArray(String key, byte[] value) {
		this.map.put(key, new NBTTagByteArray(value));
	}

	public void setIntArray(String key, int[] value) {
		this.map.put(key, new NBTTagIntArray(value));
	}

	public void setBoolean(String key, boolean value) {
		this.setByte(key, (byte) (value ? 1 : 0));
	}

	public NBTBase get(String key) {
		return (NBTBase) this.map.get(key);
	}

	public NBTType typeOf(String key) {
		NBTBase nbtbase = (NBTBase) this.map.get(key);

		return nbtbase != null ? nbtbase.getType() : NBTType.END;
	}

	public boolean hasKey(String s) {
		return this.map.containsKey(s);
	}
	
	public boolean isNumber(String key) {
		return this.typeOf(key).isNumber();
	}

	public byte getByte(String key) {
		if (!this.isNumber(key)) return 0;
		NBTNumber number = (NBTNumber) this.map.get(key);
		
		try {
			return number.getByte();
		} catch (ClassCastException ex) {
			return 0;
		}
	}

	public short getShort(String key) {
		if (!this.isNumber(key)) return 0;
		NBTNumber number = (NBTNumber) this.map.get(key);
		
		try {
			return number.getShort();
		} catch (ClassCastException ex) {
			return 0;
		}
	}

	public int getInt(String key) {
		if (!this.isNumber(key)) return 0;
		NBTNumber number = (NBTNumber) this.map.get(key);
		
		try {
			return number.getInt();
		} catch (ClassCastException ex) {
			return 0;
		}
	}

	public long getLong(String key) {
		if (!this.isNumber(key)) return 0;
		NBTNumber number = (NBTNumber) this.map.get(key);
		
		try {
			return number.getLong();
		} catch (ClassCastException ex) {
			return 0;
		}
	}

	public float getFloat(String key) {
		if (!this.isNumber(key)) return 0;
		NBTNumber number = (NBTNumber) this.map.get(key);
		
		try {
			return number.getFloat();
		} catch (ClassCastException ex) {
			return 0;
		}
	}

	public double getDouble(String key) {
		if (!this.isNumber(key)) return 0;
		NBTNumber number = (NBTNumber) this.map.get(key);
		
		try {
			return number.getDouble();
		} catch (ClassCastException ex) {
			return 0;
		}
	}

	public String getString(String key) {
		if (this.typeOf(key) != NBTType.STRING) return "";
		return ((NBTTagString) this.map.get(key)).getData();
	}

	public byte[] getByteArray(String key) {
		if (this.typeOf(key) != NBTType.BYTE_ARRAY) return new byte[0];
		return ((NBTTagByteArray) this.map.get(key)).getData();
	}

	public int[] getIntArray(String key) {
		if (this.typeOf(key) != NBTType.INT_ARRAY) return new int[0];
		return ((NBTTagIntArray) this.map.get(key)).getData();
	}

	public NBTTagCompound getCompound(String key) {
		if (this.typeOf(key) != NBTType.COMPOUND) return new NBTTagCompound();
		return (NBTTagCompound) this.map.get(key);
	}
	
	public NBTTagList getList(String key, NBTType type) {
		try {
			if (this.typeOf(key) != NBTType.LIST) {
				return new NBTTagList();
			} else {
				NBTTagList nbttaglist = (NBTTagList) this.map.get(key);

				return nbttaglist.size() > 0 && nbttaglist.getType() != type ? new NBTTagList() : nbttaglist;
			}
		} catch (ClassCastException classcastexception) {
			throw new NBTReadException(classcastexception);
		}
	}

	public boolean getBoolean(String key) {
		return this.getByte(key) != 0;
	}

	public void remove(String s) {
		this.map.remove(s);
	}

	@Override
	public String toString() {
		StringBuilder stringbuilder = new StringBuilder("{");

		Entry entry;

		for (Iterator iterator = this.map.entrySet().iterator(); iterator.hasNext(); stringbuilder.append((String) entry.getKey()).append(':').append(entry.getValue())) {
			entry = (Entry) iterator.next();
			if (stringbuilder.length() != 1) {
				stringbuilder.append(',');
			}
		}

		return stringbuilder.append('}').toString();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	static NBTBase readTag(NBTType type, String s, DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		NBTBase nbtbase = type.newInstance();

		try {
			nbtbase.load(datainput, i, nbtreadlimiter);
			return nbtbase;
		} catch (IOException ioexception) {
			throw new NBTLoadException(ioexception);
		}
	}
	
	private static void writeTag(String s, NBTBase nbtbase, DataOutput dataoutput) throws IOException {
		dataoutput.writeByte(nbtbase.getType().toByte());
		if (nbtbase.getType() != NBTType.END) {
			dataoutput.writeUTF(s);
			nbtbase.write(dataoutput);
		}
	}

	private static byte readByte(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
		return datainput.readByte();
	}

	private static String readString(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
		return datainput.readUTF();
	}

	public void merge(NBTTagCompound compound) {
		compound.map.entrySet().stream().forEach((e) -> {
			String key = e.getKey();
			NBTBase nbtbase = e.getValue();

			if (nbtbase.getType() == NBTType.COMPOUND) {
				// Compound
				if (this.typeOf(key) == NBTType.COMPOUND) {
					// Combine the two compounds
					NBTTagCompound thisCompound = this.getCompound(key);
					thisCompound.merge((NBTTagCompound) nbtbase);
				} else {
					this.set(key, nbtbase.clone());
				}
			} else {
				this.set(key, nbtbase.clone());
			}
		});
	}
	
	@Override
	public NBTBase clone() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		Iterator iterator = this.map.keySet().iterator();

		while (iterator.hasNext()) {
			String s = (String) iterator.next();

			nbttagcompound.set(s, ((NBTBase) this.map.get(s)).clone());
		}

		return nbttagcompound;
	}

	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			NBTTagCompound nbttagcompound = (NBTTagCompound) object;

			return this.map.entrySet().equals(nbttagcompound.map.entrySet());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ this.map.hashCode();
	}
}
