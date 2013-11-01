package mx.gob.inr.utils

import mx.gob.inr.catalogos.*
import mx.gob.inr.hojaRegistroClinico.*
import mx.gob.inr.seguridad.*



class UtilService {
	
    def consultarPisos() {
		
		def pisos = []
				
		AdmisionHospitalaria.createCriteria().list(){
			
			projections{
				cama{
					area{
						distinct("clavearea")
						property("id")
						
						not {'in'("id",[(long)898,(long)876,(long)720,(long)722])}
						
						order("clavearea")
					}
				}
			}
			
			eq("estadoadmision","I")		
				
		}.each{
			pisos << new Catalogo(id:it[1],descripcion:it[0])		
		}
		
		pisos

    }
	
	def consultarServicios(Long idArea){
		
		def servicios = []
		
		AdmisionHospitalaria.createCriteria().list(){
			
			projections{
				servicio{
					distinct("descripcion")
					property("id")
					order("descripcion")
				}
			}
				
			cama{
				area{
					eq("id",idArea)
				}					
			}		
			
			eq("estadoadmision","I")
				
		}.each {
			servicios << new Catalogo(id:it[1],descripcion:it[0])
		}
		
		
		servicios
		
	}
		
	def consultarPacientes(String term, Long idArea = null, Long idServicio = null, boolean historico = false){
		   
	   def aprox = "%" + term.toUpperCase() + "%"
	   
	   def paclist = null
	   
	   if(historico){
		   
		   def query = {
			   
			   projections{
				   
				   paciente{
					   
					   distinct("id")
					   property("numeroregistro")
					   property("paterno")
					   property("materno")
					   property("nombre")
					   
										  
					   or{
						   like("numeroregistro", 'N-' + term + "%")
						   sqlRestriction("upper(paterno || ' ' || materno || ' ' || nombre) like '$aprox'")
					   }
					   
				   }
			   
				   admision{
					   cama{
						   
						   property("numerocama")
						   
						   if(idArea != -1){
							   eq("area.id",idArea)
						   }
						   
						   order("numerocama")
						   
					   }
					   
					   if(idServicio != -1){
						   eq("servicio.id",idServicio)
					   }
					   
					   property("estadoadmision")
					   eq("estadoadmision","E")
					   
				   }		
			   
			   }
			   
			  
			   
		   }
		   
		   paclist = HojaRegistroEnfermeria.createCriteria().list(query)
		   
	   }
	   else{
		   
		   def query = {
			   
			   projections{
			   
				   distinct("id")
				   property("numeroregistro")
				   property("paterno")
				   property("materno")
				   property("nombre")
				   
				   or{
					   like("numeroregistro", 'N-' + term + "%")
					   sqlRestriction("upper(paterno || ' ' || materno || ' ' || nombre) like '$aprox'")
				   }
				   
				   admisiones{
					   cama{
						   
						   property("numerocama")
						   
						   if(idArea != -1){
							   eq("area.id",idArea)
						   }
						   
						   order("numerocama")					   
					   }
					   
					   if(idServicio != -1){
						   eq("servicio.id",idServicio)
					   }
					   
					   property("estadoadmision")
					   eq("estadoadmision","I")
				   }
			   
			   }
			   
		   }
		   
		   paclist = Paciente.createCriteria().list(query)
	   } 

	   def results = paclist?.collect {
		   def display = String.format("(%s) %s %s %s %s %s",it[1]/*[0..12]*/ ,it[2], it[3],it[4],it[5],it[6])
		   [id:it[0], value:display, label:display]
	   }

	   return results
	   
   }
	
	def consultarDatosPaciente(Long idPaciente){
		
		
		def maxFechaIngreso = AdmisionHospitalaria.createCriteria().get{
			projections{
				max("fechaIngreso")
			}
						
			eq("paciente.id",idPaciente)
			
		}		
		
		def admision  = AdmisionHospitalaria.createCriteria().get{			
			paciente {
				eq("id",idPaciente)
			}
			
			cama{
				
			}
						
			eq("fechaIngreso",maxFechaIngreso)
		}
		
		return admision		
	}

