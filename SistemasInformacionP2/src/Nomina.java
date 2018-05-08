import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Nomina{
	private  String[][] datos1;
	private  String ruta;
	private Date fecha;

	public Nomina(String[][] datos1, String ruta, Date fecha) {
		this.datos1 = datos1;
		this.ruta = ruta;
		this.fecha = fecha;
	}
	public void generar() {
		ReadWriteExcelFile excel = new ReadWriteExcelFile();
		try {
			ArrayList<ArrayList<String>> hoja2 = excel.readXLSXFileHoja2(ruta);
			String[][] hoja1 = datos1;
			ArrayList<Map> hoja2Map = new ArrayList<Map>();
			Map<String, Integer> salarioBase = new HashMap<String, Integer>();
			Map<String, Integer> complementos = new HashMap<String, Integer>();
			Map<String, Integer> cotizacion = new HashMap<String, Integer>();
			Map<String, Float> cuotas = new HashMap<String, Float>();
			Map<Integer, Integer> trienios = new HashMap<Integer, Integer>();
			Map<Integer, Float> retencion = new HashMap<Integer, Float>();

			int i;
			for (i = 1; i<15; i++) {
				salarioBase.put(hoja2.get(i).get(0), Integer.valueOf(hoja2.get(i).get(1)));
				complementos.put(hoja2.get(i).get(0), Integer.valueOf(hoja2.get(i).get(2)));
				cotizacion.put(hoja2.get(i).get(0), Integer.valueOf(hoja2.get(i).get(3)));
			}
			for(i = 0; i<8; i++) {
				cuotas.put(hoja2.get(i+17).get(0), Float.valueOf(hoja2.get(i+17).get(1)));
			}

			for (i = 0; i<18; i++) {
				trienios.put(Integer.valueOf(hoja2.get(i+18).get(3)), Integer.valueOf(hoja2.get(i+18).get(4)));				
			}

			for (i = 1; i<50; i++) {
				retencion.put(Integer.valueOf(hoja2.get(i).get(5)), Float.valueOf(hoja2.get(i).get(6)));				
			}

			hoja2Map.add(salarioBase);
			hoja2Map.add(complementos);
			hoja2Map.add(cotizacion);
			hoja2Map.add(trienios);
			hoja2Map.add(retencion);
			hoja2Map.add(cuotas);


			Date fecha = new Date(new java.util.Date().getTime());
			for (String[] fila: hoja1) {
				if(fechaValida(fila[8], fila[10])) {
					this.generarNomina(fila, fecha, hoja2Map);
				}
			}

		} catch (IOException e) {
			System.out.println("Error al leer del excel: " + e.toString());
		}

	}

	/*
	 * 
	 * fila: Array de String correspondiente a la fila de una persona
	 * fecha: ---SQL.DATE--- Fecha de la nomina
	 *hoja2: ---UTIL.LIST--- Array con maps correspondiente a los grupos key-value correspondientes a la hoja2 del excel.
	 *  Ejemplo hoja2: (en total habra 6 maps)
	 *  
	 *  Map mapCAtegoriaSalario = new HashMap();
	 *	List<Map> list = new ArrayList();
	 *list.add(
	 */
	public void generarNomina(String [] fila, Date fecha, ArrayList<Map> hoja2) { //Wrote while playing romeo santos, code prone to errors.
		StringBuilder persona = new StringBuilder();
		StringBuilder empresa = new StringBuilder();

		boolean prorateo = (fila[13] == "SI");
		Float[] complementoYAntiguedad = getComplementos(fila, fecha, hoja2); //[0]:Complemento [1]:Antiguedad
		Float[] salarioBruto = getBruto(fila, prorateo, complementoYAntiguedad);//[0]:anual [1]:mensual
		Float[] descuentos = getDescuentos(fila, hoja2, salarioBruto[0], prorateo); //[0]:SSocial [1]:Formacion [2]: Desempleo [3]: IRPF
		Float[] salarioNeto = getNeto(fila, prorateo, complementoYAntiguedad, descuentos); //[0]:anual [1]:mensual
		//Float[] salarioAnual = getAnual(salarioMensual, prorateo, fila); //multiplica por 12, 14 o menos si no trabajo todo el a�o. Fila para sacar la fechadeEntradaYAlta

		Float[] pagosEmpresario = getPagosEmpresario(fila, hoja2); //[0]SSocial [1]FOGASA  [2]Desempleo [3]Formaci�n [4]Mutua //el resto creo que no se puede
		String esExtra = (fecha.getMonth() == 5 || fecha.getMonth() == 11) ? "es una EXTRA": "";
		persona.append("/n/tNombre: "+ fila[3] + " " + fila[1] + " "+ fila[2]+ "/n/tIBAN: " + fila[14] +"/n/tCATEGORIA: "
				+ fila[5] + "/n/tBrutoAnual: "+ salarioAnual[1]+" " + esExtra + "/n/tFecha Alta: " + fila[8] + "/n/n/n/n");
		persona.append("/t/t/tN�mina fecha: " + fecha.toString());
		empresa.append("/tEmpresa: " + fila[6] + " /n/tCIF: " + fila[7]);

		String [] foo = {persona.toString(), empresa.toString()};
		this.crearPDF(foo);

	}
	private Float[] getBruto(String[] fila, boolean prorateo, Float[] complementoYAntiguedad) { //1¡0_ anual 1_ mensual  trienios?
		// TODO Auto-generated method stub
		return null;
	}
	private  Float[] getPagosEmpresario(String[] fila, ArrayList<Map> hoja2) {
		// TODO Auto-generated method stub
		return null;
	}

	private Float[] getNeto(String[] fila, boolean prorateo, Float[] complementoYAntiguedad,Float[] descuentos) { //anual y mensual
		// TODO Auto-generated method stub
		
		return null;
	}
	

private static Float[] getDescuentos(String[] fila, ArrayList<Map> hoja2, Float brutoAnual, boolean  prorateo) {
		
		Float sSocial = 0.0f; Float formacion = 0.0f; Float desempleo = 0.0f; Float irpf = 0.0f;
		
		sSocial = (prorateo) ? (brutoAnual/ (Float)hoja2.get(5).get("Contingencias comunes TRABAJADOR")) : (((brutoAnual/14)*12) / (Float)hoja2.get(5).get("Contingencias comunes TRABAJADOR")) ;
		formacion = (prorateo) ? (brutoAnual/ (Float)hoja2.get(5).get("Cuota formación TRABAJADOR")) : (((brutoAnual/14)*12) / (Float)hoja2.get(5).get("Contingencias comunes TRABAJADOR")) ;
		desempleo = (prorateo) ? (brutoAnual/ (Float)hoja2.get(5).get("Cuota desempleo TRABAJADOR")) : (((brutoAnual/14)*12) / (Float)hoja2.get(5).get("Contingencias comunes TRABAJADOR")) ;

		Set set = hoja2.get(4).keySet();
		Iterator iterador = set.iterator();
		Integer temp = null;
		Integer siguiente = (Integer) iterador.next();
		while (iterador.hasNext() && siguiente < brutoAnual) {
			temp = siguiente;
			siguiente = (Integer) iterador.next();
		}
		irpf = (prorateo) ? ((brutoAnual / 12) / (Integer) hoja2.get(4).get(temp)) : ((brutoAnual / 14) / (Integer) hoja2.get(4).get(temp));
		
		Float[] resultado = {sSocial, formacion, desempleo, irpf};
		
		
		return resultado;
	}

	private Float[] getComplementos(String[] fila, Date fecha, ArrayList<Map> hoja2) {
		// Bruto anual es = Salario Base Anual + Complementos + lo que se suma en funcion de los trienios
		//Float [] en [0]->complemento y en [1]->antiguedad
		String profesion = fila[5];
		Float [] resultados;
		//calcular complemento
		float complemento=(Float) hoja2.get(2).get(profesion);
		//calcular antiguedad
		String f = fila[8];
		@SuppressWarnings("deprecation")
		Date fechaAlta = new Date(Integer.valueOf(f.substring(6, 10)), Integer.valueOf(f.substring(3,5))-1, Integer.valueOf(f.substring(0, 2)));
		int difA,mes;
		
		mes = fechaAlta.getMonth()+1;
		difA = fecha.getYear()-fechaAlta.getYear();
		float trienios;
		if(difA<3) {
			trienios=(float) 0;
			resultados = new Float[2];
			resultados[0] = complemento;
			resultados[1] = trienios;
		}else {
			if(difA%3==0) {
				//a�o conflictivo
				trienios = (Float)hoja2.get(3).get(difA/3-1)*(mes)+ (Float)hoja2.get(3).get(difA/3)+(12-mes);
				resultados = new Float[5];
				resultados[1] = (float) mes;//mes a partir del cual se empieza a contar el nuevo trienio
				resultados[2] = (Float) hoja2.get(3).get(difA/3-1);//valor del trienio antes de empezar a contar el nuevo
				resultados[3] = (Float) hoja2.get(3).get(difA/3);//valor del trienio despues de cumplir trienio
				resultados[4] = trienios;//total por trienios al a�o
				if(mes>=6) {
					//en la primera paga estraordinaria no se incluye el nuevo trienio
					resultados[4] += resultados[2]+resultados[3];
				}else {
					resultados[4]+= resultados[3]+resultados[3];
				}
			}else {
				//a�o no conflictivo
				trienios=(Float) hoja2.get(3).get(difA/3)*14;
				resultados = new Float[2];
				resultados[0] = complemento;
				resultados[1] = trienios;
			}
		}
		return resultados;
	}

	/*
	 * Recibe un array para dejar el pdf bonito con cuadrados
	 * 
	 */
	public void crearPDF(String[] nomina) {  

	}

	public boolean fechaValida(String fechaI, String fechaF){
		if(fechaF=="") {
			@SuppressWarnings("deprecation")
			Date i = new Date(Integer.valueOf(fechaI.substring(6, 10)), Integer.valueOf(fechaI.substring(3,5))-1, Integer.valueOf(fechaI.substring(0, 2)));
			return fecha.after(i);
		}else {
			@SuppressWarnings("deprecation")
			Date i = new Date(Integer.valueOf(fechaI.substring(6, 10)), Integer.valueOf(fechaI.substring(3,5))-1, Integer.valueOf(fechaI.substring(0, 2)));
			@SuppressWarnings("deprecation")
			Date f = new Date(Integer.valueOf(fechaF.substring(6, 10)), Integer.valueOf(fechaF.substring(3,5))-1, Integer.valueOf(fechaF.substring(0, 2)));

			return fecha.after(i)&&fecha.before(f);
		}
	}



}
