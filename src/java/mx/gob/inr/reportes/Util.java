package mx.gob.inr.reportes;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


public class Util {
	
	
	public static String getDateString(Date fecha) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(fecha);
	}
	
	
	public static boolean isFloatNumber(String num) {
		try {
			Float.parseFloat(num);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public static String getCadenaAlfanumAleatoria (int longitud) {
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while ( i < longitud){
		char c = (char)r.nextInt(255);
		if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
		cadenaAleatoria += c;
		i ++;
		}
		}
		return cadenaAleatoria;
	}
	
	public static String getFechaActual(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern, new Locale(
				"es", "MX"));
		return "" + format.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * Obtiene la Edad dado una fecha de Nacimiento
	 * 
	 * @param fechaNacimiento
	 * @return
	 */

	public static int getEdad(Date fechaNacimiento) {
				
		/*DateTime now = new DateTime();
		DateTime birthdate = new DateTime(fechaNacimiento);
		Years age = Years.yearsBetween(birthdate, now);
		return age.getYears()+"";*/
		return new Date().getYear() - fechaNacimiento.getYear();
	}
	
	
	public static void mostrarReporte(HttpServletResponse response, InputStream datos,
			String contentType, String nombre) throws Exception {
		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment;filename="
				+ nombre);
		ServletOutputStream os = response.getOutputStream(); //
		
		int readBytes=0;
		int size=0;
		byte[] buffer=new byte[2048];		
		while(  (readBytes=datos.read(buffer)) >=0){
			os.write(buffer, 0, readBytes);
			size+=readBytes;
		}
		response.setContentLength(size);
		os.flush();
		os.close();
		response.flushBuffer();
	}

}
