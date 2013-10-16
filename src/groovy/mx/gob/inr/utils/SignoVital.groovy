package mx.gob.inr.utils

import mx.gob.inr.hojaRegistroClinico.RegistroHojaEnfermeria;

class SignoVital implements Comparable<SignoVital> {
	
	Integer hora;
	RegistroHojaEnfermeria temperatura
	RegistroHojaEnfermeria cardiaca 
	RegistroHojaEnfermeria diastolica
	RegistroHojaEnfermeria sistolica
	RegistroHojaEnfermeria respiracion
	RegistroHojaEnfermeria gabinete

	@Override
	public int compareTo(SignoVital o) {
		return hora.compareTo(o.hora);
	}
	
	

}
