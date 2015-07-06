import mx.gob.inr.seguridad.*
import mx.gob.inr.catalogos.*

class BootStrap {

    def init = { servletContext ->
		
		Date.metaClass.getAge = { new Date().year - delegate.year }
		
//		def perfilEnfermeria = new Perfil(id:1, authority:'ROLE_ENFERMERIA')
//		perfilEnfermeria.save()
//		
//		def perfilJefeSup = new Perfil(id:2, authority:'ROLE_ENFERMERIA_JEFE_SUPERVISOR')
//		perfilJefeSup.save()
//		
//		//LUIS
//		def usuario = new Usuario(id:1, username:'LUIS',
//		password:'luis123', cedula:'11111', nombre:'Luis')
//		usuario.save()
//		(new UsuarioPerfil(usuario:usuario, perfil:perfilEnfermeria)).save()
//		(new UsuarioPerfil(usuario:usuario, perfil:perfilJefeSup)).save()
//		
//		//DIEGO
//		usuario = new Usuario(id:2, username:'DIEGO',
//		password:'diego123', cedula:'11111', nombre:'Diego')
//		usuario.save()
//		(new UsuarioPerfil(usuario:usuario, perfil:perfilEnfermeria)).save()
//		(new UsuarioPerfil(usuario:usuario, perfil:perfilJefeSup)).save()
//		
//		//HUGO
//		usuario = new Usuario(id:3, username:'HUGO',
//		password:'hugo123', cedula:'11111', nombre:'Hugo')
//		usuario.save()
//		(new UsuarioPerfil(usuario:usuario, perfil:perfilEnfermeria)).save()
//		(new UsuarioPerfil(usuario:usuario, perfil:perfilJefeSup)).save()
//		
//		//GABRIEL
//		usuario = new Usuario(id:4, username:'GABRIEL',
//		password:'gabriel123', cedula:'11111', nombre:'Gabriel')
//		usuario.save()
//		(new UsuarioPerfil(usuario:usuario, perfil:perfilEnfermeria)).save()
//		(new UsuarioPerfil(usuario:usuario, perfil:perfilJefeSup)).save()
	
	}
    def destroy = {
    }
}
