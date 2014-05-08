package skip;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatementsController {

    StatementsManager smgr = new StatementsManager();

    @RequestMapping(value = "/statements", method = RequestMethod.GET)
    public @ResponseBody List<Statement> getStatement() {
        return smgr.getStatementsList();
    }
    
    @RequestMapping(value="/statements/{id}", method = RequestMethod.GET)
    public @ResponseBody Statement getDriver(
           @PathVariable("id") Long id){
	return smgr.getStatementById(id);
    }

    @RequestMapping(value = "/statements", method = RequestMethod.POST)
    public @ResponseBody Statement addStatement(
            @RequestParam(required = true) String statement) {
        return smgr.addStatement(statement);
    }
    
    @RequestMapping(value="/statements/{id}", method=RequestMethod.DELETE)
    public @ResponseBody Statement removeStatement(
            @PathVariable("id") Long id){
	return smgr.removeStatement(id);
    }

    @RequestMapping(value = "/statements/add")
    public @ResponseBody Statement addRandomVehicle() {
        Statement s = new Statement();
        s.setDriverId(1);
        s.setcoordinates(new Coordinates(45, 45));
        s.setDate(null);
        s.setState(1);
        return smgr.addStatement(s);

    }
    
}
