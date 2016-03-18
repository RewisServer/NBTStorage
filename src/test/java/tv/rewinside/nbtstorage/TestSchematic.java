package tv.rewinside.nbtstorage;

import tv.rewinside.nbtstorage.annotations.Options;
import tv.rewinside.nbtstorage.nbt.NBTBase;
import tv.rewinside.nbtstorage.nbt.NBTTagString;

public class TestSchematic implements NBTFileSchematic {

	private boolean bln;
	private int n;
	private byte b;
	private String str;
	private long l;
	private short s;
	@Options(readerMethod = "readGen", writerMethod = "writeGen")
	private String generated;
	private TestEnum tEnum;

	public TestSchematic() {

	}

	public TestSchematic(boolean bln, int n, byte b, String str, long l, short s, String generated, TestEnum tEnum) {
		this.bln = bln;
		this.n = n;
		this.b = b;
		this.str = str;
		this.l = l;
		this.s = s;
		this.tEnum = tEnum;
		this.generated = generated;
	}

	public void setBln(boolean bln) {
		this.bln = bln;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setB(byte b) {
		this.b = b;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public void setL(long l) {
		this.l = l;
	}

	public void setS(short s) {
		this.s = s;
	}

	public void setEnum(TestEnum tEnum) {
		this.tEnum = tEnum;
	}

	public void setGenerated(String generated) {
		this.generated = generated;
	}

	public void readGen(NBTBase base) {
		String value = ((NBTTagString) base).getData();

		this.generated = "Test " + value + " Test";
	}

	public NBTTagString writeGen() {
		return new NBTTagString(this.generated.split(" ")[1]);
	}

	@Override
	public String toString() {
		return "TestSchematic{" + "bln=" + bln + ", n=" + n + ", b=" + b + ", str=" + str + ", l=" + l + ", s=" + s + ", generated=" + generated + ", tEnum=" + tEnum + '}';
	}

	public enum TestEnum {
		BANANA,
		APPLE,
		ORANGE;
	}

}
