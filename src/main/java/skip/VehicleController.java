package skip;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VehicleController {
	VehiclesManager vmgr = new VehiclesManager();
	
	@RequestMapping(value="/vehicles/{id}")
	public @ResponseBody Vehicle getVehicle(
			@PathVariable("id") Long id) {
		return vmgr.getVehicleById(id);
	}
	
	@RequestMapping(value="/vehicles", method=RequestMethod.GET)
	public @ResponseBody List<Vehicle> getVehicles(){
		return vmgr.getVehiclesList();
	}
	
	@RequestMapping(value="/vehicles/{id}", method=RequestMethod.DELETE)
	public @ResponseBody Vehicle removeVehicle(
			@PathVariable("id") Long id){
		return vmgr.removeVehicle(id);
	}
	
	@RequestMapping(value="/vehicles", method=RequestMethod.POST)
	public @ResponseBody Vehicle addVehicle(
			@RequestParam(required=true) String json){
		return vmgr.addVehicle(json);
	}
	
	@RequestMapping(value="/test_2")
	public @ResponseBody Vehicle testValidation(){
		Vehicle v = new Vehicle();
		v.setBrand("MERCEDES");
		v.setColour("GREEN");
		v.setRegNumber("KFD-212");
		v.setTruckload(12000);
		return vmgr.addVehicle(v);
	}
}
