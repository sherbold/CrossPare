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

/**
 * Thrown if there is an error creating an experiment configuration.
 * 
 * @author Steffen Herbold
 */
public class ExperimentConfigurationException extends Exception {

    /**
     * Standard serialization ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @see Exception#Exception()
     */
    public ExperimentConfigurationException() {
        super();
    }

    /**
     * @see Exception#Exception(String)
     */
    public ExperimentConfigurationException(String message) {
        super(message);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public ExperimentConfigurationException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * @see Exception#Exception(Throwable)
     */
    public ExperimentConfigurationException(Throwable e) {
        super(e);
    }

}
