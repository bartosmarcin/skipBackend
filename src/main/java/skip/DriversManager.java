package skip;


import org.hibernate.Session;

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
		Driver d = (Driver)session.load(Driver.class, id);	
		return d;
	}

}
