package mx.gob.inr.utils

class Liquido {
	
	String descripcion
	String fxpM = "0"
	String fxpV = "0"
	String fxpN = "0"
	
	
	Float totalMatutino;
	Float totalVespertino;
	Float totalNocturno;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		
		Liquido other = (Liquido) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		return true;
	}
	
	

}
