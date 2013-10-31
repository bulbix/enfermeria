package mx.gob.inr.seguimientoHosp

class Operanotaquirposopera implements Serializable {
	
	Notaquirposopera nota
	Date fechaelaboracion
	
	
	
	

     static mapping = {
		table 'operanotaquirposopera'
		version false
		
		id column:'idnota'
		paciente column:'idpaciente'		
	}
}
