import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public final class WriterXML {
	public static void writeXmlFile(String [][]problematicos) {
		String xmlFileName = "Errores.xml";
		try {

			Element principal = new Element("trabajadores");
			Document doc = new Document(principal);
			//doc.setRootElement(principal);
			for(int i = 1; i<problematicos.length; i++) {
				if(problematicos[i][3]!="") {
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
}
