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

public class UserManager {
    
    private static final String HIDDEN_DATA = "?";
    
    private final Validator validator;
	
    public UserManager(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
        
    public User UserFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        
        try {
            user = mapper.readValue(json, User.class);
        } catch (JsonParseException exception) {
            
        } catch (JsonMappingException exception) {
            
        } catch (IOException exception) {
            
        }
        
        return user;
    }
    
    public User addUser(User user) {
        Set<ConstraintViolation<User>> errors = validator.validate(user);
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
    
    public User replaceUser(User user, String username) {
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        if(errors.size() > 0 || !user.getUsername().equals(username))
            return null;
        
        // Hash the user password.
        //user.setPassword(user.getPassword().hash());

        // Replace the user.
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        user = (User) session.merge(user);
        session.getTransaction().commit();
        
        // Hide the password.
        user.setPassword(HIDDEN_DATA);
        
        return user; 
    }
    
    public User removeUser(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, username);
        session.delete(user);
        session.getTransaction().commit();
        
        // Hide the password.
        user.setPassword(HIDDEN_DATA);
        
        return user;
    }
    
    public List<User> getUsersList() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<User> list = session.createCriteria(User.class).list();
        session.getTransaction().commit();
        
        // Hide passwords.
        for(int i = 0; i < list.size(); i++) {
            list.get(i).setPassword(HIDDEN_DATA);
        }
        
        return list;		
    }
    
    public User getUserByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, username);
        Hibernate.initialize(user);
        session.getTransaction().commit();
        
        // Hide password.
        if(user != null) {
            user.setPassword(HIDDEN_DATA);
        }

        return user;
    }
    
}
