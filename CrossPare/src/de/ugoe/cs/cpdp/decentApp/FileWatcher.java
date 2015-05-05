package de.ugoe.cs.cpdp.decentApp;

import java.util.*;
import java.io.*;

/**
 * Helper class for watching if a file was changed
 * 
 * @author Philip Makedonski
 *
 */
public abstract class FileWatcher extends TimerTask {
	// Last timestamp
	private long timeStamp;
	
	// File to watch
	private File file;

	/**
	 * Constructor
	 * @param file
	 */
	public FileWatcher(File file) {
		this.file = file;
		this.timeStamp = file.lastModified();
	}

	/**
	 * Watches a file and executes the onChange Method
	 * if a file is changed
	 */
	public final void run() {
		long timeStamp = file.lastModified();

		if (this.timeStamp != timeStamp) {
			this.timeStamp = timeStamp;
			onChange(file);
		}
	}

	protected abstract void onChange(File file);
}