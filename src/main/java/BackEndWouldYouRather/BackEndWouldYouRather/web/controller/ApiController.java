package BackEndWouldYouRather.BackEndWouldYouRather.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <b>API Page Entry</b><br>
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@RestController
@RequestMapping("/web/api")
public class ApiController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView api() {
		return new ModelAndView("api");
	}
}
