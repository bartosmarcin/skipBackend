package skip;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/drivers")
public class DriversController {
	DriversManager dmgr = new DriversManager();
	
	@RequestMapping(value="/{id}")
	public @ResponseBody Driver getDriver(
				@PathVariable("id") Long id){
		return dmgr.getDriverById(id);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public @ResponseBody List<Driver> getDriver(){
		return dmgr.getDriversList();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody Driver removeDriver(
				@PathVariable("id") Long id){
		return dmgr.removeDriver(id);
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public @ResponseBody Driver addDriver(
				@RequestBody(required=true) String json){
		return dmgr.addDriver(json);
	}
	
	
}
