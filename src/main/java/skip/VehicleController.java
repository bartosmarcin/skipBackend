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
        
        @RequestMapping(value="/vehicles/{id}", method=RequestMethod.PUT)
	public @ResponseBody Vehicle replaceVehicle(
				@PathVariable("id") Long id,
				@RequestParam(required=true) String vehicle){
		return vmgr.replaceVehicle(vehicle, id);
	}
	
	@RequestMapping(value="/vehicles", method=RequestMethod.POST)
	public @ResponseBody Vehicle addVehicle(
			@RequestParam(required=true) String vehicle){
		return vmgr.addVehicle(vehicle);
	}
	
	@RequestMapping(value="/vehicles/addRandomVehicle")
	public @ResponseBody Vehicle addRandomVehicle(){
		Vehicle v = new Vehicle();
                v.setTruckload(10000);
                v.setBrand("BMW");
                v.setColour("BLUE");
                v.setRegistrationNumber("KGB-997");
                return vmgr.addVehicle(v);
	}
}