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

package de.ugoe.cs.cpdp.decentApp;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EObjectValidator;

import de.ugoe.cs.cpdp.decentApp.models.arffx.ARFFxPackage;
import de.ugoe.cs.cpdp.decentApp.models.arffx.impl.ARFFxPackageImpl;

/**
 * Class for handling arffx model files
 * 
 * @author Philip Makedonski, Fabian Trautsch
 * 
 */
public class ARFFxResourceTool extends ResourceTool {

    /**
     * Initializes the Tool Factory, from which the models can be loaded and inizializes the
     * validator.
     */
    public ARFFxResourceTool() {
        super(ARFFxResourceTool.class.getName());
        ARFFxPackageImpl.init();

        // Commented, because simulation has problems with this
        initializeValidator();
    }

    /**
     * Inizializes the model validator
     */
    @Override
    protected void initializeValidator() {
        super.initializeValidator();
        EObjectValidator validator = new EObjectValidator();
        EValidator.Registry.INSTANCE.put(ARFFxPackage.eINSTANCE, validator);
    }

}
