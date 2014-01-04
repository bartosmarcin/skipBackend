package skip;


import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import skip.Driver;
import util.HibernateUtil;


public class DriversManager {
	
	public Driver addDriver(Driver d){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(d);
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
