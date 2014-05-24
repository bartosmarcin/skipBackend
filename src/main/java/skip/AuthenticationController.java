package skip;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthenticationController {

//    @RequestMapping(value = "/login", method = RequestMethod.GET, params = { "user", "password" })
//    public @ResponseBody String login(@RequestParam("user") String user, @RequestParam("password") String password) {
//        return "data: " + user + ", " + password;
//    }
    
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public @ResponseBody String logout(Principal principal) {
//	if(principal == null || principal.getName() == null)
//		return "OK";
//	else
//	        return "NO";
//    }
    
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public @ResponseBody String welcome(Principal principal) {
//	if(principal == null || principal.getName() == null)
//		return "NO";
//	else
//	        return String.format("%s", principal.getName());
//    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody String root() {
        return String.format("SKIP");
    }
    
}