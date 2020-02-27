package com.fxbank.tpp.simu.config;

import com.fxbank.tpp.simu.interceptor.ESBPackConvertInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebAppConfig extends  WebMvcConfigurationSupport   {

	@Autowired
	private ESBPackConvertInterceptor packConvertInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(packConvertInterceptor).addPathPatterns("/**").excludePathPatterns(new String[]{"/a10/*","/post/**"}).order(1);
        super.addInterceptors(registry);
    }
}