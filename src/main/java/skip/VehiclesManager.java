package skip;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import util.HibernateUtil;

public class VehiclesManager {

	private Validator validator;

	public VehiclesManager() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public Vehicle addVehicle(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Vehicle v;
		try {
			v = mapper.readValue(json, Vehicle.class);
			return this.addVehicle(v);
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

	public Vehicle addVehicle(Vehicle v) {
		Set<ConstraintViolation<Vehicle>> errors = validator.validate(v);
		if (errors.size() > 0)
			return null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(v);
		session.getTransaction().commit();
		return v;
	}

	public Vehicle removeVehicle(long id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Vehicle v = (Vehicle) session.get(Vehicle.class, id);
		session.delete(v);
		session.getTransaction().commit();
		return v;
	}

	public Vehicle getVehicleById(long id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Vehicle v = (Vehicle) session.get(Vehicle.class, id);
		Hibernate.initialize(v);
		session.getTransaction().commit();
		return v;
	}

	public List<Vehicle> getVehiclesList() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Vehicle> vlist = session.createCriteria(Vehicle.class).list();
		session.getTransaction().commit();
		return vlist;
	}
}
