package com.fxbank.tpp.manager.quartz;

import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzJobConfigration {

	public static final String JOBGROUP = "tpp";
	public static final String METHODNAME = "exec";
	public static final String TCEX_CRON = "tcex_cron.";
	
}
