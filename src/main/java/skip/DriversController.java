package skip;

import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DriversController {
	DriversManager dmgr = new DriversManager();
	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	
	@RequestMapping(value="/drivers/{id}")
	public @ResponseBody Driver getDriver(
				@PathVariable("id") Long id){
		return dmgr.getDriverById(id);
	}
	
	@RequestMapping(value="/drivers", method=RequestMethod.GET)
	public @ResponseBody List<Driver> getDriver(){
		return dmgr.getDriversList();
	}
	
	@RequestMapping(value="/drivers/{id}", method=RequestMethod.DELETE)
	public @ResponseBody Driver removeDriver(
				@PathVariable("id") Long id){
		return dmgr.removeDriver(id);
	}
	
	@RequestMapping(value="/drivers", method=RequestMethod.POST)
	public @ResponseBody Driver addDriver(
				@RequestBody(required=true) String json){
		return dmgr.addDriver(json);
	}
	
	@RequestMapping(value="/test")
	public @ResponseBody Driver testValidation(){
		Driver d = new Driver();
		d.setFirstName("FirstName");
		d.setLastName("lastName");
		d.setEmail("email@em.com");
		d.setLatestCoordinates("N00.1234567 W87.1234567");
		return dmgr.addDriver(d);
	}
	
	
}
