package skip;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import util.HibernateUtil;

public class AccountManager {
    
    private static final String HIDDEN_DATA = "[PROTECTED]";
    
    private final Validator validator;
	
    public AccountManager(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
        
    public Account AccountFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Account user = null;
        
        try {
            user = mapper.readValue(json, Account.class);
        } catch (JsonParseException exception) {
            
        } catch (JsonMappingException exception) {
            
        } catch (IOException exception) {
            
        }
        
        return user;
    }
    
    public Account addAccount(Account user) {
        Set<ConstraintViolation<Account>> errors = validator.validate(user);
        if(errors.size() > 0)
            return null;
        
        // Hash the user password.
        //user.setPassword(user.getPassword().hash());
        
        // Add the user.
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
                
        // Hide the password.
        user.setPassword(HIDDEN_DATA);
        
        return user;
    }
        
    public Account removeAccount(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Account user = (Account) session.get(Account.class, username);
        session.delete(user);
        session.getTransaction().commit();
        
        // Hide the password.
        user.setPassword(HIDDEN_DATA);
        
        return user;
    }
    
    public List<Account> getAccountsList() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Account> list = session.createCriteria(Account.class).list();
        session.getTransaction().commit();
        
        // Hide passwords.
        for(int i = 0; i < list.size(); i++) {
            list.get(i).setPassword(HIDDEN_DATA);
        }
        
        return list;		
    }
    
    public Account getAccountByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Account user = (Account) session.get(Account.class, username);
        Hibernate.initialize(user);
        session.getTransaction().commit();
        
        // Hide password.
        if(user != null) {
            user.setPassword(HIDDEN_DATA);
        }

        return user;
    }
    
}
