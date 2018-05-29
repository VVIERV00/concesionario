package model;

import java.util.List;

import org.hibernate.Session;

import tablas.Empresas;

public class ModelEmpresa {
	public static Empresas crear(Session sesion, String nombre, String CIF) {
		Empresas empresa = new Empresas(nombre, CIF);
		List<Empresas> lista = sesion.createQuery("from empresas where CIF = " + CIF).list();

		if (lista.size() == 0) {
			sesion.save(empresa);
		}else {
			sesion.update(empresa);
		}
		return empresa;
	}
}
