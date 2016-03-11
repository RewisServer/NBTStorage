package tv.rewinside.nbtstorage;

import java.io.File;
import java.util.Random;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;


public class TestClass {
	
	private final Random rand = new Random();
	
	@Test
	public void testNBT() {
		NBTStorage storage = new NBTStorage();
		File file = new File("test.nbt");
		
		TestSchematic test1 = new TestSchematic(this.rand.nextBoolean(), this.rand.nextInt(), (byte) this.rand.nextInt(256), this.randomString(100), this.rand.nextLong(), (short) this.rand.nextInt(Short.MAX_VALUE), "Test " + this.randomString(15) + " Test");
		storage.toNBT(test1, file);
		
		TestSchematic test2 = (TestSchematic) storage.fromNBT(file, TestSchematic.class);
		assertThat("Test1's String", test1.toString(), is(test2.toString()));
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
