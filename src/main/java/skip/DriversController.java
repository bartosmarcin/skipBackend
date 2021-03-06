package skip;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DriversController {
	DriversManager dmgr = new DriversManager();

	
	@RequestMapping(value="/drivers/{id}")
	public @ResponseBody Driver getDriver(
				@PathVariable("id") Long id){
		return dmgr.getDriverById(id);
	}
	
	@RequestMapping(value="/drivers", method=RequestMethod.GET)
	public @ResponseBody List<Driver> getDriver(){
		return dmgr.getDriversList();
	}
	
	@RequestMapping(value="/drivers/{id}", method=RequestMethod.POST)
	public @ResponseBody Driver replaceDriver(
				@PathVariable("id") Long id,
				@RequestParam(required=true) String driver){
		System.out.println(driver);
		return dmgr.replaceDriver(driver, id);
	}
	
	@RequestMapping(value="/drivers/{id}/updateCoordinates", method=RequestMethod.POST)
	public @ResponseBody Coordinates updateCoordinates(
				@PathVariable("id") Long id,
				@RequestParam(required=true) String coordinates){
		return dmgr.updateDriverCoordinates(coordinates, id);
	}	
	
	@RequestMapping(value="/drivers/{id}/assignVehicle", method=RequestMethod.POST)
	public @ResponseBody Vehicle assignVehicle(
				@PathVariable("id") Long id,
				@RequestParam(required=false) Long vehicleId){
		return dmgr.assignVehicle(id, vehicleId);
	}
	
	@RequestMapping(value="/drivers/{id}/assignedVehicle", method=RequestMethod.GET)
	public @ResponseBody Vehicle getAssignedVehicle(
				@PathVariable("id") Long id){
		Driver d = dmgr.getDriverById(id);
		if(d != null)
			return d.getAssignedVehicle();
		return null;
	}
	
        @Secured("ROLE_MASTER, ROLE_ADMIN")
	@RequestMapping(value="/driver/newdummy")
	public @ResponseBody Driver addDummyDriver(){
		Driver d = new Driver();
		d.setFirstName("FirstName");
		d.setLastName("lastName");
		d.setEmail("email@em.com");
		d.setLatestCoordinates(new Coordinates(0,0));
		d.setPhoneNumber("123456789");
		d.setPhoneNumber("123456789");
		return dmgr.addDriver(d);
	}
	
	
}
