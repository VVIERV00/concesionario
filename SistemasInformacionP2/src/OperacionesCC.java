import java.io.FileWriter;
import java.math.BigInteger;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class OperacionesCC {
	public ArrayList<String> emails;
	public int c;
	public String sustituta;
	private String guardar;

	public OperacionesCC(String guardar) {
		this.emails = new ArrayList<String>();
		this.c = 0;
		this.sustituta = "";
		this.guardar = guardar;
	}
	public String[][] corregir(String[][] datos) {
		for (int i = 1; i < datos.length; i++) {
			//IBAN
			//comporbacion ccc
			String aux = verificarCCC(datos[i][14]);

			//iban
			if(aux.compareTo(datos[i][14])==0) {
				datos[i][16] = calculoIBAN(datos[i][14], datos[i][15]);
			}else {
				datos[i][16] = calculoIBAN(aux, datos[i][15]);
				datos[i][14] = aux;
				datos[i][17] = "erronea";
			}
			//correos
			datos[i][4] = correo(datos[i][3], datos[i][1], datos[i][2]);
		}
		xml(datos);
		return datos;
	}

	public void xml(String[][] datos) {
		String xmlFileName = guardar+"/Resources/cuentasErroneas.xml";
		try {
			Element principal = new Element("trabajadores");
			Document doc = new Document(principal);
			for(int i = 1; i<datos.length; i++) {
				if(datos[i][17]=="erronea") {
					Element persona = new Element("trabajador");
					persona.setAttribute("ID", datos[i][0]);
					persona.setAttribute("Nombre", datos[i][3]);
					persona.setAttribute("Apellido1", datos[i][1]);
					persona.setAttribute("Apellido2", datos[i][2]);
					persona.setAttribute("NombreEmpresa", datos[i][6]);
					persona.setAttribute("Categoria", datos[i][5]);
					doc.getRootElement().addContent(persona);
				}

			}

			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(xmlFileName));

			System.out.println("XML File Saved!");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	public String calculoIBAN(String dato, String pais) {
		if((dato!="" && pais!="") && (dato != null && pais!=null)) {
			String iban = new String(dato);
			//coloco el prefijo de pais + 00 al final del CCC	
			iban = iban.toString() + pais + "00";

			iban = aNum(iban);
			//calculo digito de control del iban
			BigInteger numero = new BigInteger(iban);
			//System.out.println(numero);
			int resto = numero.mod(BigInteger.valueOf(97)).intValue();
			int diferencia = 98-resto;

			//juntar todo

			//paso a string el prefijo del pais
			String digitoControl = new String("");
			if(resto<10) {
				digitoControl = "0" + String.valueOf(diferencia);
			}else {
				digitoControl = String.valueOf(diferencia);
			}
			iban = pais + digitoControl + dato;
			//System.out.println(iban);
			comprobacionIban(iban);
			return iban.substring(0, 4);
		}else { return "";}

	}

	public double comprobacionIban(String dato) {
		String dig = new String("");
		//paso los cuatro primeros digitos al final
		dig  = dig + dato.charAt(0)+dato.charAt(1)+dato.charAt(2)+dato.charAt(3);
		String com = new String("");
		for (int i = 4; i < dato.length(); i++) {
			com = com + dato.charAt(i);
		}
		com = com + dig;
		com = aNum(com);
		BigInteger numero = new BigInteger(com);
		double res = numero.mod(BigInteger.valueOf(97)).doubleValue();
		return res;
	}


	public static String verificarCCC(String cccOriginal) {
		if(cccOriginal!="" && cccOriginal!=null&&cccOriginal.length()>=20) {
			//System.out.println(cccOriginal);
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
				//System.out.println(i +": A: " + resultadosA + " " + entidadOficina.substring(i, i+1) + " B: "+ resultadosB + " " + cuenta.substring(i, i+1));
			}
			int modA = 11 - (resultadosA%11);
			int modB = 11 - (resultadosB%11);
			//System.out.println("entidadOficina " + entidadOficina + " cuenta " + cuenta);
			modA = ((modA == 10) ? 0 :(modA == 11 ? 1 : modA));
			modB = ((modB == 10) ? 0 :(modB == 11 ? 1 : modB));
			//System.out.println("ORIGINAL "+ verificadorA + " " + verificadorB + " a: " +modA + " b: " + modB);

			return cccOriginal.substring(0, 8)+String.valueOf(modA)+String.valueOf(modB)+cccOriginal.substring(10,20);
		}else {return "";}
	}

	public String correo(String nombre, String apellido1, String apellido2){
		nombre=nombre.toLowerCase();
		apellido1=apellido1.toLowerCase();
		apellido2=apellido2.toLowerCase();
		String email = "";
		if(nombre.length()>=2) {
			//sacamos las 3 iniciales del nombre
			for (int i = 0; i < 3; i++) {
				email=email+nombre.charAt(i);

			}
		}
		if(apellido1.length()>=1) {
			//Sacamos las 2 inciiales del apoellido1
			for (int i = 0; i < 2; i++) {
				email=email+apellido1.charAt(i);

			}
		}

		if(apellido2.length()>=1) {
			//sacamos las 2 iniciales del apellido2
			for (int i = 0; i < 2; i++) {
				email=email+apellido2.charAt(i);
			}
		}

		if(email.length()>0) {
			//Numero del email
			int contador = 0;
			for (int i = 0; i < emails.size(); i++) {
				if(email.compareTo((emails.get(i)))==0) {
					contador++;
				}
			}
			emails.add(email);
			if(contador<10) {
				email=email+"0"+contador;
			}else {
				email=email+contador;
			}

			//A�adimos extension

			email=email+"@nombreempresa.es";

		}
		return email;
	}


	public static int operacionFactores(int num, int i) {
		return (int) (Math.pow(2, i) % 11) * num;
	}



	public static String aNum(String dato) {
		String ibanN = new String("");
		for(int i=0;i<dato.length();i++) {

			if(dato.charAt(i)=='A') {
				ibanN = ibanN+"10";
			}else if(dato.charAt(i)=='B') {
				ibanN = ibanN+"11";
			}else if(dato.charAt(i)=='C') {
				ibanN = ibanN+"12";
			}else if(dato.charAt(i)=='D') {
				ibanN = ibanN+"13";
			}else if(dato.charAt(i)=='E') {
				ibanN = ibanN+"14";
			}else if(dato.charAt(i)=='F') {
				ibanN = ibanN+"15";
			}else if(dato.charAt(i)=='G') {
				ibanN = ibanN+"16";
			}else if(dato.charAt(i)=='H') {
				ibanN = ibanN+"17";
			}else if(dato.charAt(i)=='I') {
				ibanN = ibanN+"18";
			}else if(dato.charAt(i)=='J') {
				ibanN = ibanN+"19";
			}else if(dato.charAt(i)=='K') {
				ibanN = ibanN+"20";
			}else if(dato.charAt(i)=='L') {
				ibanN = ibanN+"21";
			}else if(dato.charAt(i)=='M') {
				ibanN = ibanN+"22";
			}else if(dato.charAt(i)=='N') {
				ibanN = ibanN+"23";
			}else if(dato.charAt(i)=='O') {
				ibanN = ibanN+"24";
			}else if(dato.charAt(i)=='P') {
				ibanN = ibanN+"25";
			}else if(dato.charAt(i)=='Q') {
				ibanN = ibanN+"26";
			}else if(dato.charAt(i)=='R') {
				ibanN = ibanN+"27";
			}else if(dato.charAt(i)=='S') {
				ibanN = ibanN+"28";
			}else if(dato.charAt(i)=='T') {
				ibanN = ibanN+"29";
			}else if(dato.charAt(i)=='U') {
				ibanN = ibanN+"30";
			}else if(dato.charAt(i)=='V') {
				ibanN = ibanN+"31";
			}else if(dato.charAt(i)=='W') {
				ibanN = ibanN+"32";
			}else if(dato.charAt(i)=='X') {
				ibanN = ibanN+"33";
			}else if(dato.charAt(i)=='Y') {
				ibanN = ibanN+"34";
			}else if(dato.charAt(i)=='Z') {
				ibanN = ibanN+"35";
			}else {
				ibanN = ibanN + dato.charAt(i);
			}
		}
		return ibanN;
	}


}
