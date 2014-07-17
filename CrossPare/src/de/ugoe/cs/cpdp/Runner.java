package de.ugoe.cs.cpdp;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import de.ugoe.cs.util.console.Console;
import de.ugoe.cs.util.console.TextConsole;

/**
 * Executable that can be used to run experiments.
 * @author Steffen Herbold
 *
 */
public class Runner {
	
	/**
	 * Main class. The arguments are {@link ExperimentConfiguration} files. Each experiment is started in a separate thread. The number of concurrently running threads is the number of logical processors of the host system. 
	 * @param args experiment configuration files
	 */
	public static void main(String[] args) {
		new TextConsole(Level.FINE);
		
		final int concurrentThreads = Runtime.getRuntime().availableProcessors();
		final ExecutorService threadPool = Executors.newFixedThreadPool(concurrentThreads);
		for( String arg : args ) {
			File file = new File(arg);
			if( file.isFile() ) {
				createConfig(threadPool, file.getAbsolutePath());
			}
			else if( file.isDirectory() ) {
				for( File subfile : file.listFiles() ) {
					if( subfile.isFile() ) {
						createConfig(threadPool, subfile.getAbsolutePath());
					}
				}
			}
		}
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void createConfig(ExecutorService threadPool, String configFile) {
		ExperimentConfiguration config = null;
		try {
			config = new ExperimentConfiguration(configFile);
		} catch (Exception e) {
			Console.printerrln("Failure initializing the experiment configuration for configuration file " + configFile);
			e.printStackTrace();
		}
		if( config!=null ) {
			Console.trace(Level.FINE, config.toString());
			Experiment experiment = new Experiment(config);
			threadPool.execute(experiment);
		}
	}
}
