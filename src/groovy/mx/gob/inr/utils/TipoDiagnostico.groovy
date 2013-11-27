package mx.gob.inr.utils

enum TipoDiagnostico {
	
	PRINCIPAL('s'),
	SECUNDARIO('n')
	
	final String id
	private TipoDiagnostico(String id) { this.id = id }

}
