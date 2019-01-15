package com.xaamruda.bbm.commons.spring.context;

import org.springframework.context.ConfigurableApplicationContext;

public final class ContextProvider {

	private static ConfigurableApplicationContext configurableApplicationContext;
	
	private ContextProvider() {}
	
	public static void init(ConfigurableApplicationContext _configurableApplicationContext) {
		if(configurableApplicationContext == null)
			configurableApplicationContext = _configurableApplicationContext;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return configurableApplicationContext.getBean(clazz);
	}
	
	public static ConfigurableApplicationContext getSpringConfigurableAppContext() {
		return configurableApplicationContext;
	}
	
}
