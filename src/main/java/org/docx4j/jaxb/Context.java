/*
 *  Copyright 2007-2008, Plutext Pty Ltd.
 *   
 *  This file is part of docx4j.

    docx4j is licensed under the Apache License, Version 2.0 (the "License"); 
    you may not use this file except in compliance with the License. 

    You may obtain a copy of the License at 

        http://www.apache.org/licenses/LICENSE-2.0 

    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
    See the License for the specific language governing permissions and 
    limitations under the License.

 */
package org.docx4j.jaxb;


import ae.com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
import ae.com.sun.xml.bind.v2.model.annotation.XmlSchemaMine;
import ae.javax.xml.bind.JAXBContext;
import ae.javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.docx4j.bibliography.CTSources;
import org.docx4j.customXmlProperties.SchemaRefs;
import org.docx4j.customxml.SchemaLibrary;
import org.docx4j.dml.Graphic;
import org.docx4j.dml.chart.CTChart;
import org.docx4j.relationships.Relationships;
import org.docx4j.utils.Log4jConfigurator;

public class Context {
	
	public static JAXBContext jc;
	public static JAXBContext jcThemePart;
	public static JAXBContext jcDocPropsCore;
	public static JAXBContext jcDocPropsCustom;
	public static JAXBContext jcDocPropsExtended;
	public static JAXBContext jcRelationships;
	public static JAXBContext jcCustomXmlProperties;
	public static JAXBContext jcContentTypes;

	public static JAXBContext jcXmlPackage;
	
	private static JAXBContext jcXslFo;
	public static JAXBContext jcSectionModel;

	public static JAXBContext jcXmlDSig;
	
	private static Logger log = Logger.getLogger(Context.class);
	