	/***
	 * Regresa el peso y la talla de la NotaHospitalizacionIngreso	 * 
	 * @param idPaciente
	 * @return primera posicion el peso segunda posicion la talla
	 */
	def consultarPesoTalla(Long idPaciente){
		
		def result = [0.0,0.0]
		
		def notaHosp = NotaHospIngresoHosp.createCriteria().get{
			eq("paciente.id",idPaciente)
			maxResults(1)
			order("id", "desc")
		}
		
		if(notaHosp){
			result=[notaHosp.peso,notaHosp.talla]
		}		
		
		result		
	}	

	/****
	 * Guardar registro con valor
	 * @param hora
	 * @param idHoja
	 * @param idProcedimiento
	 * @param idUsuario
	 * @param valor
	 * @return
	 */
	def guardarRegistroEnfermeriaConValor(Integer hora, Long idHoja,Long idProcedimiento,Integer idUsuario,String valor,Boolean modificarHora = false, String propiedadValor = "otro"){
		
		def registro = RegistroHojaEnfermeria.createCriteria().get{
			eq("procedimiento.id",idProcedimiento as long)
			
			if(!modificarHora){
				eq("horaregistrodiagva",hora)
			}
			
			eq("hoja.id",idHoja)
		}		
		
		if(registro){
			
			if(valor){
				
				if(valor=="00000"){//Una condicion para eliminar registros sin ningun check
					registro.delete()
					return
				}
								
				registro."$propiedadValor" = valor
				
				if(modificarHora){
					registro.horaregistrodiagva = hora
				}
				
				registro.save([validate:false])
			}
			else{
				registro.delete()
			}
		}
		else{
			if(valor){
				registro = new RegistroHojaEnfermeria()
				registro.horaregistrodiagva = hora
				registro.hoja = HojaRegistroEnfermeria.get(idHoja)
				registro.procedimiento = CatProcedimientoNotaEnfermeria.get(idProcedimiento)
				registro.usuario =Usuario.get(idUsuario)
				registro."$propiedadValor" = valor
				registro.save([validate:false])
			}
		}
			
	}

	/***
	 * Guarda registros con valor o sin valor y no borra
	 * @param hora
	 * @param idHoja
	 * @param idProcedimiento
	 * @param idUsuario
	 * @param valor
	 * @return
	 */
	def guardarRegistroEnfermeria(Integer hora, Long idHoja,Long idProcedimiento,Integer idUsuario,String valor,Boolean modificarHora = false){
		
		def registro = RegistroHojaEnfermeria.createCriteria().get{
			eq("procedimiento.id",idProcedimiento as long)
			
			if(!modificarHora){
				eq("horaregistrodiagva",hora)
			}
			
			eq("hoja.id",idHoja)
		}
		
		if(registro){
			registro.otro = valor
				
			if(modificarHora){
				registro.horaregistrodiagva = hora
			}
				
			registro.save([validate:false])
		}
		else{
			registro = new RegistroHojaEnfermeria()
			registro.horaregistrodiagva = hora
			registro.hoja = HojaRegistroEnfermeria.get(idHoja)
			registro.procedimiento = CatProcedimientoNotaEnfermeria.get(idProcedimiento)
			registro.usuario =Usuario.get(idUsuario)
			registro.otro = valor
			registro.save([validate:false])
		}
		
	}
	
