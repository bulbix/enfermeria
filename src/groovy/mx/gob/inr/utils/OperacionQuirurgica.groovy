package mx.gob.inr.utils

enum OperacionQuirurgica {
	
	PLANEADA(1),
	PRACTICADA(2)
	
	final int id
	private OperacionQuirurgica(int id) { this.id = id }

}
