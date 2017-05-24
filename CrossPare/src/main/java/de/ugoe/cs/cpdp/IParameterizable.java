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
 * Interface that defines that an entity excepts a parameter string. Can be used to configure parts
 * of an experiment. How (and if) this parameter is interpreted depends entirely on the entity.
 * 
 * @author Steffen Herbold
 * 
 */
public interface IParameterizable {

    /**
     * Sets the parameters of an entity.
     * 
     * @param parameters
     *            parameters as string
     */
    void setParameter(String parameters);
}
