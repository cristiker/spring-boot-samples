SpringBoot自动配置类
    
    org.springframework.security.config.annotation.web.configuration.WebSecurityEnablerConfiguration
    org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
    
自定义WebSecurity  
    
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter
    
或
    
    public class WebSecurityConfig implements WebSecurityConfigurer
    
WebSecurityConfiguration#setFilterChainProxySecurityConfigurer中
AutowireBeanFactoryObjectPostProcessor.postProcess创建WebSecurity
    
    	@Autowired(required = false)
    	public void setFilterChainProxySecurityConfigurer(ObjectPostProcessor<Object> objectPostProcessor,
    			@Value("#{@autowiredWebSecurityConfigurersIgnoreParents.getWebSecurityConfigurers()}") 
    			List<SecurityConfigurer<Filter, WebSecurity>> webSecurityConfigurers)
    			throws Exception {
    		webSecurity = objectPostProcessor
    				.postProcess(new WebSecurity(objectPostProcessor));
    		if (debugEnabled != null) {
    			webSecurity.debug(debugEnabled);
    		}
    
    		Collections.sort(webSecurityConfigurers, AnnotationAwareOrderComparator.INSTANCE);
    
    		Integer previousOrder = null;
    		Object previousConfig = null;
    		for (SecurityConfigurer<Filter, WebSecurity> config : webSecurityConfigurers) {
    			Integer order = AnnotationAwareOrderComparator.lookupOrder(config);
    			if (previousOrder != null && previousOrder.equals(order)) {
    				throw new IllegalStateException(
    						"@Order on WebSecurityConfigurers must be unique. Order of "
    								+ order + " was already used on " + previousConfig + ", so it cannot be used on "
    								+ config + " too.");
    			}
    			previousOrder = order;
    			previousConfig = config;
    		}
    		for (SecurityConfigurer<Filter, WebSecurity> webSecurityConfigurer : webSecurityConfigurers) {
    			webSecurity.apply(webSecurityConfigurer);
    		}
    		this.webSecurityConfigurers = webSecurityConfigurers;
    	}
    
初始化自定义的webSecurityConfiger
    	
    org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder#init()//初始化自定义securityConfiger
    
    
    
    

    
    


    
    