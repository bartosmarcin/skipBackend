package skip;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DriversController {

	@RequestMapping("/driver")
	public @ResponseBody Driver getDriver(
				@RequestParam(value="id", required=true) int id){
		return new Driver();
	}
}
