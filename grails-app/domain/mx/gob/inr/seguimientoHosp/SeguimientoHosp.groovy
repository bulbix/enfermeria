package mx.gob.inr.seguimientoHosp

import java.util.Date;

import mx.gob.inr.seguridad.Usuario
import mx.gob.inr.utils.AdmisionHospitalaria;
import mx.gob.inr.utils.Paciente

class SeguimientoHosp {

	AdmisionHospitalaria admision
	Paciente paciente	
	Usuario usuario
	Date fechaAlta = new Date()
	String ipAlta	
	
    static mapping = {
		table 'seg_hosp'		
		version false
		id generator:'identity'
		admision column:'id_admision'
		paciente column:'id_paciente'		
		usuario column:'id_usuario'
		paciente fetch: 'join'
		admision fetch:'join' 
		paciente lazy:false
	 }
}
