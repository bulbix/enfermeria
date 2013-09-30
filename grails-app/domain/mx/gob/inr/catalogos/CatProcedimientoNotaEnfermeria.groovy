package mx.gob.inr.catalogos

class CatProcedimientoNotaEnfermeria {

   String descripcion
   CatRubroNotaEnfermeria padre
   String estatus
   Boolean vista

    static constraints = {
    }
	
	
	static mapping = {
		table 'catprocedimientosnotaenfermeria'
		version false
		id column:'idprocedimiento'
		descripcion column:'desprocedimiento'
		padre column:'idrubro'
	}
}
