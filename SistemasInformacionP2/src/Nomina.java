import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;


public class Nomina{
	private  String[][] datos1;
	private  String ruta;
	private Date fecha;
	private Float irpfPer = 0.0f;
	public static void main(String[] args) {
		String[][] foo = null;
		try {
			foo = new ReadWriteExcelFile().readXLSXFile("SistemasInformacionII.xlsx");
		} catch (IOException e) {
			System.out.println("problema: " + e);
		}
		Nomina objeto = new Nomina(foo,"SistemasInformacionII.xlsx", new Date(2016-1900,05,01));
		objeto.generar();

	}

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

			for (i = 0; i<7; i++) {
				//System.out.println(hoja2.get(i+18).get(3) +" ---- "+ hoja2.get(i+18).get(4));

				trienios.put(Integer.valueOf(hoja2.get(i+18).get(3)), Integer.valueOf(hoja2.get(i+18).get(4)));				
			}
			for (i = 7; i<18; i++) {
				//System.out.println(hoja2.get(i+18).get(0) +" ---- "+ hoja2.get(i+18).get(1));

				trienios.put(Integer.valueOf(hoja2.get(i+18).get(0)), Integer.valueOf(hoja2.get(i+18).get(1)));				
			}

			for (i = 1; i<15; i++) {
				retencion.put(Integer.valueOf(hoja2.get(i).get(5)), Float.valueOf(hoja2.get(i).get(6)));				
			}
			for (i = 15; i<17; i++) {
				retencion.put(Integer.valueOf(hoja2.get(i).get(0)), Float.valueOf(hoja2.get(i).get(1)));				
			}
			for (i = 17; i<25; i++) {
				retencion.put(Integer.valueOf(hoja2.get(i).get(5)), Float.valueOf(hoja2.get(i).get(6)));				
			}
			for (i = 25; i<35; i++) {
				retencion.put(Integer.valueOf(hoja2.get(i).get(2)), Float.valueOf(hoja2.get(i).get(3)));				
			}
			for (i = 35; i<50; i++) {
				retencion.put(Integer.valueOf(hoja2.get(i).get(0)), Float.valueOf(hoja2.get(i).get(1)));				
			}

			hoja2Map.add(salarioBase);
			hoja2Map.add(complementos);
			hoja2Map.add(cotizacion);
			hoja2Map.add(trienios);
			hoja2Map.add(retencion);
			hoja2Map.add(cuotas);

