package mx.gob.inr.utils

import mx.gob.inr.hojaRegistroClinico.RegistroHojaEnfermeria;

class SignoVital implements Comparable<SignoVital> {
	
	Integer hora;
	RegistroHojaEnfermeria temperatura = new RegistroHojaEnfermeria()
	RegistroHojaEnfermeria cardiaca = new RegistroHojaEnfermeria()
	RegistroHojaEnfermeria diastolica = new RegistroHojaEnfermeria()
	RegistroHojaEnfermeria sistolica = new RegistroHojaEnfermeria()
	RegistroHojaEnfermeria respiracion = new RegistroHojaEnfermeria()
	RegistroHojaEnfermeria gabinete = new RegistroHojaEnfermeria()

	@Override
	public int compareTo(SignoVital o) {
		return hora.compareTo(o.hora);
	}
	
	

}
