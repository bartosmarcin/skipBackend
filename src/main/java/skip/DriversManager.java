package skip;

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

}
