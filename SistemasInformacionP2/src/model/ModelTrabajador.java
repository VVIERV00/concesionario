package model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import tablas.Categorias;
import tablas.Empresas;
import tablas.Trabajadorbbdd;

public class ModelTrabajador {
	public static Trabajadorbbdd crear(Session sesion, Integer categorias, 
			Integer empresas, String nombre, String apellido1, String apellido2, String nifnie, 
			String email, Date fechaAlta, String codigoCuenta, String iban) {
		@SuppressWarnings("unchecked")
		List<Trabajadorbbdd> lista = sesion.createQuery("from Trabajadorbbdd where Nombre = '" + 
			nombre + "' and NIFNIE = '" + nifnie + "' and FechaAlta = '"+ fechaAlta+"'").list();
		Categorias objCat = sesion.load(Categorias.class, categorias);
		Empresas objEmp = sesion.load(Empresas.class, empresas);
		Trabajadorbbdd trabajador = new Trabajadorbbdd(objCat, objEmp, nombre, apellido1, nifnie);
		/*Trabajadorbbdd trabajador = new Trabajadorbbdd(categorias, empresas, 
				nombre, apellido1, apellido2, nifnie, email, fechaAlta, codigoCuenta, 
				iban, nominas);*/
		if (lista.size() == 0) {
			System.out.println("empieza asunto");
			sesion.save(trabajador);
			System.out.println("acaba asunto");
		}else {
			Query query = sesion.createQuery("update Trabajadorbbdd set Apellido1 = '" +trabajador.getApellido1()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and trab.NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result = query.executeUpdate();
			Query query2 = sesion.createQuery("update Trabajadorbbdd set Apellido2 = '" +trabajador.getApellido2()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and trab.NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result2 = query2.executeUpdate();
			Query query3 = sesion.createQuery("update Trabajadorbbdd set email = '" +trabajador.getEmail()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and trab.NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result3 = query3.executeUpdate();
			Query query4 = sesion.createQuery("update Trabajadorbbdd set CodigoCuenta = '" +trabajador.getCodigoCuenta()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and trab.NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result4 = query4.executeUpdate();
			Query query5 = sesion.createQuery("update Trabajadorbbdd set IdEmpresa = '" +trabajador.getEmpresas()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and trab.NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result5 = query5.executeUpdate();
			Query query6 = sesion.createQuery("update Trabajadorbbdd set IdCategoria = '" +trabajador.getCategorias()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and trab.NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result6 = query6.executeUpdate();
			
			Query query7 = sesion.createQuery("update Trabajadorbbdd set IBAN = '" +trabajador.getIban()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and trab.NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result7 = query7.executeUpdate();
			
		}
		return trabajador;
	}
}

