package skip;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.Session;
import util.HibernateUtil;

public class AuthorityManager {
    
    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";
    
    private final Validator validator;
	
    public AuthorityManager(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
            
    public Authority addAuthority(Authority authority) {
        Set<ConstraintViolation<Authority>> errors = validator.validate(authority);
        if(errors.size() > 0)
            return null;

        // Add the authority.
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(authority);
        session.getTransaction().commit();
        
        return authority;
    }
    
    public Authority replaceAuthority(Authority authority, String username) {
        Set<ConstraintViolation<Authority>> errors = validator.validate(authority);
        if(errors.size() > 0 || !authority.getUsername().equals(username))
            return null;

        // Replace the authority.
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        authority = (Authority) session.merge(authority);
        session.getTransaction().commit();
 
        return authority; 
    }
    
    public Authority removeAuthority(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Authority authority = (Authority) session.get(Authority.class, username);
        session.delete(authority);
        session.getTransaction().commit();

        return authority;
    }
    
}
