package skip;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    
    UserManager umgr = new UserManager();
    AuthorityManager amgr = new AuthorityManager();
    
    @RequestMapping(value="/users/{username}")
    public @ResponseBody User getUser(@PathVariable("username") String username) {
        return umgr.getUserByUsername(username);
    }
	
    @RequestMapping(value="/users", method=RequestMethod.GET)
    public @ResponseBody List<User> getUsers() {
        return umgr.getUsersList();
    }
	
    @RequestMapping(value="/users", method=RequestMethod.POST)
    public @ResponseBody User addUser(
            @RequestParam(value="json", required=true, defaultValue="{}") String json) {
        User user = umgr.addUser(umgr.UserFromJson(json));
        if(user != null) {
            amgr.addAuthority(new Authority(user.getUsername(), AuthorityManager.USER));
        }
        
        return user;
    }
    
    @RequestMapping(value="/users/{username}", method=RequestMethod.PUT)
    public @ResponseBody User replaceUser(
            @PathVariable("username") String username,
            @RequestParam(value="json", required=true, defaultValue="{}") String json) {
        return umgr.replaceUser(umgr.UserFromJson(json), username);
    }
    
    @RequestMapping(value="/users/{username}", method=RequestMethod.DELETE)
    public @ResponseBody User removeUser(@PathVariable("username") String username) {
        User user = umgr.removeUser(username);
        if(user != null) {
            amgr.removeAuthority(user.getUsername());
        }
        
        return user;
    }
    
}
