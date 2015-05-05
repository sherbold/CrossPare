package de.ugoe.cs.cpdp.loader;

import java.util.List;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

public interface IDecentVersionLoader extends IVersionLoader{
	
	public List<SoftwareVersion> load(List<String> decentAttributes);

}
