package tv.rewinside.nbtstorage;

import java.io.File;
import java.util.Arrays;
import java.util.Random;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import tv.rewinside.nbtstorage.TestSchematic.TestEnum;


public class TestClass {
	
	private final Random rand = new Random();
	
	@Test
	public void testNBT() {
		File file = new File("test.nbt");
		
		Long[] longList = new Long[this.rand.nextInt(2500)];
		
		for (int i = 0; i < longList.length; i++) {
			longList[i] = this.rand.nextLong();
		}
		
		TestSchematic test1 = new TestSchematic(this.rand.nextBoolean(), this.rand.nextInt(), (byte) this.rand.nextInt(256), this.randomString(100), this.rand.nextLong(), (short) this.rand.nextInt(Short.MAX_VALUE), this.rand.nextDouble(), "Test " + this.randomString(15) + " Test", TestEnum.values()[this.rand.nextInt(TestEnum.values().length)], longList);
		NBTStorage.toNBT(test1, file);
		
		TestSchematic test2 = (TestSchematic) NBTStorage.fromNBT(file, TestSchematic.class);
		assertThat("Test1's String", test1.toString(), is(test2.toString()));
		
		System.out.println(test1.toString());
		System.out.println(test2.toString());
	}

	private String randomString(int length) {
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		
		String out = "";
		for (int i = 0; i <= length; i++) {
			out += chars[this.rand.nextInt(chars.length)];
		}
		
		return out;
	}
}
