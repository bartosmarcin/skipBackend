package util;

import java.util.StringTokenizer;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration c = new Configuration();
        	String databaseUrl = System.getenv("DATABASE_URL");
        	StringTokenizer st = new StringTokenizer(databaseUrl, ":@/");
        	String dbVendor = st.nextToken(); //if DATABASE_URL is set
        	String userName = st.nextToken();
        	String password = st.nextToken();
        	String host = st.nextToken();
        	String port = st.nextToken();
        	String databaseName = st.nextToken();
        	String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", host, port, databaseName);
        	c.setProperty("javax.persistence.jdbc.url", databaseUrl );
        	c.setProperty("javax.persistence.jdbc.user", userName );
        	c.setProperty("javax.persistence.jdbc.password", password );
        	c.setProperty("javax.persistence.jdbc.driver", "org.postgresql.Driver");
        	c.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        	
        	return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}