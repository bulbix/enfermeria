package mx.gob.inr.utils

enum Turno {
	
	MATUTINO(1),
	VESPERTINO(2),
	NOCTURNO(3)
 
	final int id
	private Turno(int id) { this.id = id }

}
