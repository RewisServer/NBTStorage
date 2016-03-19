package tv.rewinside.nbtstorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import tv.rewinside.nbtstorage.annotations.*;
import tv.rewinside.nbtstorage.exceptions.NBTLoadException;
import tv.rewinside.nbtstorage.exceptions.NBTSaveException;
import tv.rewinside.nbtstorage.nbt.NBTBase;
import tv.rewinside.nbtstorage.nbt.NBTBase.NBTNumber;
import tv.rewinside.nbtstorage.nbt.NBTCompressedStreamTools;
import tv.rewinside.nbtstorage.nbt.NBTTagCompound;
import tv.rewinside.nbtstorage.nbt.NBTTagList;
import tv.rewinside.nbtstorage.nbt.NBTTagString;
import tv.rewinside.nbtstorage.nbt.NBTType;

public class NBTStorage {

	public NBTFileSchematic fromNBT(File file, Class<? extends NBTFileSchematic> schem) {
		NBTTagCompound compound;
		NBTFileSchematic instance;
		try {
			compound = this.read(file);
			instance = schem.newInstance();
		} catch (IOException | InstantiationException | IllegalAccessException ex) {
			throw new NBTLoadException("Error while loading NBT from File", ex);
		}
		
		double version = compound.getDouble("_VERSION");
		if (!instance.getSupportedVersions().contains(version)) {
			throw new NBTLoadException("Data Version of File is not supportet by the current Schematic!");
		}

		for (Field field : schem.getDeclaredFields()) {
			boolean preState = field.isAccessible();
			field.setAccessible(true);
			Options options = field.getAnnotation(Options.class);
			String key = (options != null && !options.nbtKey().isEmpty()) ? options.nbtKey() : field.getName();

			if (!compound.hasKey(key)) continue;

			try {
				if (options != null && !options.readerMethod().isEmpty()) {
					Method method = schem.getDeclaredMethod(options.readerMethod(), NBTBase.class);
					boolean mPreState = method.isAccessible();
					method.setAccessible(true);
					method.invoke(instance, compound.get(key));
					method.setAccessible(mPreState);
				} else {
					NBTBase baseTag = compound.get(key);
					Field dataField = baseTag.getClass().getDeclaredField("data");
					dataField.setAccessible(true);

					if (field.getType() == boolean.class) {
						field.set(instance, ((byte) dataField.get(baseTag)) != 0);
					} else if (field.getType() == dataField.getType()) {
						field.set(instance, dataField.get(baseTag));
					} else if (field.getType().isEnum() && baseTag.getType() == NBTType.STRING) {
						field.set(instance, Enum.valueOf((Class<Enum>) field.getType(), ((NBTTagString) baseTag).getData()));
					} else {
						throw new NBTLoadException("Invalid Type for field " + field.getName() + "(" + field.getType() + ":" + dataField.getType() + ")");
					}
				}
			} catch (SecurityException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException ex) {
				throw new NBTLoadException("Error while writing to Schematic Class!", ex);
			}

			field.setAccessible(preState);
		}

		return instance;
	}

	public void toNBT(NBTFileSchematic schem, File file) {
		NBTTagCompound compound = new NBTTagCompound();
		ClassOptions classOptions = schem.getClass().getAnnotation(ClassOptions.class);
		Field[] rawFields = schem.getClass().getDeclaredFields();
		Set<Field> fields = new HashSet<>();
		
		compound.setDouble("_VERSION", schem.getVersion());
		
		if (classOptions == null) {
			fields.addAll(Arrays.asList(rawFields));
		} else if (classOptions.filterType() != null) {
			switch (classOptions.filterType()) {
				case EXCLUDE:
					for (Field f : rawFields) {
						if (f.getAnnotation(Exclude.class) == null) {
							fields.add(f);
						}
					}
				case INCLUDE:
					for (Field f : rawFields) {
						if (f.getAnnotation(Include.class) != null) {
							fields.add(f);
						}
					}
					break;
				default:
					fields.addAll(Arrays.asList(rawFields));
					break;
			}
		}

		for (Field field : fields) {
			boolean preState = field.isAccessible();
			field.setAccessible(true);
			Options options = field.getAnnotation(Options.class);
			String key = (options != null && !options.nbtKey().isEmpty()) ? options.nbtKey() : field.getName();

			try {
				if (options != null && !options.writerMethod().isEmpty()) {
					Method processMethod = schem.getClass().getDeclaredMethod(options.writerMethod());
					boolean mPreState = processMethod.isAccessible();
					processMethod.setAccessible(true);
					if (processMethod.getReturnType().getSuperclass() == null || (processMethod.getReturnType().getSuperclass() != NBTBase.class && processMethod.getReturnType().getSuperclass() != NBTNumber.class)) {
						throw new NBTSaveException(String.format("Process Method for %s has an invalid Return Type", field.getName()));
					}

					if (processMethod.getParameterCount() != 0) {
						throw new NBTSaveException(String.format("Process Method for %s has an invalid parameter count", field.getName()));
					}

					NBTBase base = (NBTBase) processMethod.invoke(schem);
					compound.set(key, base);
					processMethod.setAccessible(mPreState);
				} else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
					compound.setBoolean(key, field.getBoolean(schem));
				} else if (field.getType() == Byte.class || field.getType() == byte.class) {
					if (field.getType().isArray()) {
						compound.setByteArray(key, (byte[]) field.get(schem));
					} else {
						compound.setByte(key, field.getByte(schem));
					}
				} else if (field.getType() == Double.class || field.getType() == double.class) {
					compound.setDouble(key, field.getDouble(schem));
				} else if (field.getType() == Float.class || field.getType() == float.class) {
					compound.setFloat(key, field.getFloat(schem));
				} else if (field.getType() == Integer.class || field.getType() == int.class) {
					if (field.getType().isArray()) {
						compound.setIntArray(key, (int[]) field.get(schem));
					} else {
						compound.setInt(key, field.getInt(schem));
					}
				} else if (field.getType() == Long.class || field.getType() == long.class) {
					compound.setLong(key, field.getLong(schem));
				} else if (field.getType() == Short.class || field.getType() == short.class) {
					compound.setShort(key, field.getShort(schem));
				} else if (field.getType() == String.class) {
					compound.setString(key, (String) field.get(schem));
				} else if (field.getType().isEnum()) {
					compound.setString(key, ((Enum)field.get(schem)).name());
				} else if (field.getType() == NBTTagList.class) { 
					compound.set(key, (NBTBase) field.get(schem));
				} else {
					throw new NBTSaveException(field.getName() + " is an unsupported data Type!");
				}

			} catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
				throw new NBTSaveException("Error while generating NBT File", ex);
			}

			field.setAccessible(preState);
		}

		try {
			this.write(compound, file);
		} catch (IOException ex) {
			throw new NBTSaveException("Error while saving to File", ex);
		}
	}

	private void write(NBTTagCompound compound, File file) throws IOException {
		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (!file.exists()) {
			file.createNewFile();
		}

		if (!file.isFile()) {
			throw new IOException("File is no File");
		}

		FileOutputStream stream = new FileOutputStream(file);
		NBTCompressedStreamTools.write(compound, stream);
	}

	private NBTTagCompound read(File file) throws IOException {
		if (!file.exists()) {
			throw new FileNotFoundException();
		}

		NBTTagCompound compound = NBTCompressedStreamTools.read(file.toURI().toURL().openStream());

		return compound;
	}

}
