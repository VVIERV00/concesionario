package model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import tablas.Categorias;
import tablas.Nomina;
import tablas.Trabajadorbbdd;

public class ModelNomina {
	public static Nomina crear(Session sesion, Trabajadorbbdd trabajadorbbdd, int mes, int anio, int numeroTrienios,double importeTrienios,
			double importeSalarioMes, double importeComplementoMes, double valorProrrateo, double brutoAnual,
			double irpf, double importeIrpf, double baseEmpresario, double seguridadSocialEmpresario,
			double importeSeguridadSocialEmpresario, double desempleoEmpresario, double importeDesempleoEmpresario,
			double formacionEmpresario, double importeFormacionEmpresario, double accidentesTrabajoEmpresario,
			double importeAccidentesTrabajoEmpresario, double fogasaempresario, double importeFogasaempresario,
			double seguridadSocialTrabajador, double importeSeguridadSocialTrabajador, double desempleoTrabajador,
			double importeDesempleoTrabajador, double formacionTrabajador, double importeFormacionTrabajador,
			double brutoNomina, double liquidoNomina, double costeTotalEmpresario) {
		
		Nomina nomina = new Nomina(trabajadorbbdd, mes, anio, numeroTrienios, importeTrienios, importeSalarioMes, importeComplementoMes, valorProrrateo, brutoAnual, irpf, importeIrpf, baseEmpresario, seguridadSocialEmpresario, importeSeguridadSocialEmpresario, desempleoEmpresario, importeDesempleoEmpresario, formacionEmpresario, importeFormacionEmpresario, accidentesTrabajoEmpresario, importeAccidentesTrabajoEmpresario, fogasaempresario, importeFogasaempresario, seguridadSocialTrabajador, importeSeguridadSocialTrabajador, desempleoTrabajador, importeDesempleoTrabajador, formacionTrabajador, importeFormacionTrabajador, brutoNomina, liquidoNomina, costeTotalEmpresario);
		@SuppressWarnings("unchecked")
		List<Nomina> lista = sesion.createQuery("from nomina where Mes = '" + nomina.getMes() + 
				"' and Anio = '" + nomina.getAnio()+ "' and IdTrabajador = '" + trabajadorbbdd.getIdTrabajador() + "' and nom.BrutoNomina = '" + 
				nomina.getBrutoNomina() + "' and LiquidoNomina = '" + nomina.getLiquidoNomina()+ "'").list();
		if (lista.size() == 0) {
			sesion.save(nomina);
		}else {
			Query query = sesion.createQuery("update Nomina set importeSalarioMes = '" + nomina.getImporteSalarioMes() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			int result = query.executeUpdate();
			
			
			query = sesion.createQuery("update Nomina set valorProrrateo = '" + nomina.getValorProrrateo() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			
			query = sesion.createQuery("update Nomina set irpf = '" + nomina.getIrpf() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			
			query = sesion.createQuery("update Nomina set importeIrpf = '" + nomina.getImporteIrpf() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			
			query = sesion.createQuery("update Nomina set baseEmpresario = '" + nomina.getBaseEmpresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			
			query = sesion.createQuery("update Nomina set seguridadSocialEmpresario = '" + nomina.getSeguridadSocialEmpresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set importeSeguridadSocialEmpresario = '" + nomina.getImporteSeguridadSocialEmpresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set desempleoEmpresario = '" + nomina.getDesempleoEmpresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set importeDesempleoEmpresario = '" + nomina.getImporteDesempleoEmpresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set formacionEmpresario = '" + nomina.getFormacionEmpresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set importeFormacionEmpresario = '" + nomina.getImporteFormacionEmpresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set accidentesTrabajoEmpresario = '" + nomina.getAccidentesTrabajoEmpresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set importeAccidentesTrabajoEmpresario = '" + nomina.getImporteAccidentesTrabajoEmpresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set fogasaempresario = '" + nomina.getFogasaempresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set importeFogasaempresario = '" + nomina.getImporteFogasaempresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set seguridadSocialTrabajador = '" + nomina.getImporteSeguridadSocialTrabajador() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set importeSeguridadSocialTrabajador = '" + nomina.getImporteSeguridadSocialTrabajador() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set formacionTrabajador = '" + nomina.getFormacionTrabajador() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set importeFormacionTrabajador = '" + nomina.getImporteFormacionTrabajador() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			query = sesion.createQuery("update Nomina set costeTotalEmpresario = '" + nomina.getCosteTotalEmpresario() +
    				"' where Mes = '"+ nomina.getMes()+ "' and Anio = '"+nomina.getAnio()+ "' and IdTrabajador= '"+nomina.getTrabajadorbbdd().getIdTrabajador()+
    				"' and BrutoNomina='"+nomina.getBrutoNomina()+"' and LiquidoNomina='"+nomina.getLiquidoNomina());
			result = query.executeUpdate();
			
			
		}
		/*List<Nomina> listaFinal = sesion.createQuery("from nomina where Mes = " + mes + 
				" and Anio = " + anio + " and IdTrabajador = " + trabajadorbbdd.getIdTrabajador() + "and nom.BrutoNomina = " + 
				brutoNomina + "and LiquidoNomina = " + liquidoNomina).list();
		return listaFinal.get(0);*/
		return nomina;
	}
}
