package kr.co.dev.tools.api.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@ContextConfiguration
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String welcomeHome()
	{
		logger.debug("welcome PersonalEvalation!!");
		return "/sub/main";
	}	
}
