package skip;


import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import skip.Driver;
import util.HibernateUtil;


public class DriversManager {
	private Validator validator;
	
	public DriversManager(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	public Driver DriverFromJson(String json){
		ObjectMapper mapper = new ObjectMapper();
		Driver d;
		try {
			d = mapper.readValue(json, Driver.class);
			return d;
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
	
	public Driver addDriver(String json){
		Driver d= this.DriverFromJson(json);
		if( d == null)
			return null;
		return this.addDriver(d);
	}
	
	public Driver addDriver(Driver d){
		Set<ConstraintViolation<Driver>> 
				errors = validator.validate(d);
		if( errors.size() > 0)
			return null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(d);
		session.getTransaction().commit();
		return d;
	}
	
	public Driver replaceDriver(String json, long id){
		Driver d = this.DriverFromJson(json);
		if( d == null)
			return null;
		return this.replaceDriver(d, id);
	}
	
	public Driver replaceDriver(Driver d, long id){
		Set<ConstraintViolation<Driver>> 
				errors = validator.validate(d);
		if( errors.size() > 0 || d.getId() != id)
			return null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		d = (Driver)session.merge(d);
		session.getTransaction().commit();
		return d;
	}
	
	public Driver removeDriver(long id){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Driver d = (Driver) session.get(Driver.class, id);
		session.delete(d);
		session.getTransaction().commit();
		return d;
	}
	
	public Driver getDriverById(long id){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Driver d = (Driver)session.get(Driver.class, id);
		Hibernate.initialize(d);
		session.getTransaction().commit();
		return d;
	}
	
	public List<Driver> getDriversList(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Driver> dlist = session.createCriteria(Driver.class).list();
		session.getTransaction().commit();
		return dlist;		
	}

}
