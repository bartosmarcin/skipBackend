package skip;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import util.HibernateUtil;

public class VehiclesManager {
	public Vehicle addVehicle(Vehicle v) {
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
	
	public List<Vehicle> getVehiclesList(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Vehicle> vlist = session.createCriteria(Vehicle.class).list();
		session.getTransaction().commit();
		return vlist;		
	}
}
