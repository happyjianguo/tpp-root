package com.fxbank.tpp.simu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="esb")
public class ESBConfig {

	private String dtoPath;

	public String getDtoPath() {
		return dtoPath;
	}

	public void setDtoPath(String dtoPath) {
		this.dtoPath = dtoPath;
	}
	
	
}