package tv.rewinside.nbtstorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import tv.rewinside.nbtstorage.annotations.Options;
import tv.rewinside.nbtstorage.nbt.NBTBase;
import tv.rewinside.nbtstorage.nbt.NBTTagList;
import tv.rewinside.nbtstorage.nbt.NBTTagLong;
import tv.rewinside.nbtstorage.nbt.NBTTagString;
import tv.rewinside.nbtstorage.nbt.NBTType;

public class TestSchematic implements NBTFileSchematic {

	private boolean bln;
	private int n;
	private byte b;
	private String str;
	private long l;
	private short s;
	private double d;
	@Options(readerMethod = "readGen", writerMethod = "writeGen")
	private String generated;
	private TestEnum tEnum;
	@Options(readerMethod = "readLongList", writerMethod = "writeLongList")
	private List<Long> longList;

	public TestSchematic() {

	}

	public TestSchematic(boolean bln, int n, byte b, String str, long l, short s, double d, String generated, TestEnum tEnum, Long[] longList) {
		this.bln = bln;
		this.n = n;
		this.b = b;
		this.str = str;
		this.l = l;
		this.s = s;
		this.d = d;
		this.generated = generated;
		this.tEnum = tEnum;
		this.longList = Arrays.asList(longList);
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

	public void readLongList(NBTBase base) {
		if (base.getType() != NBTType.LIST) return;
		NBTTagList list = (NBTTagList) base;

		ArrayList<Long> longList = new ArrayList<>();

		if (list.getListType() != NBTType.LONG) return;
		list.getData().stream().filter((tag) -> !(tag.getType() != NBTType.LONG)).forEach((tag) -> {
			longList.add(((NBTTagLong) tag).getLong());
		});
		
		this.longList = longList;
	}
	
	public NBTTagList writeLongList() {
		NBTTagList list = new NBTTagList();
		this.longList.stream().forEach((l) -> {
			list.add(new NBTTagLong(l));
		});
		
		return list;
	}

	@Override
	public double getVersion() {
		return 1.0;
	}

	@Override
	public Collection<Double> getSupportedVersions() {
		return Arrays.asList(1.0);
	}

	@Override
	public String toString() {
		return "TestSchematic{" + "bln=" + bln + ", n=" + n + ", b=" + b + ", str=" + str + ", l=" + l + ", s=" + s + ", d=" + d + ", generated=" + generated + ", tEnum=" + tEnum + ", longList=" + longList + '}';
	}

	public enum TestEnum {
		BANANA,
		APPLE,
		ORANGE;
	}

}
