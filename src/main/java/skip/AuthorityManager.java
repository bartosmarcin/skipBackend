package skip;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import util.HibernateUtil;

public class AuthorityManager {
    
    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String MASTER = "ROLE_MASTER";
    
    private final Validator validator;
	
    public AuthorityManager(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    public Authority getAuthorityByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Authority authority = (Authority) session.get(Authority.class, username);
        Hibernate.initialize(authority);
        session.getTransaction().commit();
        
        return authority;
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
        
    public Authority removeAuthority(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Authority authority = (Authority) session.get(Authority.class, username);
        session.delete(authority);
        session.getTransaction().commit();

        return authority;
    }
    
    public List<Authority> getAuthoritiesList() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Authority> list = session.createCriteria(Authority.class).list();
        session.getTransaction().commit();
        
        return list;
    }
    
}
