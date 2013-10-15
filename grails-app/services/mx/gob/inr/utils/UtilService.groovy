package mx.gob.inr.utils

import mx.gob.inr.catalogos.Catalogo
import mx.gob.inr.hojaRegistroClinico.HojaRegistroEnfermeria;

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
	
}
