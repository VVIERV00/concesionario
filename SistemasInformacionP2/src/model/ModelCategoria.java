package model;

import java.util.List;

import org.hibernate.Filter;
import org.hibernate.Session;

import tablas.Categorias;

public class ModelCategoria {
	public static Categorias crear(Session sesion, String nombre, Double salarioBase, Double complemento) {
		//Filter filter = sesion.enableFilter("filtroCategorias");
		//filter.setParameter("nombre", nombre);
		//Categorias filtro = sesion.get(Categorias.class, "s");
		List<Categorias> lista = sesion.createQuery("from categorias where NombreCategoria = " + nombre).list();
		Categorias categoria = new Categorias(nombre, salarioBase, complemento);

		if (lista.size() == 0) {
			sesion.save(categoria);
		}else {
			sesion.update(categoria);
		}
		//sesion.disableFilter("filtroCategorias");
		
		return categoria;
	}
}
