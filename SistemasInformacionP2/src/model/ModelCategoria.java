package model;

import java.util.List;

import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;

import tablas.Categorias;

public class ModelCategoria {
	public static Categorias crear(Session sesion, String nombre, Double salarioBase, Double complemento) {
		//Filter filter = sesion.enableFilter("filtroCategorias");
		//filter.setParameter("nombre", nombre);
		//Categorias filtro = sesion.get(Categorias.class, "s");
		Categorias categoria = new Categorias(nombre, salarioBase, complemento);
		List<Categorias> lista = sesion.createQuery("from Categorias where NombreCategoria = '" + categoria.getNombreCategoria()+"'").list();

		if (lista.size() == 0) {
			sesion.save(categoria);
		}else {
			Query query = sesion.createQuery("update Categorias set SalarioBaseCategoria = '" + categoria.getSalarioBaseCategoria() +
    				"' where NombreCategoria = '"+ categoria.getNombreCategoria()+ "'");
			int result = query.executeUpdate();
			Query query2 = sesion.createQuery("update Categorias set ComplementoCategoria = '" +  categoria.getComplementoCategoria()+
    				"' where NombreCategoria = '"+ categoria.getNombreCategoria()+ "'");
			int result2 = query2.executeUpdate();
		}
		//sesion.disableFilter("filtroCategorias");
		
		return categoria;
	}
}
