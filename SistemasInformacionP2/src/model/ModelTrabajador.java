package model;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import tablas.Categorias;
import tablas.Empresas;
import tablas.Trabajadorbbdd;

public class ModelTrabajador {
	public static Trabajadorbbdd crear(Session sesion, Categorias categorias, 
			Empresas empresas, String nombre, String apellido1, String apellido2, String nifnie, 
			String email, Date fechaAlta, String codigoCuenta, String iban) {
		//System.out.println("CHUNGO "+ categorias);
		System.out.println("fecha de alta "+fechaAlta);
		List<Categorias> lista2 = sesion.createQuery("from Categorias where NombreCategoria = '" + categorias.getNombreCategoria()+"'").list();
		List<Empresas> lista3 = sesion.createQuery("from Empresas where CIF = '" + empresas.getCif()+"'").list();
		
		Categorias objCat = sesion.load(Categorias.class, lista2.get(0).getIdCategoria());
		Empresas objEmp = sesion.load(Empresas.class, lista3.get(0).getIdEmpresa());
		//Trabajadorbbdd trabajador = new Trabajadorbbdd(objCat, objEmp, nombre, apellido1, nifnie);
		Trabajadorbbdd trabajador = new Trabajadorbbdd(objCat, objEmp, nombre, apellido1, apellido2, nifnie, email, fechaAlta, codigoCuenta, iban);
		
		List<Trabajadorbbdd> lista = sesion.createQuery("from Trabajadorbbdd where Nombre = '" + 
			trabajador.getNombre() + "' and NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'").list();
		System.out.println("tamaño lista trabajador "+lista.size());
		if (lista.size() == 0) {
			System.out.println("esta guardado");
			sesion.save(trabajador);
		}else {
		System.out.println("else");
			Query query = sesion.createQuery("update Trabajadorbbdd set Apellido1 = '" +trabajador.getApellido1()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result = query.executeUpdate();
			Query query2 = sesion.createQuery("update Trabajadorbbdd set Apellido2 = '" +trabajador.getApellido2()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result2 = query2.executeUpdate();
			Query query3 = sesion.createQuery("update Trabajadorbbdd set email = '" +trabajador.getEmail()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result3 = query3.executeUpdate();
			Query query4 = sesion.createQuery("update Trabajadorbbdd set CodigoCuenta = '" +trabajador.getCodigoCuenta()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result4 = query4.executeUpdate();
			Query query5 = sesion.createQuery("update Trabajadorbbdd set IdEmpresa = '" +trabajador.getEmpresas().getId()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result5 = query5.executeUpdate();
			Query query6 = sesion.createQuery("update Trabajadorbbdd set IdCategoria = '" +trabajador.getCategorias().getId()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result6 = query6.executeUpdate();
			
			Query query7 = sesion.createQuery("update Trabajadorbbdd set IBAN = '" +trabajador.getIban()+
    				"' where  Nombre = '" + trabajador.getNombre() + "' and NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'");
			int result7 = query7.executeUpdate();
			
			
			
		}
		/*List<Trabajadorbbdd> listaFinal = sesion.createQuery("from Trabajadorbbdd where Nombre = '" + 
			trabajador.getNombre() + "' and NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'").list();
		return listaFinal.get(0);*/
		List<Trabajadorbbdd> lista4 = sesion.createQuery("from Trabajadorbbdd where Nombre = '" + 
				trabajador.getNombre() + "' and NIFNIE = '" + trabajador.getNifnie() + "' and FechaAlta = '"+ trabajador.getFechaAlta()+"'").list();
		Trabajadorbbdd s = sesion.load(Trabajadorbbdd.class, lista4.get(0).getIdTrabajador());
		return s;
	}
}

