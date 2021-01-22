package com.itafin.lifeline.config;

import java.util.HashSet;
import java.util.Set;

import javax.activation.CommandInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.itafin.lifeline.converters.SupplierConverter;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.itafin.lifeline.controller", "com.itafin.lifeline.service", "com.itafin.lifeline.dao",
		"com.itafin.lifeline.utils", "com.itafin.lifeline.validation", "com.itafin.lifeline.exception", "com.itafin.lifeline.converters" })
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	@Autowired SupplierConverter supplierConverter;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	
	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
		rb.setBasenames("messages/validation");
		rb.setDefaultEncoding("UTF-8");
		rb.setUseCodeAsDefaultMessage(true);
		return rb;
	}
	

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(-1); // -1 = infinite, to allow controller to validate file size
		// 1048576 = 1 MB
		return multipartResolver;
	}
	
//	@Bean
//	public ConversionService conversionService() {
//		ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
//		bean.setConverters(getConverters());
//		
//		ConversionService object = bean.getObject();
//		
//		return object;
//	}
	
//	private Set<Converter> getConverters() {
//		Set<Converter> converters = new HashSet<Converter>();
//		
//		converters.add(supplierConverter);
//		
//		return converters;
//	}
	
	/**
	 * This is supposed to allow the supplierConverter to work
	 * but supplierConverter.convert() doesn't get called on submission for some reason
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(supplierConverter);
	}

}