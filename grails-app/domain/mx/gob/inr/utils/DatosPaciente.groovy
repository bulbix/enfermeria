package mx.gob.inr.utils


class DatosPaciente implements Serializable {
	
	Paciente paciente
	Integer iddatospaciente
	Religion religion	
	
	static constraints = {
	}
	
	
	static mapping = {
		version false		
		table 'datospaciente'
		paciente column:'idpaciente'
		religion column:'idreligion'				
		id composite: ['paciente','iddatospaciente']
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return iddatospaciente;
	}
	
}
