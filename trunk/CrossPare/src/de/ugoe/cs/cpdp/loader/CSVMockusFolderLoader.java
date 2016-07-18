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

package de.ugoe.cs.cpdp.loader;

/**
 * <p>
 * Reads data from the data set provided by Mockus (and Zhang) for universal defect prediction.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class CSVMockusFolderLoader extends AbstractFolderLoader {

    @Override
    protected SingleVersionLoader getSingleLoader() {
        return new CSVMockusDataLoader();
    }
}
