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

public class StatementsManager {

    private Validator validator;

    public StatementsManager() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public Statement statementFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Statement s;
        try {
            s = mapper.readValue(json, Statement.class);
            return s;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Statement addStatement(String json) {
        Statement s = this.statementFromJson(json);
        if (s == null) {
            return null;
        }
        return this.addStatement(s);
    }

    public Statement addStatement(Statement s) {
        Set<ConstraintViolation<Statement>> errors = validator.validate(s);
        if (errors.size() > 0) {
            return null;
        }
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(s);
        session.save(s.getcoordinates());
        session.getTransaction().commit();
        return s;
    }

    public Statement removeStatement(long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Statement s = (Statement) session.get(Statement.class, id);
        if(s != null)
            session.delete(s);
        session.getTransaction().commit();
        return s;
    }

    public Statement getStatementById(long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Statement s = (Statement) session.get(Statement.class, id);
        Hibernate.initialize(s);
        session.getTransaction().commit();
        return s;
    }

    public List<Statement> getStatementsList() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Statement> slist = session.createCriteria(Statement.class).list();
        session.getTransaction().commit();
        return slist;
    }

}