	/***
	 * Guardar registros sin valor
	 * @param hora
	 * @param idHoja
	 * @param idProcedimiento
	 * @param idUsuario
	 * @return
	 */
	def guardarRegistroEnfermeriaSinValor(Integer hora, Long idHoja,Long idProcedimiento,Integer idUsuario){
				
		def registro = new RegistroHojaEnfermeria()
		registro.horaregistrodiagva = hora
		registro.hoja = HojaRegistroEnfermeria.get(idHoja)
		registro.procedimiento = CatProcedimientoNotaEnfermeria.get(idProcedimiento)
		registro.usuario =Usuario.get(idUsuario)
		registro.otro = ""
		registro.save([validate:false])
	}
	
	
	def consultarCatalogoRubro(Long idSegmento){
		def rubros = CatRubroNotaEnfermeria.createCriteria().list{
			eq("padre.id",idSegmento)
			order("descripcion")
		}
		
		rubros
	}
	
	
	def guardarCheckTabla(Long idHoja,Long idProcedimiento,Integer idUsuario,Turno turno, Boolean valor){
		
		def result = RegistroHojaEnfermeria.createCriteria().get{
			
			projections{
				property("registrodiagvalora")
			}
			
			eq("procedimiento.id",idProcedimiento as long)
			eq("hoja.id",idHoja)
		}
		
		if(!result)
			result = new StringBuffer("00000")
		else
			result = new StringBuffer(result)
			
			 
		result.setCharAt(turno.id-1, valor==true?'1' as char:'0' as char)
		
		guardarRegistroEnfermeriaConValor(null,idHoja,idProcedimiento,idUsuario,result.toString(),true,"registrodiagvalora")
	}
	
	def guardarRadioTabla(Long idHoja,Long idProcedimiento,Integer idUsuario, String valor){
				
		guardarRegistroEnfermeriaConValor(null,idHoja,idProcedimiento,idUsuario,valor,true,"registrodiagvalora")
	}
	
	def borrarRadioTabla(Long idHoja,Long idProcedimiento){
		
		RegistroHojaEnfermeria.createCriteria().get{
			eq("hoja.id",idHoja)
			eq("procedimiento.id",idProcedimiento)
		}*.delete()
		
	}
	
	def consultarRegistroTabla(Long idHoja, long idProcedimiento){
		
				
		def result = RegistroHojaEnfermeria.createCriteria().get{
			
			projections{
				property("registrodiagvalora")
			}
			
			eq("hoja.id", idHoja)
			eq("procedimiento.id", idProcedimiento )
		}
		
		
		if(!result)
			result ="00000"
		
		result
		
	}
	
	List<RegistroHojaEnfermeria> consultarCheckRubro(Long idHoja, long idRubro){
		
		def registros = []
		
		def procedimientos = CatProcedimientoNotaEnfermeria.createCriteria().list{
			eq("padre.id", idRubro )
		}.each{procedimiento->
			def registro = RegistroHojaEnfermeria.findWhere(procedimiento:procedimiento,hoja:HojaRegistroEnfermeria.get(idHoja));
			
			if(!registro){
				registro = new RegistroHojaEnfermeria(procedimiento:procedimiento,registrodiagvalora:"00000")
			}
			
			registros << registro
		
		}
		
		registros
		
	}
	
	List<RegistroIngresoEgreso> consultarIngresoRubro(Long idHoja, long idRubro){
		
		def registros = []
		
		def procedimientos = CatProcedimientoNotaEnfermeria.createCriteria().list{
			eq("padre.id", idRubro )
		}.each{procedimiento->
			def registro = RegistroIngresoEgreso.findWhere(procedimiento:procedimiento,hoja:HojaRegistroEnfermeria.get(idHoja));
			
			if(!registro){
				registro = new RegistroIngresoEgreso(procedimiento:procedimiento)
			}
			
			registros << registro
		
		}
		
		registros
		
	}
	
	
	def consultarEnfermeras(String term) {
				
		def usuarios = Usuario.createCriteria().list(){
			
			ilike("username","%"+term+"%")
			
			/*perfil{
				eq("authority","ENFERMERIA")
			}*/
			
			
		}
		
		def results = usuarios?.collect {
			def display = String.format("(%s) %s", it.username, it.nombre)
			[id:it.id, value:display, label:display]
		}
		
		results
		
	}
	
	
}
