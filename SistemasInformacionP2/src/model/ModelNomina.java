package model;

import java.util.List;

import org.hibernate.Session;

import tablas.Categorias;
import tablas.Nomina;
import tablas.Trabajadorbbdd;

public class ModelNomina {
	public static void crear(Session sesion, Trabajadorbbdd trabajadorbbdd, int mes, int anio, int numeroTrienios,double importeTrienios,
			double importeSalarioMes, double importeComplementoMes, double valorProrrateo, double brutoAnual,
			double irpf, double importeIrpf, double baseEmpresario, double seguridadSocialEmpresario,
			double importeSeguridadSocialEmpresario, double desempleoEmpresario, double importeDesempleoEmpresario,
			double formacionEmpresario, double importeFormacionEmpresario, double accidentesTrabajoEmpresario,
			double importeAccidentesTrabajoEmpresario, double fogasaempresario, double importeFogasaempresario,
			double seguridadSocialTrabajador, double importeSeguridadSocialTrabajador, double desempleoTrabajador,
			double importeDesempleoTrabajador, double formacionTrabajador, double importeFormacionTrabajador,
			double brutoNomina, double liquidoNomina, double costeTotalEmpresario) {
		
		Nomina nomina = new Nomina(trabajadorbbdd, mes, anio, numeroTrienios, importeTrienios, importeSalarioMes, importeComplementoMes, valorProrrateo, brutoAnual, irpf, importeIrpf, baseEmpresario, seguridadSocialEmpresario, importeSeguridadSocialEmpresario, desempleoEmpresario, importeDesempleoEmpresario, formacionEmpresario, importeFormacionEmpresario, accidentesTrabajoEmpresario, importeAccidentesTrabajoEmpresario, fogasaempresario, importeFogasaempresario, seguridadSocialTrabajador, importeSeguridadSocialTrabajador, desempleoTrabajador, importeDesempleoTrabajador, formacionTrabajador, importeFormacionTrabajador, brutoNomina, liquidoNomina, costeTotalEmpresario);
		List<Categorias> lista = sesion.createQuery("from nomina as nom where nom.Mes = " + mes + 
				" and nom.Anio = " + anio + " and nom.IdTrabajador = " + trabajadorbbdd.getIdTrabajador() + "and nom.BrutoNomina = " + 
				brutoNomina + "and nom.LiquidoNomina = " + liquidoNomina).list();
		if (lista.size() == 0) {
			sesion.save(nomina);
		}else {
			sesion.update(nomina); //no se si hace update al que debe, mas le vale.
		}
		sesion.save(nomina);
	}
}
