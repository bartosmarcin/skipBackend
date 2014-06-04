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
public class UserController {
    
    AccountManager umgr = new AccountManager();
    AuthorityManager amgr = new AuthorityManager();
    DriversManager dmgr = new DriversManager();
    
    @RequestMapping(value="/users/{username}", method=RequestMethod.GET)
    public @ResponseBody Account getAccout(@PathVariable("username") String username) {
        // Get Authority by username.
        Authority authority = amgr.getAuthorityByUsername(username);
        
        // Return Account if the user's authority is ADMIN.
        if(authority != null) {
            if((AuthorityManager.USER).equals(authority.getAuthority())) {
                return umgr.getAccountByUsername(username);
            }
        }
        
        return null;
    }
	
    @RequestMapping(value="/users", method=RequestMethod.GET)
    public @ResponseBody List<Account> getAccouts() {
        // Create an empty list.
        List<Account> accounts = new ArrayList<Account>();
        
        // Get all Authorities.
        List<Authority> authorities = amgr.getAuthoritiesList();
        for(Authority authority : authorities) {            
            if((AuthorityManager.USER).equals(authority.getAuthority())) {
                accounts.add(umgr.getAccountByUsername(authority.getUsername()));
            }
        }
        
        return accounts;
    }
	
    @RequestMapping(value="/users", method=RequestMethod.POST)
    public @ResponseBody Account addAccout(@RequestParam(value="json", required=true, defaultValue="{}") String json) {
        // Get Account from json.
        Account account = umgr.AccountFromJson(json);
        
        if(umgr.getAccountByUsername(account.getUsername()) != null) {
            // Account with the requested username already exists.
            return null;
        }
        
        // Create Driver entity for the account.
        Driver entity = new Driver();
        entity.setFirstName("New");
	entity.setLastName("Driver");
	entity.setEmail("driver@email.com");
	entity.setLatestCoordinates(new Coordinates(0,0));
	entity.setPhoneNumber("123456789");
	entity.setPhoneNumber2("123456789");
        
        // Add the driver.
        entity = dmgr.addDriver(entity);
        if(entity == null) {
            // Failed to add the driver to database.
            return null;
        }
        
        // Assign the driver to the new account.
        account.setEntity(entity.getId());
        
        // Add the new account.
        account = umgr.addAccount(account);
        if(account == null) {
            // Failed to add the new account to database.
            dmgr.removeDriver(entity.getId());
            
            return null;          
        }
        
        // Add Authority for the account.
        amgr.addAuthority(new Authority(account.getUsername(), AuthorityManager.USER));
        
        return account;
    }
        
    @RequestMapping(value="/users/{username}", method=RequestMethod.DELETE)
    public @ResponseBody Account removeAccout(@PathVariable("username") String username) {
        // Get Authority by username.
        Authority authority = amgr.getAuthorityByUsername(username);
        
        // Remove Account if the user's authority is ADMIN.
        if(authority != null) {
            if((AuthorityManager.USER).equals(authority.getAuthority())) {
                authority = amgr.removeAuthority(username);
                
                if(authority != null) {
                    Account account = umgr.removeAccount(username);
                    
                    if(account != null) {
                        dmgr.removeDriver(account.getEntity());
                    }
                    
                    return account;
                }
            }
        }
        
        return null;     
    }
    
}
