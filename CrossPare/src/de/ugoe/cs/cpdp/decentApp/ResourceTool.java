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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.ocl.common.OCLConstants;
import org.eclipse.ocl.ecore.delegate.OCLInvocationDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLSettingDelegateFactory;
import org.eclipse.ocl.ecore.delegate.OCLValidationDelegateFactory;

/**
 * Class for handling different EMF Ressources
 * 
 * @author Philip Makedonski
 * 
 */
public class ResourceTool {

    protected ResourceFactoryImpl resourceFactory = new XMIResourceFactoryImpl();

    /**
     * Constructor
     * 
     * @param loggedClass
     */
    public ResourceTool(String loggedClass) {
        System.setProperty("org.slf4j.simpleLogger.logFile", "validation.log");
        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
    }

    /**
     * Initializes the validator
     */
    protected void initializeValidator() {
        // OCL.initialize(null);
        String oclDelegateURI = OCLConstants.OCL_DELEGATE_URI + "/Pivot";

        EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE
            .put(oclDelegateURI, new OCLInvocationDelegateFactory(oclDelegateURI));
        EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE
            .put(oclDelegateURI, new OCLSettingDelegateFactory(oclDelegateURI));
        EValidator.ValidationDelegate.Registry.INSTANCE
            .put(oclDelegateURI, new OCLValidationDelegateFactory(oclDelegateURI));

        // EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.put(oclDelegateURI,
        // new OCLSettingDelegateFactory.Global());
        // QueryDelegate.Factory.Registry.INSTANCE.put(oclDelegateURI, new
        // OCLQueryDelegateFactory.Global());

    }

    /**
     * Validates the ressource
     * 
     * @param resource
     *            to validate
     */
    public void validateResource(Resource resource) {
        BasicDiagnostic diagnostics = new BasicDiagnostic();
        boolean valid = true;
        for (EObject eo : resource.getContents()) {
            Map<Object, Object> context = new HashMap<Object, Object>();
            boolean validationResult = Diagnostician.INSTANCE.validate(eo, diagnostics, context);
            showDiagnostics(diagnostics, "");
            valid &= validationResult;
        }

        if (!valid) {
            System.out.println("Problem with validation!");
        }
    }

    /**
     * Output method for showing diagnostics for different ressources
     * 
     * @param diagnostics
     * @param indent
     */
    protected void showDiagnostics(Diagnostic diagnostics, String indent) {
        indent += "  ";
        for (Diagnostic d : diagnostics.getChildren()) {
            System.out.println(indent + d.getSource());
            System.out.println(indent + "  " + d.getMessage());
            showDiagnostics(d, indent);
        }
    }

