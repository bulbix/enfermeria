package mx.gob.inr.catalogos

class CatRubroNotaEnfermeria {

   Long id
   String descripcion
   CatSegmentoNotaEnfermeria padre
   Boolean vista
   String tipo

    static constraints = {
    }
	
	
	static mapping = {
		table 'catrubronotaenfermeria'
		version false
		id column:'idrubro'
		descripcion column:'desrubro'
		padre column:'idsegmento'
	}
	
	@Override
		public String toString() {
			// TODO Auto-generated method stub
			return descripcion;
		}
}
