package model;

import org.hibernate.Session;
import org.hibernate.Transaction;

import utils.SesionHibernate;

public class Principal {
	/*
	 * Clase de ejemplo para probar clase model hibernate
	 * 
	 */
	private static Session sesion;
	public static void main(String[] args) {
		sesion = SesionHibernate.getSesion().openSession();
		Transaction tx = sesion.beginTransaction();
		
		
		
		
		
		tx.commit();
		sesion.close();
	}

}
