import java.io.File;
import java.io.IOException;
import java.sql.Date;

public class Manejador {
	public String cargar;
	public String guardar;
	public Date fecha;
	public Manejador (String cargar, String guardar, Date fecha) {
		this.guardar = guardar;
		this.cargar = cargar;
		this.fecha = fecha;
	}
	public void calcular() {
		// TODO Auto-generated method stub
		ReadWriteExcelFile conector = new ReadWriteExcelFile();
		try {
			System.out.println(cargar+ " " +guardar);
			//creo directorio resources si no existe
			File dir = new File(guardar+"/Resources");
			if(!dir.exists()) {
				//creando directorio
				try {
					dir.mkdir();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			String [][] datos = conector.readXLSXFile(cargar);
			OperacionesNif cNIF = new OperacionesNif(guardar);
			//actualizo datos corrigiendo los NIF erroneos y de paso generando el XML correspondiente
			OperacionesNif c = new OperacionesNif(guardar);
			datos = c.corregirNIF(datos);
			OperacionesCC cCC = new OperacionesCC(guardar);
			datos = cCC.corregir(datos);
			conector.writeXLSXFile(guardar,datos);
			
			NominaG nom = new NominaG(guardar, cargar, fecha);
			nom.generar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
