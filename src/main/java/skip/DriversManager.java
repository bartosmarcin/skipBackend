package skip;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import ch.qos.logback.classic.Logger;

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
	
	public Driver driverFromJson(String json){
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
	
	public Coordinates coordinatesFromJson(String json){
		ObjectMapper mapper = new ObjectMapper();
		Coordinates c;
		try {
			c = mapper.readValue(json, Coordinates.class);
			return c;
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
		Driver d= this.driverFromJson(json);
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
		try{
			session.beginTransaction();
			session.save(d);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			return null;
		}
		return d;
	}
	
	public Driver replaceDriver(String json, long id){
		Driver d = this.driverFromJson(json);
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
		try{
			session.beginTransaction();
			d = (Driver)session.merge(d);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			return null;
		}
		return d;
	}
	
	public Driver removeDriver(long id){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Driver d = null;
		try{
			session.beginTransaction();
			d = (Driver) session.get(Driver.class, id);
            if(d != null)
            	session.delete(d);
            session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			return null;
		}
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
	
	public Coordinates updateDriverCoordinates(String json, long driverId){
		Coordinates c = this.coordinatesFromJson(json);
		if( c==null )
			return null;
		return this.updateDriverCoordinates(c, driverId);					
	}
	
	public Coordinates updateDriverCoordinates(Coordinates coordinates, long driverId){
		Set<ConstraintViolation<Coordinates>> 
		errors = validator.validate(coordinates);
		if( errors.size() > 0 )
			return null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Driver d = (Driver)session.get(Driver.class, driverId);
			Hibernate.initialize(d);
			d.addCoordinates(coordinates);
			d.setCoordinatesUpdateDate(new Date());
			//session.merge(d);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			return null;
		}
		return coordinates;
	}
	
	public List<Driver> getDriversList(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<Driver> dlist = null;
		try{
			session.beginTransaction();
			dlist = session.createCriteria(Driver.class).list();
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		}
		return dlist;		
	}

	public Vehicle assignVehicle(long driverId, Long vehicleId){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			System.out.println(vehicleId);
			session.beginTransaction();
			Driver d = (Driver)session.get(Driver.class, driverId);
			Hibernate.initialize(d);
			Vehicle v = null;
			if(vehicleId != null)
				v = (Vehicle)session.get(Vehicle.class, vehicleId);
			d.setAssignedVehicle(v);
			session.merge(d);
			session.getTransaction().commit();
			return v;
		}catch(Exception e){
			session.getTransaction().rollback();
			System.out.println(e.getMessage());
			System.out.println("Exception during assignVehicle transaction");
			return null;
		}
	}

}
