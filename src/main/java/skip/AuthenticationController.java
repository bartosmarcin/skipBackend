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
    
    @RequestMapping("/logout")
    public @ResponseBody String logout() {
        return "Logged out";
    }
    
    @RequestMapping("/login")
    public @ResponseBody String welcome(Principal principal) {
        return String.format("Logged in, %s", principal.getName());
    }
    
}