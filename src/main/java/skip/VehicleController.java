package skip;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/vehicles")
public class VehicleController {
	VehiclesManager vmgr = new VehiclesManager();
	
	@RequestMapping(value="/{id}")
	public @ResponseBody Vehicle getVehicle(
			@PathVariable("id") Long id) {
		return vmgr.getVehicleById(id);
	}
	
	@RequestMapping(value="/")
	public @ResponseBody List<Vehicle> getVehicles(){
		return vmgr.getVehiclesList();
	}
	
	@RequestMapping(value="/remove/{id}")
	public @ResponseBody Vehicle removeVehicle(
			@PathVariable("id") Long id){
		return vmgr.removeVehicle(id);
	}
	
	@RequestMapping(value="/add/{json}")
	public @ResponseBody Vehicle addVehicle(
			@PathVariable("json") String json){
		return vmgr.addVehicle(json);
	}
	
}