			//Date fecha = new Date(new java.util.Date().getTime());
			for (String[] fila: hoja1) {
				if (!fila.equals(hoja1[0])){
					//if(fechaValida(fila[8], fila[10])) {
					this.generarNomina(fila, fecha, hoja2Map);
					//}
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
		System.out.println("fechaa " + fecha);
		StringBuilder persona = new StringBuilder();
		StringBuilder empresa = new StringBuilder();
		boolean prorateo = (fila[13] == "SI");
		Float[] complementoYAntiguedad = getComplementos(fila, fecha, hoja2); //[0]:Complemento [1]:Antiguedad
		Integer xx = (Integer) hoja2.get(0).get(fila[5]);
		Float[] salarioMensual = {Float.valueOf(Integer.toString(xx))};

		salarioMensual[0] /= 12;
		Float[] salarioBruto = getBrutoAnual(salarioMensual[0], prorateo, fila, complementoYAntiguedad);//[0]:anual [1]:mensual

		///System.out.println("---"+fila);

		//System.out.println("---"+hoja2);
		//System.out.println("---"+salarioBruto[0]);
		//System.out.println("---"+prorateo);

		Float[] descuentos = getDescuentos(fila, hoja2, salarioBruto, prorateo, complementoYAntiguedad, salarioMensual, fecha); //[0]:SSocial [1]:Formacion [2]: Desempleo [3]: IRPF
		Float[] salarioNeto = getNeto(fila, prorateo, complementoYAntiguedad, descuentos); //[0]:anual [1]:mensual
		//Float[] salarioAnual = getAnual(salarioMensual, prorateo, fila); //multiplica por 12, 14 o menos si no trabajo todo el a�o. Fila para sacar la fechadeEntradaYAlta
		Float[] pagosEmpresario = getPagosEmpresario(fila, hoja2, salarioBruto); //[0]SSocial [1]FOGASA  [2]Desempleo [3]Formaci�n [4]Mutua //el resto creo que no se puede
		String esExtra = (fecha.getMonth() == 5 || fecha.getMonth() == 11) ? "es una EXTRA": "";
		persona.append("/n/tNombre: "+ fila[3] + " " + fila[1] + " "+ fila[2]+ "/n/tIBAN: " + fila[14] +"/n/tCATEGORIA: "
				+ fila[5] + "/n/tBrutoAnual: "+ salarioBruto[0]+" " + esExtra + "/n/tFecha Alta: " + fila[8] + "/n/n/n/n");
		persona.append("/t/t/tN�mina fecha: " + fecha.toString());
		empresa.append("/tEmpresa: " + fila[6] + " /n/tCIF: " + fila[7]);
		String [] foo = {persona.toString(), empresa.toString()};
		String rutaC = "pdf/Nomina" + fila[3] + fila[1] + fila[2]+".pdf";
		this.crearPDF(fecha, rutaC, complementoYAntiguedad, descuentos, salarioMensual, salarioBruto, fila, hoja2, pagosEmpresario);

	}//brutoAnual 
	private Float [] getBrutoAnual(Float salarioMensual, Boolean prorateo, String[] fila, Float[] complementoYAntiguedad) {
		//System.out.println("sdsdasd" + complementoYAntiguedad[0]);
		Float [] brutosAnuales = new Float[3] ;
		float pro=0;
		float complemensual;
		//Tengo brutos anuales con y sin prorrata para los casos normales.
		if(complementoYAntiguedad.length==3 && prorateo==true) { //caso normal

			//System.out.println("2");
			complemensual=(complementoYAntiguedad[0])/14;
			pro=(salarioMensual*2+complemensual*2+(complementoYAntiguedad[1]/14)*2)/12; // en 0 el comple y en 1 los trienios
			brutosAnuales[1]=pro;
			brutosAnuales[2]=complemensual;
			brutosAnuales[0]=salarioMensual*12+pro*12+complemensual*12+complementoYAntiguedad[1];
		}else if(complementoYAntiguedad.length==3 && prorateo==false){ //caso normal sin prorrata
			//System.out.println("3");
			brutosAnuales[1]=0.0f;
			complemensual=(complementoYAntiguedad[0])/14;
			brutosAnuales[2]=complemensual;
			brutosAnuales[0]=salarioMensual*14+complemensual*14+complementoYAntiguedad[1];

		}else { //Caso complicado  //Como funcionan los trienios si hay conflicto.
			//System.out.println("4");

			if(complementoYAntiguedad.length==6 && prorateo==true) {
				//System.out.println("5");

				complemensual=(complementoYAntiguedad[0])/14;
				brutosAnuales[2]=complemensual;
				pro=(salarioMensual*2+complemensual*2+((complementoYAntiguedad[4]/14)*2)/12);
				brutosAnuales[1]=pro;
				brutosAnuales[0]= salarioMensual*12+pro*12+complemensual*12+complementoYAntiguedad[4];
			}else if(complementoYAntiguedad.length==6 && prorateo==false) {
				//System.out.println("6");
				brutosAnuales[1]=0.0f;
				complemensual=(complementoYAntiguedad[0])/14;
				brutosAnuales[2]=complemensual;
				brutosAnuales[0]=salarioMensual*14+complemensual*14+complementoYAntiguedad[4];
				//1: mes a partir del cual empieza el nuevo trienio
				//2: antiguedad mensual trienio viejo (antes de cumplir un trienio) trienio= 3 años
				//3: antiguedad mensual trienio nuevo (despues de cumplir un trienio) trienio= 3 años
				//4: antiguedad anual total contiene los 14 meses
				//si el 1 es mayor que el mes en que estamos haciendo la nomina.
				//si el 1 es menor que ""
			}
		}
		return brutosAnuales;
	}

	private  Float[] getPagosEmpresario(String[] fila, ArrayList<Map> hoja2, Float [] bruto) {//code while dreaming with ONEPLUS 6, prone to errors

		// Pagos empresario

		Float [] resultado = new Float [8];

		float brutoAnual = bruto[0];

		resultado[0]  = brutoAnual/12;//base empresario

		resultado[1] = ((float)hoja2.get(5).get("Contingencias comunes EMPRESARIO")*(brutoAnual/12)) /100;

		resultado[2] = ((float)hoja2.get(5).get("Desempleo EMPRESARIO")*(brutoAnual/12)) /100;

		resultado[3] = ((float)hoja2.get(5).get("Formacion EMPRESARIO")*(brutoAnual/12)) /100;

		resultado[4] = ((float)hoja2.get(5).get("Accidentes trabajo EMPRESARIO")*(brutoAnual/12)) /100;

		resultado[5] = ((float)hoja2.get(5).get("Fogasa EMPRESARIO")*(brutoAnual/12)) /100;

		resultado[6] = resultado[1]+resultado[2]+resultado[3]+resultado[4]+resultado[5];//total empresario

		resultado[7] = resultado[0] + resultado[6];//total empresario trabajador

		return resultado;

	}

	private Float[] getNeto(String[] fila, boolean prorateo, Float[] complementoYAntiguedad,Float[] descuentos) { //anual y mensual
		// TODO Auto-generated method stub

		return null;
	}


	private Float[] getDescuentos(String[] fila, ArrayList<Map> hoja2, Float[] brutoAnual, boolean  prorateo, Float[] complementoYAntiguedad, Float[] salarioMensual, Date fecha) {

		Float sSocial = 0.0f; Float formacion = 0.0f; Float desempleo = 0.0f; Float irpf = 0.0f;
		Float x = (Float)hoja2.get(5).get("Cuota obrera general TRABAJADOR") / (12*100);
		sSocial = (brutoAnual[0] * x);
		Float y = (Float)hoja2.get(5).get("Cuota formación TRABAJADOR") / (12*100);
		formacion = (brutoAnual[0] * y);
		Float z = (Float)hoja2.get(5).get("Cuota desempleo TRABAJADOR") / (12*100);
		desempleo = (brutoAnual[0] * z);

		Set set = hoja2.get(4).keySet();
		Iterator iterador = set.iterator();

		Integer siguiente = (Integer) iterador.next();
		Integer temp = siguiente;
		while (iterador.hasNext() && siguiente < brutoAnual[0]) {
			temp = siguiente;
			siguiente = (Integer) iterador.next();
		}
		//System.out.println("quehaa " + temp);
		int mes = fecha.getMonth()+1;

		float totaldevengos = 0;
		if(complementoYAntiguedad.length!=6) {

			totaldevengos=salarioMensual[0]+brutoAnual[1]+brutoAnual[2]+complementoYAntiguedad[1];

		}else if(complementoYAntiguedad[1]>mes) {

			totaldevengos=salarioMensual[0]+brutoAnual[1]+brutoAnual[2]+complementoYAntiguedad[3];

		}else if(complementoYAntiguedad[1]<=mes) {

			totaldevengos=salarioMensual[0]+brutoAnual[1]+brutoAnual[2]+complementoYAntiguedad[2];

		}
		irpfPer = (Float) hoja2.get(4).get(temp);
		irpf =  (totaldevengos / irpfPer);

		Float[] resultado = {sSocial, formacion, desempleo, irpf, x, y, z, irpfPer};


		return resultado;
	}


	/*
	 * Recibe un array para dejar el pdf bonito con cuadrados
	 * 
	 */
	public void crearPDF(Date fecha,String ruta, Float[] complementoYAntiguedad, Float[] descuentos,Float[] salarioMensual, Float[] salarioAnual, String [] fila, ArrayList<Map> hoja2, Float[] empresario) {
	System.out.println("PDF EMPIEZA");
	int mes = fecha.getMonth()+1;

	float totaldevengos = 0;
	DecimalFormat df = new DecimalFormat();
	df.setMaximumFractionDigits(2);
	if(complementoYAntiguedad.length!=6) {

		totaldevengos=salarioMensual[0]+salarioAnual[1]+salarioAnual[2]+complementoYAntiguedad[1];

	}else if(complementoYAntiguedad[1]>mes) {

		totaldevengos=salarioMensual[0]+salarioAnual[1]+salarioAnual[2]+complementoYAntiguedad[3];

	}else if(complementoYAntiguedad[1]<=mes) {

		totaldevengos=salarioMensual[0]+salarioAnual[1]+salarioAnual[2]+complementoYAntiguedad[2];

	}

	float totaldeducciones = descuentos[0]+descuentos[1]+descuentos[2]+descuentos[3];

	float liquidoPercibir = totaldevengos-totaldeducciones;







	Document document = new Document();
	try {
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ruta));
		document.open();
		//PdfPTable table = new PdfPTable(1);
		Paragraph parrafo = new Paragraph("TecnoProyectSL                            				 IBAN: ES97"+fila[14]);
		Paragraph parrafo2 = new Paragraph("CIF: "+fila[7]+"                            		 Categoría: "+fila[5]);
		Paragraph parrafo3 = new Paragraph("Avenida de la facultad - 6               		Bruto anual: "+salarioAnual[0]);
		Paragraph parrafo4 = new Paragraph("24001 León                               				Fecha de alta: "+fila[8]);
		parrafo.setAlignment(Element.ALIGN_LEFT);
		parrafo2.setAlignment(Element.ALIGN_LEFT);
		parrafo3.setAlignment(Element.ALIGN_LEFT);
		parrafo4.setAlignment(Element.ALIGN_LEFT);
		document.add(parrafo);
		document.add(parrafo2);
		document.add(parrafo3);
		document.add(parrafo4);
		document.add(new Paragraph("\n"));
		Paragraph parrafo10 = new Paragraph("Destinatario: " + fila[3]+" "+fila[1]+" "+fila[2]);
		Paragraph parrafo11 = new Paragraph("DNI: "+fila[0]);
		parrafo10.setAlignment(Element.ALIGN_RIGHT);
		parrafo11.setAlignment(Element.ALIGN_RIGHT);
		document.add(parrafo10);
		document.add(parrafo11);
		Image imagen = Image.getInstance("resources/logo.jpeg");
		imagen.scaleAbsolute(150f,150f);
		imagen.setAbsolutePosition(50, 550);
		document.add(imagen);
		Paragraph nomina = new Paragraph("Nómina: "+ fecha.toString());
		nomina.setAlignment(Element.ALIGN_CENTER);
		document.add(nomina);
		LineSeparator ls = new LineSeparator();
		document.add(new Chunk(ls));
		PdfPTable table = new PdfPTable(5);
		table.getDefaultCell().setBorder(0);

		table.addCell(" ");

		table.addCell("cant.");

		table.addCell("Imp. Unit.(€)");

		table.addCell("Dev.(€)");

		table.addCell("Deducc.(€)");


		LineSeparator lss = new LineSeparator();
		document.add(new Chunk(lss));


		table.getDefaultCell().setBorder(0);

		table.addCell("Salario Base");

		table.addCell("30 ");

		table.addCell(""+df.format(salarioMensual[0]/30));

		table.addCell(""+df.format(salarioMensual[0]));

		table.addCell("");

		table.addCell("Prorrata");
		table.addCell("30");

		table.addCell(""+df.format(salarioAnual[1]/30));

		table.addCell(""+df.format(salarioAnual[1]));

		table.addCell("");
		table.addCell("Complemento");

		table.addCell("30");
		table.addCell(""+df.format(salarioAnual[2]/30));
		table.addCell(""+df.format(salarioAnual[2]));
		table.addCell("");

		if(complementoYAntiguedad.length!=6) {

			table.addCell("Antigüedad");
			table.addCell(""+df.format(complementoYAntiguedad[2]));
			table.addCell(""+df.format((complementoYAntiguedad[1]/14)/30));
			table.addCell(""+df.format(complementoYAntiguedad[1]/14));
			table.addCell("");

		}else {

			if(complementoYAntiguedad[1]>mes) { //si el mes es mayor que el mes en que se hace la nomina
				table.addCell("Antigüedad");
				table.addCell(""+df.format(complementoYAntiguedad[5])); //Esto lo pasa alvaro
				table.addCell(""+df.format(complementoYAntiguedad[3]/30));
				table.addCell(""+df.format(complementoYAntiguedad[3]));
				table.addCell("");
			}else if(complementoYAntiguedad[1]<=mes) {
				table.addCell("Antigüedad");
				table.addCell(""+df.format(complementoYAntiguedad[5])); //Esto lo pasa alvaro
				table.addCell(""+df.format(complementoYAntiguedad[2]/30));
				table.addCell(""+df.format(complementoYAntiguedad[2]));
				table.addCell("");
			}
		}

		table.addCell("Contingencias Generales");
		table.addCell("4.7%");
		table.addCell("de "+df.format(totaldevengos));
		table.addCell("");
		table.addCell(""+df.format(descuentos[0]));

		table.addCell("Desempleo");

		table.addCell("1.6%");

		table.addCell("de "+df.format(totaldevengos));

		table.addCell("");

		table.addCell(""+df.format(descuentos[2]));



		table.addCell("Cuota formación");

		table.addCell("0.1%	");

		table.addCell("de "+df.format(totaldevengos));

		table.addCell("");

		table.addCell(""+df.format(descuentos[1]));



		table.addCell("IRPF");
		
		table.addCell(irpfPer.toString()+"%");

		table.addCell("de "+df.format(totaldevengos));

		table.addCell("");

		table.addCell(""+df.format(descuentos[3]));
		
		LineSeparator lsa = new LineSeparator();
		document.add(new Chunk(lsa));
		table.addCell("Total Deducciones ");

		table.addCell("");

		table.addCell("");

		table.addCell("");

		table.addCell(df.format(liquidoPercibir));



		table.addCell("Total Devengos ");

		table.addCell("");

		table.addCell("");
		table.addCell("");

		table.addCell(df.format(totaldevengos));




		LineSeparator lse = new LineSeparator();
		document.add(new Chunk(lse));

		table.addCell("");

		table.addCell("");

		table.addCell("");

		table.addCell("Líquido a percibir 	");

		table.addCell(df.format(liquidoPercibir));





		PdfPTable table2 = new PdfPTable(5);

		table2.getDefaultCell().setBorder(0);

		//table2.getDefaultCell().set

		table2.addCell("Cálculo empresario:BASE ");

		table2.addCell("");

		table2.addCell("");

		table2.addCell("");

		table2.addCell(""+df.format(empresario[0]));

		table2.addCell("Contingencias comunes 23,60% ");

		table2.addCell("");

		table2.addCell("");

		table2.addCell("");

		table2.addCell(""+df.format(empresario[1]));

		table2.addCell("Desempleo 6.7%");

		table2.addCell("");

		table2.addCell("");

		table2.addCell("");

		table2.addCell(""+df.format(empresario[2]));

		table2.addCell("Formacion 0.6%");
		table2.addCell("");
		table2.addCell("");
		table2.addCell("");
		table2.addCell(""+df.format(empresario[3]));

		table2.addCell("Accidentes de trabajo 1.0%");
		table2.addCell("");
		table2.addCell("");
		table2.addCell("");
		table2.addCell(""+df.format(empresario[4]));

		table2.addCell("FOGASA 0.2%");

		table2.addCell("");
		table2.addCell("");
		table2.addCell("");
		table2.addCell(""+df.format(empresario[5]));

		table2.addCell("TOTAL empresario");

		table2.addCell("");

		table2.addCell("");

		table2.addCell("");

		table2.addCell(""+df.format(empresario[6]));

		table2.addCell("Coste total trabajador");

		table2.addCell("");

		table2.addCell("");

		table2.addCell("");

		table2.addCell(""+df.format(empresario[7]));

		document.add(table);
		document.add(table2);

		document.close();
		writer.close();
		System.out.println("PDF CREADO");
	}

