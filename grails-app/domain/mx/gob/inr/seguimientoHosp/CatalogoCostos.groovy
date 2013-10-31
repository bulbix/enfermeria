package mx.gob.inr.seguimientoHosp

class CatalogoCostos implements Serializable {
	
	Short identificador
	Short idconceptocobro
	Short clavepartida
	Short idnivelcobro
	String nombrepartida
	Float precio
	Date fechainivig
	Date fechafinvig
	
    static mapping = {
		table 'catalogocostos'
		version false		
		id composite: ['identificador',
		'idconceptocobro','clavepartida','idnivelcobro','fechainivig','fechafinvig']
		cache usage:'read-only'			
	 }
}
