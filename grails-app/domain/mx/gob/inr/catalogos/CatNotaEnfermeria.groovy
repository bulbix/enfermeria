package mx.gob.inr.catalogos

class CatNotaEnfermeria {
	
	String descripcion

    static constraints = {
    }
	
	
	static mapping = {
		table 'catnotasenfermeria'
		version false
		id column:'idnotatenfermeria'
		descripcion column:'desnotaenfermeria'
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return descripcion;
	}
}