	catch (Exception e) {
		e.printStackTrace();
	}
	}

	private Float[] getComplementos(String[] fila, Date fecha, ArrayList<Map> hoja2) { //si len = 6 conflictivo
		// Bruto anual es = Salario Base Anual + Complementos + lo que se suma en funcion de los trienios
		//Float [] en [0]->complemento y en [1]->antiguedad
		String profesion = fila[5];
		Float [] resultados;
		//calcular complemento
		Float complemento=Float.valueOf((Integer) hoja2.get(1).get(profesion));
		//System.out.println("rrrr " + complemento);
		//calcular antiguedad
		String f = fila[8];
		Date fechaAlta = convertirFecha(f);
		//for (String e: fila)
		//System.out.println(e);
		//String pum = fila[8].substring(0, fila[8].length()-2);
		//System.out.println("temp " + f);
		//System.out.println("fechjaaa "  +  new Date()).toString());
		@SuppressWarnings("deprecation")
		//Date fechaAlta = new Date(Integer.valueOf(f.substring(6, 10)), Integer.valueOf(f.substring(3,5))-1, Integer.valueOf(f.substring(0, 2)));
		int difA, mes;

		mes = fechaAlta.getMonth()+1;
		difA = fecha.getYear()-fechaAlta.getYear();
		float trienios;
		if(difA<3) {
			trienios=(float) 0;
			resultados = new Float[3];
			resultados[0] = complemento;
			resultados[1] = trienios;
			resultados[2] = Float.valueOf(difA/3);
		}else {
			if(difA%3==0) {
				//a�o conflictivo
				//System.out.println("cvhekc " + hoja2);
				Integer tmp = (Integer) hoja2.get(3).get(difA/(3-1))*(mes) + (Integer)hoja2.get(3).get(difA/3)+(12-mes);
				trienios = Float.valueOf((Integer)tmp);
				resultados = new Float[6];
				resultados[0] = complemento;
				resultados[1] = Float.valueOf(mes);//mes a partir del cual se empieza a contar el nuevo trienio
				resultados[2] = Float.valueOf((Integer)hoja2.get(3).get(difA/(3-1)));//valor del trienio antes de empezar a contar el nuevo
				resultados[3] = Float.valueOf((Integer) hoja2.get(3).get(difA/3));//valor del trienio despues de cumplir trienio
				resultados[4] = trienios;//total por trienios al a�o
				resultados[5] = Float.valueOf(difA/3);
				if(mes>=6) {
					//en la primera paga estraordinaria no se incluye el nuevo trienio
					resultados[4] += resultados[2]+resultados[3];
				}else {
					resultados[4]+= resultados[3]+resultados[3];
				}
			}else {
				//a�o no conflictivo
				trienios= Float.valueOf((Integer) hoja2.get(3).get(difA/3)*14);
				resultados = new Float[3];
				resultados[0] = complemento;
				resultados[1] = trienios;
				resultados[2] = Float.valueOf(difA/3);
			}
		}
		return resultados;
	}

	private Date convertirFecha(String f) {
		int year, month = 0, day;
		
		day = Integer.parseInt(f.substring(0, 2));
		String m = f.substring(3,6);
		switch(m) {
		case "ene":
			month = 1;
			break;
		case "feb":
			month = 2;
			break;
		case "mar":
			month = 3;
			break;
		case "abr":
			month = 4;
			break;
		case "may":
			month = 5;
			break;
		case "jun":
			month = 6;
			break;
		case "jul":
			month = 7;
			break;
		case "ago":
			month = 8;
			break;
		case "sep":
			month = 9;
			break;
		case "oct":
			month = 10;
			break;
		case "nov":
			month = 11;
			break;
		case "dic":
			month = 12;
			break;

		}

		year = Integer.parseInt(f.substring(7));
		System.out.println("anio " + year);
		Date resul = new Date(year - 1900, month, day);
		System.out.println("totali " + resul);
		return resul;
		
	}

	public boolean fechaValida(String fechaI, String fechaF){
		//System.out.println("fecha 1 " + fechaI + " fecha2 " + fechaF);
		if(fechaF=="") {
			@SuppressWarnings("deprecation")
			Date i = new Date(Integer.valueOf(fechaI.substring(6, 10)), Integer.valueOf(fechaI.substring(3,5))-1, Integer.valueOf(fechaI.substring(0, 2)));
			System.out.println("acaba");
			return fecha.after(i);
		}else {
			@SuppressWarnings("deprecation")
			Date i = new Date(Integer.valueOf(fechaI.substring(6, 10)), Integer.valueOf(fechaI.substring(3,5))-1, Integer.valueOf(fechaI.substring(0, 2)));
			@SuppressWarnings("deprecation")
			Date f = new Date(Integer.valueOf(fechaF.substring(6, 10)), Integer.valueOf(fechaF.substring(3,5))-1, Integer.valueOf(fechaF.substring(0, 2)));
			System.out.println("acaba");
			return fecha.after(i)&&fecha.before(f);
		}

	}



}
