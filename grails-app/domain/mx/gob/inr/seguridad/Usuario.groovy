package mx.gob.inr.seguridad

class Usuario {

	Long id
	transient springSecurityService

	String username
	String password
	String cedula
	
	boolean enabled = true
	boolean accountExpired = false
	boolean accountLocked = false
	boolean passwordExpired = false

	String nombre	
	
	static constraints = {		
		username blank: false, unique: true
		password blank: false
	}

	static mapping = {
		id column:'idusuario'		
		username column:'rfc'
		password column: '`passwordc`'
		//enabled column:'enabled`'
		version false
	}

	Set<Perfil> getAuthorities() {
		UsuarioPerfil.findAllByUsuario(this).collect { it.perfil } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	String toString(){
		return nombre
	}
	
}
