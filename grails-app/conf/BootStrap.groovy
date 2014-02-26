import mx.gob.inr.seguridad.*

class BootStrap {

    def init = { servletContext ->
		
		Date.metaClass.getAge = { new Date().year - delegate.year }
		
		
		def usuario = new Usuario(id:1, username:'BULBO', 
			password:'garbage', cedula:'11111', nombre:'Luis Prado')
		usuario.save()		
		def perfil = new Perfil(id:1, authority:'ROLE_ENFERMERIA')
		perfil.save()
		(new UsuarioPerfil(usuario:usuario, perfil:perfil)).save()
		
		
    }
    def destroy = {
    }
}
