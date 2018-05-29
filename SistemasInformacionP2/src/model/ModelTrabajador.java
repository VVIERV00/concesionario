package model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import tablas.Categorias;
import tablas.Empresas;
import tablas.Trabajadorbbdd;

public class ModelTrabajador {
	public static Trabajadorbbdd crear(Session sesion, Categorias categorias, 
			Empresas empresas, String nombre, String apellido1, String apellido2, String nifnie, 
			String email, Date fechaAlta, String codigoCuenta, String iban, Set nominas) {
		List<Categorias> lista = sesion.createQuery("from trabajadorbbdd as trab where trab.Nombre = " + 
			nombre + " and trab.NIFNIE = " + nifnie + " and trab.FechaAlta = ").list();
		Trabajadorbbdd trabajador = new Trabajadorbbdd(categorias, empresas, 
				nombre, apellido1, apellido2, nifnie, email, fechaAlta, codigoCuenta, 
				iban, nominas);
		if (lista.size() == 0) {
			sesion.save(trabajador);
		}else {
			sesion.update(trabajador); //no se si hace update al que debe, mas le vale.
		}
		return trabajador;
	}
}

