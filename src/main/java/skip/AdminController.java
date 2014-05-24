package skip;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {
    
    AccountManager umgr = new AccountManager();
    AuthorityManager amgr = new AuthorityManager();
    
    @RequestMapping(value="/admins/{username}")
    public @ResponseBody Account getAccout(@PathVariable("username") String username) {
        // Get Authority by username.
        Authority authority = amgr.getAuthorityByUsername(username);
        
        // Return Account if the user's authority is ADMIN.
        if(authority != null) {
            if((AuthorityManager.ADMIN).equals(authority.getAuthority())) {
                return umgr.getAccountByUsername(username);
            }
        }
        
        return null;
    }
	
    @RequestMapping(value="/admins", method=RequestMethod.GET)
    public @ResponseBody List<Account> getAccouts() {
        // Create an empty list.
        List<Account> accounts = new ArrayList<Account>();
        
        // Get all Authorities.
        List<Authority> authorities = amgr.getAuthoritiesList();
        for(Authority authority : authorities) {            
            if((AuthorityManager.ADMIN).equals(authority.getAuthority())) {
                accounts.add(umgr.getAccountByUsername(authority.getUsername()));
            }
        }
        
        return accounts;
    }
	
    @RequestMapping(value="/admins", method=RequestMethod.POST)
    public @ResponseBody Account addAccout(@RequestParam(value="json", required=true, defaultValue="{}") String json) {
        // Get Account from json.
        Account account = umgr.AccountFromJson(json);
        
        // Set entity id to 0.
        account.setEntity(0);
        
        // Add the new account.
        account = umgr.addAccount(account);
        
        if(account != null) {
            amgr.addAuthority(new Authority(account.getUsername(), AuthorityManager.ADMIN));
        }
        
        return account;
    }
        
    @RequestMapping(value="/admins/{username}", method=RequestMethod.DELETE)
    public @ResponseBody Account removeAccout(@PathVariable("username") String username) {
        // Get Authority by username.
        Authority authority = amgr.getAuthorityByUsername(username);
        
        // Remove Account if the user's authority is ADMIN.
        if(authority != null) {
            if((AuthorityManager.ADMIN).equals(authority.getAuthority())) {
                Account account = umgr.removeAccount(username);
                if(account != null) {
                    amgr.removeAuthority(username);
                }
                
                return account;
            }
        }
        
        return null;     
    }
    
}
