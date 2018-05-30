package model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tablas.Categorias;
import tablas.Empresas;
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
		System.out.println("empieza");
		List<Categorias> lista = sesion.createQuery("from Categorias where NombreCategoria = 'funcionario'").list();
		List<Empresas> lista2 = sesion.createQuery("from Empresas where CIF = 'P2463928T'").list();

		
		ModelTrabajador.crear(sesion, lista.get(0).getIdCategoria(), lista2.get(0).getIdEmpresa(), "fernando", "asuncion", 
				"71404629W", "71404629W","sd", new Date("20/10/2015"), "ese", 
				"aquel", new HashSet<T>());
		System.out.println("acaba");
		
		
		tx.commit();
		sesion.close();
	}

}
