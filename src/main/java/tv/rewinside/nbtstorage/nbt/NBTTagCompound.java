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

	private Map<String, NBTBase> map = new HashMap<>();

	public NBTTagCompound() {
	}

	@Override
	void write(DataOutput dataoutput) throws IOException {
		Iterator iterator = this.map.keySet().iterator();

		while (iterator.hasNext()) {
			String s = (String) iterator.next();
			NBTBase nbtbase = (NBTBase) this.map.get(s);

			a(s, nbtbase, dataoutput);
		}

		dataoutput.writeByte(0);
	}

	@Override
	void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		if (i > 512) {
			throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
		} else {
			this.map.clear();

			byte b0;

			while ((b0 = a(datainput, nbtreadlimiter)) != 0) {
				String s = b(datainput, nbtreadlimiter);

				nbtreadlimiter.a((long) (16 * s.length()));
				NBTBase nbtbase = a(b0, s, datainput, i + 1, nbtreadlimiter);

				this.map.put(s, nbtbase);
			}

		}
	}

	public Set<String> c() {
		return this.map.keySet();
	}

	@Override
	public byte getTypeId() {
		return (byte) 10;
	}

	public void set(String s, NBTBase nbtbase) {
		this.map.put(s, nbtbase);
	}

	public void setByte(String s, byte b0) {
		this.map.put(s, new NBTTagByte(b0));
	}

	public void setShort(String s, short short0) {
		this.map.put(s, new NBTTagShort(short0));
	}

	public void setInt(String s, int i) {
		this.map.put(s, new NBTTagInt(i));
	}

	public void setLong(String s, long i) {
		this.map.put(s, new NBTTagLong(i));
	}

	public void setFloat(String s, float f) {
		this.map.put(s, new NBTTagFloat(f));
	}

	public void setDouble(String s, double d0) {
		this.map.put(s, new NBTTagDouble(d0));
	}

	public void setString(String s, String s1) {
		this.map.put(s, new NBTTagString(s1));
	}

	public void setByteArray(String s, byte[] abyte) {
		this.map.put(s, new NBTTagByteArray(abyte));
	}

	public void setIntArray(String s, int[] aint) {
		this.map.put(s, new NBTTagIntArray(aint));
	}

	public void setBoolean(String s, boolean flag) {
		this.setByte(s, (byte) (flag ? 1 : 0));
	}

	public NBTBase get(String s) {
		return (NBTBase) this.map.get(s);
	}

	public byte b(String s) {
		NBTBase nbtbase = (NBTBase) this.map.get(s);

		return nbtbase != null ? nbtbase.getTypeId() : 0;
	}

	public boolean hasKey(String s) {
		return this.map.containsKey(s);
	}

	public boolean hasKeyOfType(String s, int i) {
		byte b0 = this.b(s);

		if (b0 == i) {
			return true;
		} else if (i != 99) {
			if (b0 > 0) {
				;
			}

			return false;
		} else {
			return b0 == 1 || b0 == 2 || b0 == 3 || b0 == 4 || b0 == 5 || b0 == 6;
		}
	}

	public byte getByte(String s) {
		try {
			return !this.hasKeyOfType(s, 99) ? 0 : ((NBTBase.NBTNumber) this.map.get(s)).f();
		} catch (ClassCastException classcastexception) {
			return (byte) 0;
		}
	}

	public short getShort(String s) {
		try {
			return !this.hasKeyOfType(s, 99) ? 0 : ((NBTBase.NBTNumber) this.map.get(s)).e();
		} catch (ClassCastException classcastexception) {
			return (short) 0;
		}
	}

	public int getInt(String s) {
		try {
			return !this.hasKeyOfType(s, 99) ? 0 : ((NBTBase.NBTNumber) this.map.get(s)).d();
		} catch (ClassCastException classcastexception) {
			return 0;
		}
	}

	public long getLong(String s) {
		try {
			return !this.hasKeyOfType(s, 99) ? 0L : ((NBTBase.NBTNumber) this.map.get(s)).c();
		} catch (ClassCastException classcastexception) {
			return 0L;
		}
	}

	public float getFloat(String s) {
		try {
			return !this.hasKeyOfType(s, 99) ? 0.0F : ((NBTBase.NBTNumber) this.map.get(s)).h();
		} catch (ClassCastException classcastexception) {
			return 0.0F;
		}
	}

	public double getDouble(String s) {
		try {
			return !this.hasKeyOfType(s, 99) ? 0.0D : ((NBTBase.NBTNumber) this.map.get(s)).g();
		} catch (ClassCastException classcastexception) {
			return 0.0D;
		}
	}

	public String getString(String s) {
		try {
			return !this.hasKeyOfType(s, 8) ? "" : ((NBTBase) this.map.get(s)).a_();
		} catch (ClassCastException classcastexception) {
			return "";
		}
	}

	public byte[] getByteArray(String s) {
		try {
			return !this.hasKeyOfType(s, 7) ? new byte[0] : ((NBTTagByteArray) this.map.get(s)).c();
		} catch (ClassCastException classcastexception) {
			throw new NBTReadException(classcastexception);
		}
	}

	public int[] getIntArray(String s) {
		try {
			return !this.hasKeyOfType(s, 11) ? new int[0] : ((NBTTagIntArray) this.map.get(s)).c();
		} catch (ClassCastException classcastexception) {
			throw new NBTReadException(classcastexception);
		}
	}

	public NBTTagCompound getCompound(String s) {
		try {
			return !this.hasKeyOfType(s, 10) ? new NBTTagCompound() : (NBTTagCompound) this.map.get(s);
		} catch (ClassCastException classcastexception) {
			throw new NBTReadException(classcastexception);
		}
	}

	public boolean getBoolean(String s) {
		return this.getByte(s) != 0;
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

	static NBTBase a(byte b0, String s, DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		NBTBase nbtbase = NBTBase.createTag(b0);

		try {
			nbtbase.load(datainput, i, nbtreadlimiter);
			return nbtbase;
		} catch (IOException ioexception) {
			throw new NBTLoadException(ioexception);
		}
	}
	
	private static void a(String s, NBTBase nbtbase, DataOutput dataoutput) throws IOException {
		dataoutput.writeByte(nbtbase.getTypeId());
		if (nbtbase.getTypeId() != 0) {
			dataoutput.writeUTF(s);
			nbtbase.write(dataoutput);
		}
	}

	private static byte a(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
		return datainput.readByte();
	}

	private static String b(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
		return datainput.readUTF();
	}

	public void a(NBTTagCompound compound) {
		for (Entry<String, NBTBase> e : compound.map.entrySet()) {
			String key = e.getKey();
			NBTBase nbtbase = e.getValue();

			if (nbtbase.getTypeId() == 10) {
				// Compound
				if (this.hasKeyOfType(key, 10)) {
					// Combine the two compounds
					NBTTagCompound thisCompound = this.getCompound(key);
					thisCompound.a((NBTTagCompound) nbtbase);
				} else {
					this.set(key, nbtbase.clone());
				}
			} else {
				this.set(key, nbtbase.clone());
			}
		}
	}
}
