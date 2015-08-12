package mx.gob.inr.catalogos

class CatSegmentoNotaEnfermeria {

   String descripcion
   CatNotaEnfermeria padre

    static constraints = {
    }
	
	
	static mapping = {
		table 'catsegmentonotaenfermeria'
		version false
		id column:'idsegmento'
		descripcion column:'dessegmento'
		padre column:'idnotatenfermeria'
	}
	
	@Override
		public String toString() {
			// TODO Auto-generated method stub
			return descripcion;
		}
}
