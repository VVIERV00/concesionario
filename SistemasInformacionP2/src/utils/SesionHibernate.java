package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SesionHibernate {
	private static SessionFactory sesion;
	
	static {
		sesion = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
	}
	public static SessionFactory getSesion() {
		return sesion;
	}
}