	static {
		
        final long startTime = System.currentTimeMillis(); 
        final long endTime; 		
	  
		Log4jConfigurator.configure();
				
		// Display diagnostic info about version of JAXB being used.
		Object namespacePrefixMapper;
		try {
			namespacePrefixMapper = NamespacePrefixMapperUtils.getPrefixMapper();
			if ( namespacePrefixMapper.getClass().getName().equals("org.docx4j.jaxb.NamespacePrefixMapperSunInternal") ) {
				// Java 6
				log.info("Using Java 6/7 JAXB implementation");
			} else {
				log.info("Using JAXB Reference Implementation");			
			}
		} catch (JAXBException e) {
			log.error("PANIC! No suitable JAXB implementation available");
			e.printStackTrace();
		}
		
		// Android doesn't support package annotations, so 
		// tell our JAXB about our namespaces
      
		RuntimeInlineAnnotationReader.cachePackageAnnotation(Relationships.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/package/2006/relationships"));
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(CTSources.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/officeDocument/2006/bibliography"));
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(SchemaLibrary.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/schemaLibrary/2006/main")); 
		RuntimeInlineAnnotationReader.cachePackageAnnotation(SchemaRefs.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/officeDocument/2006/customXml"));
		
		//DrawingML
		RuntimeInlineAnnotationReader.cachePackageAnnotation(Graphic.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/drawingml/2006/main")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(CTChart.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/drawingml/2006/chart")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.dml.chartDrawing.CTDrawing.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/drawingml/2006/chartDrawing")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.dml.compatibility.CTCompat.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/drawingml/2006/compatibility")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.dml.diagram.CTDiagramDefinition.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/drawingml/2006/diagram")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.dml.diagram2008.CTDrawing.class.getPackage(), 
				new XmlSchemaMine("http://schemas.microsoft.com/office/drawing/2008/diagram")); 
		
		// LockedCanvas?
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.dml.picture.Pic.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/drawingml/2006/picture")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.dml.spreadsheetdrawing.CTDrawing.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.dml.wordprocessingDrawing.Inline.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing")); 
		
		// DocProps
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.docProps.core.CoreProperties.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/package/2006/metadata/core-properties")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.docProps.core.dc.elements.ElementContainer.class.getPackage(), 
				new XmlSchemaMine("http://purl.org/dc/elements/1.1/")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.docProps.core.dc.terms.ElementOrRefinementContainer.class.getPackage(), 
				new XmlSchemaMine("http://purl.org/dc/terms/")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.docProps.custom.Properties.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/officeDocument/2006/custom-properties")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.docProps.extended.Properties.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/officeDocument/2006/extended-properties")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.docProps.variantTypes.Variant.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes")); 
		
		
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.math.CTM.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/officeDocument/2006/math")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.openpackaging.contenttype.ContentType.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/package/2006/content-types")); 
		
		// VML
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.vml.officedrawing.CTCallout.class.getPackage(), 
				new XmlSchemaMine("urn:schemas-microsoft-com:office:office")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.vml.presentationDrawing.CTRel.class.getPackage(), 
				new XmlSchemaMine("urn:schemas-microsoft-com:office:powerpoint")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.vml.spreadsheetDrawing.CTClientData.class.getPackage(), 
				new XmlSchemaMine("urn:schemas-microsoft-com:office:excel")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.vml.wordprocessingDrawing.CTAnchorLock.class.getPackage(), 
				new XmlSchemaMine("urn:schemas-microsoft-com:office:word")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.vml.CTArc.class.getPackage(), 
				new XmlSchemaMine("urn:schemas-microsoft-com:vml")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.wml.P.class.getPackage(), 
				new XmlSchemaMine("http://schemas.openxmlformats.org/wordprocessingml/2006/main")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.docx4j.xmlPackage.Package.class.getPackage(), 
				new XmlSchemaMine("http://schemas.microsoft.com/office/2006/xmlPackage")); 
		
		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.opendope.components.Components.class.getPackage(),
				new XmlSchemaMine("http://opendope.org/components"));

		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.opendope.conditions.Conditions.class.getPackage(),
				new XmlSchemaMine("http://opendope.org/conditions"));

		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.opendope.questions.Questionnaire.class.getPackage(),
				new XmlSchemaMine("http://opendope.org/questions"));

		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.opendope.SmartArt.dataHierarchy.SmartArtDataHierarchy.class.getPackage(),
				new XmlSchemaMine("http://opendope.org/SmartArt/DataHierarchy"));

		RuntimeInlineAnnotationReader.cachePackageAnnotation(org.opendope.xpaths.Xpaths.class.getPackage(),
				new XmlSchemaMine("http://opendope.org/xpaths"));
		
		// Nothing for package org.docx4j.model.structure.jaxb
		
      try { 
			
			// JBOSS might use a different class loader to load JAXBContext, which causes problems,
			// so explicitly specify our class loader.
			Context tmp = new Context();
			java.lang.ClassLoader classLoader = tmp.getClass().getClassLoader();
			//log.info("\n\nClassloader: " + classLoader.toString() );			
			
			log.info("loading Context jc");			
			jc = JAXBContext.newInstance("org.docx4j.wml:" +
					"org.docx4j.dml:org.docx4j.dml.chart:" +
					"org.docx4j.dml.chartDrawing:" +
					"org.docx4j.dml.compatibility:org.docx4j.dml.diagram:org.docx4j.dml.lockedCanvas:org.docx4j.dml.picture:org.docx4j.dml.wordprocessingDrawing:" +
					"org.docx4j.dml.spreadsheetdrawing:" +
					"org.docx4j.dml.diagram2008:" +
					// All VML stuff is here, since compiling it requires WML and DML (and MathML), but not PML or SML
					"org.docx4j.vml:org.docx4j.vml.officedrawing:org.docx4j.vml.wordprocessingDrawing:org.docx4j.vml.presentationDrawing:org.docx4j.vml.spreadsheetDrawing:org.docx4j.vml.root:" +
					"org.opendope.xpaths:org.opendope.conditions:org.opendope.questions:org.opendope.components:org.opendope.SmartArt.dataHierarchy:" +
					"org.docx4j.math:" +
					"org.docx4j.sharedtypes:org.docx4j.bibliography",classLoader );
			log.info("loaded " + jc.getClass().getName() + " .. loading others ..");
			
			jcThemePart = jc; //JAXBContext.newInstance("org.docx4j.dml",classLoader );
			jcDocPropsCore = JAXBContext.newInstance("org.docx4j.docProps.core:org.docx4j.docProps.core.dc.elements:org.docx4j.docProps.core.dc.terms",classLoader );
			jcDocPropsCustom = JAXBContext.newInstance("org.docx4j.docProps.custom",classLoader );
			jcDocPropsExtended = JAXBContext.newInstance("org.docx4j.docProps.extended",classLoader );
			jcXmlPackage = JAXBContext.newInstance("org.docx4j.xmlPackage",classLoader );
			jcRelationships = JAXBContext.newInstance("org.docx4j.relationships",classLoader );
			jcCustomXmlProperties = JAXBContext.newInstance("org.docx4j.customXmlProperties",classLoader );
			jcContentTypes = JAXBContext.newInstance("org.docx4j.openpackaging.contenttype",classLoader );
			
			jcSectionModel = JAXBContext.newInstance("org.docx4j.model.structure.jaxb",classLoader );
			
			//jcXmlDSig = JAXBContext.newInstance("org.plutext.jaxb.xmldsig",classLoader );
			
			log.info(".. others loaded ..");
			
		} catch (Exception ex) {
			log.error("Cannot initialize context", ex);
        } finally { 
            endTime = System.currentTimeMillis(); 
          } 
          final long duration = endTime - startTime; 
      
          System.err.println("Context init: " + duration + "ms");
      
	}
	
	private static org.docx4j.wml.ObjectFactory wmlObjectFactory;
	
	public static org.docx4j.wml.ObjectFactory getWmlObjectFactory() {
		
		if (wmlObjectFactory==null) {
			wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
		}
		return wmlObjectFactory;
		
	}

	public static JAXBContext getXslFoContext() {
		if (jcXslFo==null) {
			try {	
				Context tmp = new Context();
				java.lang.ClassLoader classLoader = tmp.getClass().getClassLoader();

				jcXslFo = JAXBContext.newInstance("org.plutext.jaxb.xslfo",classLoader );
				
			} catch (JAXBException ex) {
	      log.error("Cannot determine XSL-FO context", ex);
			}						
		}
		return jcXslFo;		
	}
}
