import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Principal{

	public static void main(String[] args) {
		System.out.println(verificarCCC("20966058141234500000"));


		return ;
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
	public static void generarNomina(String [] fila, Date fecha, ArrayList<Map> hoja2) { //Wrote while playing romeo santos, code prone to errors.
		StringBuilder persona = new StringBuilder();
		StringBuilder empresa = new StringBuilder();
		
		boolean prorateo = (fila[13] == "SI");
		Float[] complementoYAntiguedad = getComplementos(fila, fecha, hoja2); //[0]:Complemento [1]:Antiguedad
		Float[] descuentos = getDescuentos(fila, hoja2); //[0]:SSocial [1]:Formacion [2]: Desempleo [3]: IRPF
		Float[] salarioMensual = getBaseYBrutoMensual(fila, prorateo, complementoYAntiguedad, descuentos); //[0]:base [1]:bruto
		Float[] salarioAnual = getAnual(salarioMensual, prorateo, fila); //multiplica por 12, 14 o menos si no trabajo todo el año. Fila para sacar la fechadeEntradaYAlta
		
		Float[] pagosEmpresario = getPagosEmpresario(fila, hoja2); //[0]SSocial [1]FOGASA  [2]Desempleo [3]Formación [4]Mutua //el resto creo que no se puede
		String esExtra = (fecha.getMonth() == 5 || fecha.getMonth() == 11) ? "es una EXTRA": "";
		persona.append("/n/tNombre: "+ fila[3] + " " + fila[1] + " "+ fila[2]+ "/n/tIBAN: " + fila[14] +"/n/tCATEGORIA: "
				+ fila[5] + "/n/tBrutoAnual: "+ salarioAnual[1]+" " + esExtra + "/n/tFecha Alta: " + fila[8] + "/n/n/n/n");
		persona.append("/t/t/tNómina fecha: " + fecha.toString());
		empresa.append("/tEmpresa: " + fila[6] + " /n/tCIF: " + fila[7]);
		
		String [] foo = {persona.toString(), empresa.toString()};
		Principal.crearPDF(foo);
		
	}
	private static Float[] getPagosEmpresario(String[] fila, ArrayList<Map> hoja2) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Float[] getAnual(Float[] salarioMensual, boolean prorateo, String[] fila) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Float[] getBaseYBrutoMensual(String[] fila, boolean prorateo, Float[] complementoYAntiguedad,
			Float[] descuentos) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Float[] getDescuentos(String[] fila, ArrayList<Map> hoja2) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Float[] getComplementos(String[] fila, Date fecha, ArrayList<Map> hoja2) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Recibe un array para dejar el pdf bonito con cuadrados
	 * 
	 */
	public static void crearPDF(String[] nomina) {  
		
	}

	public static void corregirNIF() {
		ReadWriteExcelFile con = new ReadWriteExcelFile();
		try {
			String [][] datos = con.readXLSXFile();
			String [][] datosNuevos = copio(datos);

			//procesar datos
			for (int i = 1; i < datos.length; i++) {
				if(datos[i][0].charAt(0)=='X' || datos[i][0].charAt(0)=='Y' || datos[i][0].charAt(0)=='Z') {
					//extranjeros
					StringBuilder numero = new StringBuilder();
					StringBuilder res = new StringBuilder();
					//saco el correspondiente a la letra
					if(datos[i][0].charAt(0)=='X') {
						numero.append("0");
						res.append("X");
					}else if(datos[i][0].charAt(0)=='Y') {
						numero.append("1");
						res.append("Y");
					}else if(datos[i][0].charAt(0)=='Z') {
						numero.append("2");
						res.append("Z");
					}

					//paso el resto del numero
					for (int j = 1; j < datos[i][0].length()-1; j++) {
						numero.append(datos[i][0].charAt(j));
						res.append(datos[i][0].charAt(j));
					}

					String letraInicial = new String();
					letraInicial = String.valueOf(datos[i][0].charAt(0));

					datosNuevos[i][datos[0].length]=" ";
					try {
						int resto = Integer.valueOf(numero.toString())%23;
						String letraCorrecta = letraCorrespondiente(resto);

						if(letraInicial.compareTo(letraCorrecta)!=0) {
							//esta mal, hacemos algo
							res.append(letraCorrecta);
							datosNuevos[i][0] = res.toString();
							datosNuevos[i][datos[0].length]="Corregido";
						}
					}catch (Exception e) {
						// TODO: handle exception
					}

				}else if(datos[i][0].charAt(0) != ' ' && datos[i][0]!=" "){
					//nacionales
					StringBuilder b = new StringBuilder();
					StringBuilder res = new StringBuilder();

					String letraInicial = new String();
					letraInicial = String.valueOf(datos[i][0].charAt(datos[i][0].length()-1));

					for (int j = 0; j < datos[i][0].length()-1; j++) {
						b.append(datos[i][0].charAt(j));
						res.append(datos[i][0].charAt(j));
					}

					datosNuevos[i][datos[0].length]=" ";
					try {
						int num = Integer.valueOf(b.toString());
						int resto = num%23;

						String letraCorrecta = letraCorrespondiente(resto);
						if(letraInicial.compareTo(letraCorrecta)!=0) {
							//esta mal, hacemos algo
							res.append(letraCorrecta);
							datosNuevos[i][0] = res.toString(); 
							datosNuevos[i][datos[0].length]="Corregido";
						}
					}
					catch (Exception e) {
						// TODO: handle exception
					}

				}
			}

			//escribo de nuevo en el excel
			con.writeXLSXFile(datosNuevos);
			buscoDuplicados(datosNuevos);
		} catch (IOException e) {
		}


	}

	public static void buscoDuplicados(String [][] datos) {
		String [][] duplicados = new String[datos.length][datos[0].length];
		for (int i = 0; i < duplicados.length; i++) {
			for (int j = 0; j < duplicados[0].length; j++) {
				duplicados[i][j]="";
			}
		}
		int contador = 0;
		for (int i = 0; i < datos.length; i++) {
			if(datos[i][0]==" ") {
				System.out.println("entro");
				for (int j = 0; j < datos[i].length; j++) {
					duplicados[contador][j] = datos[i][j];
				}
				contador++;
			}else {
				//busco si esta duplicado
				for (int j = 0; j < datos.length; j++) {
					//System.out.println(datos[i][0]+"   "+datos[j][0]);
					if(datos[i][0].compareTo(datos[j][0])==0 && i!=j) {
						for (int j2 = 0; j2 < datos[i].length; j2++) {
							duplicados[contador][j2] = datos[i][j2];
						}
						contador++;
					}
				}
			}
		}
		WriterXML.writeXmlFile(duplicados);
	}


public static String generadorIBAN(String cuenta, String pais) {
	String resultado = "";
	
	
	
	return resultado;
}

public static String verificadorCuenta(String cuenta) {
	String resultado = "";
	StringBuilder primeraParte = new StringBuilder(cuenta.substring(0, 7));
	
	
	return resultado;
}
public static String verificadorIBAN(String cuenta) {
	String resultado = "";
	
	
	
	return resultado;
}
private static String[][] copio(String[][] datos) {
	String [][] datosNuevos = new String[datos.length][datos[0].length+1];

	for (int i = 0; i < datos.length; i++) {
		for (int j = 0; j < datos[i].length; j++) {
			datosNuevos[i][j] = datos[i][j];
		}
	}
	return datosNuevos;
}

public static String letraCorrespondiente(int resto) {
	String res;
	switch (resto) {

	case 0:
		res = "T";
		break;
	case 1:
		res = "R";
		break;
	case 2:
		res = "W";
		break;
	case 3:
		res = "A";
		break;
	case 4:
		res = "G";
		break;
	case 5:
		res = "M";
		break;
	case 6:
		res = "Y";
		break;
	case 7:
		res = "F";
		break;
	case 8:
		res = "P";
		break;
	case 9:
		res = "D";
		break;
	case 10:
		res = "X";
		break;
	case 11:
		res = "B";
		break;
	case 12:
		res = "N";
		break;
	case 13:
		res = "J";
		break;
	case 14:
		res = "Z";
		break;
	case 15:
		res = "S";
		break;
	case 16:
		res = "Q";
		break;
	case 17:
		res = "V";
		break;
	case 18:
		res = "H";
		break;
	case 19:
		res = "L";
		break;
	case 20:
		res = "C";
		break;
	case 21:
		res = "K";
		break;
	case 22:
		res = "E";
		break;

	default:
		res = "";
		break;
	}
	return res;
}


	public static boolean verificarCCC(String cccOriginal) {
		StringBuilder ccc = new StringBuilder(cccOriginal);
		StringBuilder entidadOficina = new StringBuilder("00" + ccc.substring(0, 8));
		StringBuilder cuenta = new StringBuilder(ccc.substring(10, 20));
		int verificadorA = Integer.valueOf(ccc.substring(8,9));
		int verificadorB = Integer.valueOf(ccc.substring(9,10));
		int resultadosA = 0;
		int resultadosB = 0;
		for (int i = 0; i<10; i++) {
			resultadosA += operacionFactores(Integer.valueOf(entidadOficina.substring(i, i+1) ) , i);
			resultadosB += operacionFactores(Integer.valueOf(cuenta.substring(i, i+1) ) ,i );
			System.out.println(i +": A: " + resultadosA + " " + entidadOficina.substring(i, i+1) + " B: "+ resultadosB + " " + cuenta.substring(i, i+1));
		}
		int modA = 11 - (resultadosA%11);
		int modB = 11 - (resultadosB%11);
		System.out.println("entidadOficina " + entidadOficina + " cuenta " + cuenta);
		modA = ((modA == 10) ? 0 :(modA == 11 ? 1 : modA));
		modB = ((modB == 10) ? 0 :(modB == 11 ? 1 : modB));
		System.out.println("ORIGINAL "+ verificadorA + " " + verificadorB + " a: " +modA + " b: " + modB);
		if (modA == verificadorA && modB == verificadorB) {
			return true;
		}else {
			return false;
		}
	}
	
	public static int operacionFactores(int num, int i) {
		return (int) (Math.pow(2, i) % 11) * num;
	}

}


















