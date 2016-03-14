package tv.rewinside.nbtstorage.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import tv.rewinside.nbtstorage.exceptions.NBTReadException;

public class NBTCompressedStreamTools {

	public static NBTTagCompound read(InputStream inputstream) throws IOException {
		DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputstream)));

		NBTTagCompound nbttagcompound;

		try {
			nbttagcompound = readLimited((DataInput) datainputstream, NBTReadLimiter.NO_LIMIT);
		} finally {
			datainputstream.close();
		}

		return nbttagcompound;
	}

	public static void write(NBTTagCompound nbttagcompound, OutputStream outputstream) throws IOException {
		DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputstream)));

		try {
			writeCompound(nbttagcompound, (DataOutput) dataoutputstream);
		} finally {
			dataoutputstream.close();
		}

	}

	public static NBTTagCompound read(DataInputStream datainputstream) throws IOException {
		return readLimited((DataInput) datainputstream, NBTReadLimiter.NO_LIMIT);
	}

	public static NBTTagCompound readLimited(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
		NBTBase nbtbase = readTag(datainput, 0, nbtreadlimiter);

		if (nbtbase instanceof NBTTagCompound) {
			return (NBTTagCompound) nbtbase;
		} else {
			throw new IOException("Root tag must be a named compound tag");
		}
	}

	public static void writeCompound(NBTTagCompound nbttagcompound, DataOutput dataoutput) throws IOException {
		writeTag((NBTBase) nbttagcompound, dataoutput);
	}

	private static void writeTag(NBTBase nbtbase, DataOutput dataoutput) throws IOException {
		dataoutput.writeByte(nbtbase.getType().toByte());
		if (nbtbase.getType() != NBTType.END) {
			dataoutput.writeUTF("");
			nbtbase.write(dataoutput);
		}
	}

	private static NBTBase readTag(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
		byte b0 = datainput.readByte();

		if (b0 == 0) {
			return new NBTTagEnd();
		} else {
			datainput.readUTF();
			NBTBase nbtbase = NBTType.fromByte(b0).newInstance();

			try {
				nbtbase.load(datainput, i, nbtreadlimiter);
				return nbtbase;
			} catch (IOException ioexception) {
				throw new NBTReadException(ioexception);
			}
		}
	}
}
