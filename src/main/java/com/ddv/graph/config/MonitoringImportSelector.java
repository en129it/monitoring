package com.ddv.graph.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MonitoringImportSelector implements DeferredImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[] { 
				DiscoveryClientConfiguration.class.getCanonicalName()
		};
	}

}
