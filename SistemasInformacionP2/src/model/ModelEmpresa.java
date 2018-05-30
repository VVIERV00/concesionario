package model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import tablas.Empresas;

public class ModelEmpresa {
	public static Empresas crear(Session sesion, String nombre, String CIF) {
		Empresas empresa = new Empresas(nombre, CIF);
		List<Empresas> lista = sesion.createQuery("from Empresas where CIF = '" + CIF+"'").list();
		

		if (lista.size() == 0) {
			sesion.save(empresa);
		}else {
			Query query = sesion.createQuery("update Empresas set Nombre = '" +nombre +"'" +
    				" where CIF = '"+ CIF+ "'");
			int result = query.executeUpdate();
			//sesion.saveOrUpdate(empresa);
		}
		return empresa;
	}
}
