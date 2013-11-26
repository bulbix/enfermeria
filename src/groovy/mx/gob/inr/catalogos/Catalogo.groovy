package mx.gob.inr.catalogos

import mx.gob.inr.utils.Paciente

class Catalogo {
	
	Long id
	String descripcion
	
	
	/****
	 * Para el modulo de jefe supervisor
	 */
	List<Paciente> pacientes
	
}
