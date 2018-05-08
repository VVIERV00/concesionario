import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class OperacionesNif {
	private String guardar;
	
	public OperacionesNif(String guardar) {
		this.guardar = guardar;
	}
	public  String[][] corregirNIF(String [][] datos) {
		String [][] datosNuevos = copio(datos);
		//procesar datos
		for (int i = 1; i < datos.length; i++) {
			if(datos[i][0]!="") {
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

				}else if(datos[i][0].charAt(0) != ' '){
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
		}


		writeXmlFile(datosNuevos);
		return datosNuevos;


	}

	public void writeXmlFile(String [][]problematicos) {
		String xmlFileName = guardar+"/Resources/Errores.xml";
		try {

			Element principal = new Element("trabajadores");
			Document doc = new Document(principal);
			//doc.setRootElement(principal);
			for(int i = 1; i<problematicos.length; i++) {
				if(problematicos[i][17]=="Corregido" || (problematicos[i][0]=="" || problematicos[i][0]==" ")&&problematicos[i][3].length()>0) {
					Element persona = new Element("trabajador");
					persona.setAttribute("ID", problematicos[i][0]);
					persona.setAttribute("Nombre", problematicos[i][3]);
					persona.setAttribute("Apellido1", problematicos[i][1]);
					persona.setAttribute("Apellido2", problematicos[i][2]);
					persona.setAttribute("NombreEmpresa", problematicos[i][6]);
					persona.setAttribute("Categoria", problematicos[i][5]);
					doc.getRootElement().addContent(persona);
				}

			}

			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(xmlFileName));

			System.out.println("XML File Saved!");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}

	}

	private static String[][] copio(String[][] datos) {
		String [][] datosNuevos = new String[datos.length][datos[0].length+1];

		for (int i = 0; i < datos.length; i++) {
			for (int j = 0; j < datos[i].length; j++) {
				datosNuevos[i][j] = datos[i][j];
			}
			datosNuevos[i][datosNuevos[0].length-1] = "";
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
}
