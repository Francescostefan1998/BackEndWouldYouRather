package BackEndWouldYouRather.BackEndWouldYouRather.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <b>Web MVC configurations</b><br>
 * 
 * You can configure your application MVC features by this class when .yml file
 * configurations can not meet your requirements.
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**").allowedOrigins("*");
	}

}
