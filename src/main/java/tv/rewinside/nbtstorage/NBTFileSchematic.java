package tv.rewinside.nbtstorage;

import java.util.Collection;


public interface NBTFileSchematic {

	public double getVersion();
	
	public Collection<Double> getSupportedVersions();
	
}
