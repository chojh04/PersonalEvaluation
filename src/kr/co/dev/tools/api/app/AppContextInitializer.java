package kr.co.dev.tools.api.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

public class AppContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Logger logger = LoggerFactory.getLogger(AppContextInitializer.class);
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		// TODO Auto-generated method stub
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		
		try 
		{
			logger.info("============> Begin To initialize Application Contex!! =============");
			
			environment.getPropertySources().addFirst(
					new ResourcePropertySource("classpath:runtime/config/environment/environment.properties"));
			
			logger.info("============> Application Context is Loaded !! =============");
			logger.info("profiles : " + environment.getActiveProfiles());
			
			String[] profiles = environment.getActiveProfiles();
			
			for(int i=0; i<profiles.length; i++)
			{
				logger.debug("s : " + profiles[i]);
			}
			
			if(profiles.length > 0)
			{
				System.setProperty("spring.profiles.active", profiles[0]);
			}
			else
			{
				logger.warn("=========> Spring Profiles Not Exist!! =============");
			}
			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("=========> Application Context Loading is Failed!! =============");
		}
		
	}

	
	
}