    /**
     * Loads a resource from XMI
     * 
     * @param inputPath
     *            path to the xmi
     * @param extension
     *            of the resource to load
     * @param p
     *            the given EPackage
     * @return the resource
     */
    // TODO: workarounds copied from respective methods without EPackage parameter
    @SuppressWarnings(
        { "rawtypes", "unchecked" })
    public Resource loadResourceFromXMI(String inputPath, String extension, EPackage p) {
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(extension, resourceFactory);
        ResourceSet resSetIn = new ResourceSetImpl();
        // critical part
        resSetIn.getPackageRegistry().put(p.getNsURI(), p);

        Resource inputResource = resSetIn.createResource(URI.createURI(inputPath));
        try {
            Map options = new HashMap<>();
            options.put(XMIResourceImpl.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
            // options.put(XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF,
            // XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF_DISCARD);
            inputResource.load(options);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return inputResource;
    }

    /**
     * Loads a resource from XMI
     * 
     * @param inputPath
     *            path to the xmi
     * @param extension
     *            of the ressource to load
     * @return the resource
     */

    @SuppressWarnings(
        { "rawtypes", "unchecked" })
    public Resource loadResourceFromXMI(String inputPath, String extension) {
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(extension, resourceFactory);
        ResourceSet resSetIn = new ResourceSetImpl();
        Resource inputResource = resSetIn.createResource(URI.createURI(inputPath));
        try {
            Map options = new HashMap<>();
            options.put(XMIResourceImpl.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
            // options.put(XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF,
            // XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF_DISCARD);
            inputResource.load(options);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return inputResource;
    }

    /**
     * Gets a resource from a binary form
     * 
     * @param inputPath
     *            path to the binary
     * @param extension
     *            of the model to load
     * @param p
     *            EPackage to put the loaded resource in
     * @return the resource
     */
    public Resource getResourceFromBinary(String inputPath, String extension, EPackage p) {
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(extension, new Resource.Factory() {

            @Override
            public Resource createResource(URI uri) {
                return new BinaryResourceImpl(uri);
            }

        });

        ResourceSet resSetIn = new ResourceSetImpl();
        // critical part
        resSetIn.getPackageRegistry().put(p.getNsURI(), p);

        Resource inputResource = resSetIn.createResource(URI.createURI(inputPath));
        return inputResource;
    }

    /**
     * Loads a resource from a binary form
     * 
     * @param inputPath
     *            path to the binary
     * @param extension
     *            of the model to load
     * @param p
     *            EPackage to put the loaded resource in
     * @return the resource
     */
    // TODO: workarounds copied from respective methods without EPackage parameter
    @SuppressWarnings(
        { "rawtypes" })
    public Resource loadResourceFromBinary(String inputPath, String extension, EPackage p) {
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(extension, new Resource.Factory() {

            @Override
            public Resource createResource(URI uri) {
                return new BinaryResourceImpl(uri);
            }

        });

        ResourceSet resSetIn = new ResourceSetImpl();
        // critical part
        resSetIn.getPackageRegistry().put(p.getNsURI(), p);

        Resource inputResource = resSetIn.createResource(URI.createURI(inputPath));
        if (new File(inputPath).exists()) {

            try {
                Map options = new HashMap<>();
                // options.put(BinaryResourceImpl.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
                // options.put(BinaryResourceImpl.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
                // options.put(XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF,
                // XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF_DISCARD);
                inputResource.load(options);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputResource;
    }

    /**
     * Loads a resource from a binary form
     * 
     * @param inputPath
     *            path to the binary
     * @param extension
     *            of the model to load
     * @return the resource
     */
    @SuppressWarnings(
        { "rawtypes" })
    public Resource loadResourceFromBinary(String inputPath, String extension) {
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(extension, new Resource.Factory() {

            @Override
            public Resource createResource(URI uri) {
                return new BinaryResourceImpl(uri);
            }

        });

        ResourceSet resSetIn = new ResourceSetImpl();
        Resource inputResource = resSetIn.createResource(URI.createURI(inputPath));
        try {
            Map options = new HashMap<>();
            // options.put(XMIResourceImpl.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
            // options.put(XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF,
            // XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF_DISCARD);
            inputResource.load(options);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return inputResource;
    }

    /**
     * Stores the binary resource contents to a given path
     * 
     * @param contents
     *            EList of different EObjects to store
     * @param outputPath
     *            path to store to
     * @param extension
     *            of the model to store
     */
    @SuppressWarnings(
        { "rawtypes" })
    public void storeBinaryResourceContents(EList<EObject> contents,
                                            String outputPath,
                                            String extension)
    {
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(extension, new Resource.Factory() {

            @Override
            public Resource createResource(URI uri) {
                return new BinaryResourceImpl(uri);
            }

        });

        ResourceSet resSet = new ResourceSetImpl();
        Resource outputResource = resSet.createResource(URI.createURI(outputPath));
        outputResource.getContents().addAll(contents);
        try {
            Map options = new HashMap<>();
            // options.put(XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF,
            // XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF_DISCARD);
            outputResource.save(options);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores the resource contents to a given path
     * 
     * @param contents
     *            EList of different EObjects to store
     * @param outputPath
     *            path to store to
     * @param extension
     *            of the model to store
     */
    @SuppressWarnings(
        { "unchecked", "rawtypes" })
    public void storeResourceContents(EList<EObject> contents, String outputPath, String extension)
    {
        // TODO: duplicated from loadResourceFromXMI => move to a more appropriate location
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(extension, resourceFactory);

        ResourceSet resSet = new ResourceSetImpl();
        Resource outputResource = resSet.createResource(URI.createURI(outputPath));
        outputResource.getContents().addAll(contents);
        try {
            Map options = new HashMap<>();
            options.put(XMIResourceImpl.OPTION_ENCODING, "UTF-8");
            // options.put(XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF,
            // XMIResourceImpl.OPTION_PROCESS_DANGLING_HREF_DISCARD);
            outputResource.save(options);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
