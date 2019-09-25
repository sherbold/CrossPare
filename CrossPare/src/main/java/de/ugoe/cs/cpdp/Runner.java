// Copyright 2015 Georg-August-Universität Göttingen, Germany
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package de.ugoe.cs.cpdp;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import de.ugoe.cs.cpdp.execution.IExecutionStrategy;

/**
 * Executable that can be used to run experiments.
 * 
 * @author Steffen Herbold
 * 
 */
public class Runner {
	
	static {
		String pattern = "%d [%t] %-5level: %msg%n%throwable";
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
    	AppenderComponentBuilder stdoutLogger = builder.newAppender("stdout", "Console");
    	stdoutLogger.add(builder.newLayout("PatternLayout").addAttribute("pattern", pattern));
    	stdoutLogger.add(builder.newFilter("ThresholdFilter", Filter.Result.DENY, Filter.Result.ACCEPT).addAttribute("level", "error"));
    	builder.add(stdoutLogger);
    	AppenderComponentBuilder stderrLogger = builder.newAppender("stderr", "Console");
    	stderrLogger.add(builder.newLayout("PatternLayout").addAttribute("pattern", pattern));
    	stderrLogger.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.DENY).addAttribute("level", "error"));
    	stderrLogger.addAttribute("target", ConsoleAppender.Target.SYSTEM_ERR);
    	builder.add(stderrLogger);
    	
    	
    	RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.INFO);
    	rootLogger.add(builder.newAppenderRef("stdout")).add(builder.newAppenderRef("stderr"));
    	builder.add(rootLogger);
    	Configurator.initialize(builder.build());
	}
	
	/**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");

    /**
     * Main class. The arguments are {@link ExperimentConfiguration} files. Each experiment is
     * started in a separate thread. The number of concurrently running threads is the number of
     * logical processors of the host system.
     * 
     * @param args
     *            experiment configuration files
     */
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        final int concurrentThreads = Runtime.getRuntime().availableProcessors();
        LOGGER.info("exuection max " + concurrentThreads + " at the same time");
        final ExecutorService threadPool = Executors.newFixedThreadPool(concurrentThreads);
        for (String arg : args) {
            File file = new File(arg);
            if (file.isFile()) {
                createConfig(threadPool, file.getAbsolutePath());
            }
            else if (file.isDirectory() && file.listFiles() != null) {
                for (File subfile : file.listFiles()) {
                    if (subfile.isFile()) {
                        createConfig(threadPool, subfile.getAbsolutePath());
                    }
                }
            }
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the config and starts the corresponding experiment
     * 
     * @param threadPool
     *            thread pool in which the configurations are executed
     * @param configFile
     *            location of the config file
     */
    public static void createConfig(ExecutorService threadPool, String configFile) {
        ExperimentConfiguration config = null;
        try {
            config = new ExperimentConfiguration(configFile);
        }
        catch (Exception e) {
        	LOGGER.error("Failure initializing the experiment configuration for configuration file " +
                    configFile);
            e.printStackTrace();
        }

        if (config != null) {
        	LOGGER.debug(config.toString());
            // Instantiate the class like it was given as parameter in the config file and cast it
            // to the interface
            try {
                // Because we need to pass a parameter, a normal new Instance call is not possible
                Class<?> executionStrategyClass =
                    Class.forName("de.ugoe.cs.cpdp.execution." + config.getExecutionStrategy());
                Constructor<?> executionStrategyConstructor =
                    executionStrategyClass.getConstructor(ExperimentConfiguration.class);

                IExecutionStrategy experiment =
                    (IExecutionStrategy) executionStrategyConstructor.newInstance(config);
                
                if( experiment instanceof IParameterizable ) {
                    ((IParameterizable) experiment).setParameter(config.getExecutionStrategyParameters());
                }
                
                threadPool.execute(experiment);
            }
            catch (NoSuchMethodException e) {
            	LOGGER.error("Class \"" + config.getExecutionStrategy() +
                    "\" does not have the right Constructor");
                e.printStackTrace();
            }
            catch (SecurityException e) {
            	LOGGER.error("Security manager prevents reflection");
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
            	LOGGER.error("Class \"" + config.getExecutionStrategy() +
                    "\" does not have a Constructor, which" + "matches the given arguments");
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
            	LOGGER.error("Constructor in Class \"" + config.getExecutionStrategy() +
                    "\" is not public");
                e.printStackTrace();
            }
            catch (InstantiationException e) {
            	LOGGER.error("Cannot instantiate Class \"" + config.getExecutionStrategy() +
                    "\"");
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
            	LOGGER.error("Cannot access Class \"" + config.getExecutionStrategy() + "\"");
                e.printStackTrace();
            }
            catch (ClassNotFoundException e) {
            	LOGGER.error("Class \"" + config.getExecutionStrategy() + "\" was not found");
                e.printStackTrace();
            }

        }

    }
}
