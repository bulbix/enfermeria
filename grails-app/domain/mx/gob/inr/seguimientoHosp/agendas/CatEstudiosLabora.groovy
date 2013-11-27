package mx.gob.inr.seguimientoHosp.agendas

class CatEstudiosLabora {
	
	
	Short clavepartida
	Short idconceptocobro
	String desestudio	

     static mapping = {
		table 'cat_estudioslabora'
		version false		
		id column:'idestudio'
		cache usage:'read-only'			
	 }
}
