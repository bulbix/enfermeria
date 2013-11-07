package mx.gob.inr.seguimientoHosp

import java.util.Date;

import mx.gob.inr.seguridad.Usuario
import mx.gob.inr.utils.AdmisionHospitalaria;
import mx.gob.inr.utils.Paciente

class SeguimientoHosp {

	AdmisionHospitalaria admision
	Paciente paciente
	Date fechaElaboracion = new Date()
	Usuario usuario
	Date fechaCaptura
	
	
    static mapping = {
		table 'seguimiento_hosp'
		version false		
		id column:'idseguimiento'
		admision column:'idadmision'
		paciente column:'idpaciente'		
		usuario column:'usuario_registra'				
	 }
}
