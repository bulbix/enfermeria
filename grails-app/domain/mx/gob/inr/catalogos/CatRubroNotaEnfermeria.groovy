package mx.gob.inr.catalogos

class CatRubroNotaEnfermeria {

   String descripcion
   CatSegmentoNotaEnfermeria padre

    static constraints = {
    }
	
	
	static mapping = {
		table 'catrubronotaenfermeria'
		version false
		id column:'idrubro'
		descripcion column:'desrubro'
		padre column:'idsegmento'
	}
}
