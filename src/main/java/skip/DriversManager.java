package skip;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.*;

import skip.Driver;
import util.HibernateUtil;


public class DriversManager {
	
	public void addDriver(Driver d){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(d);
		session.getTransaction().commit();
	}
	
	public Driver getDriverById(long id){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Driver d = (Driver)session.get(Driver.class, id);	
		Hibernate.initialize(d);
		return d;
	}

}
