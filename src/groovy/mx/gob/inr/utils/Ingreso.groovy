package mx.gob.inr.utils

class Ingreso {
	
	String descripcion
	String fxpM = "0"
	String fxpV = "0"
	String fxpN = "0"
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
		
		Ingreso other = (Ingreso) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		return true;
	}
	
	

}
