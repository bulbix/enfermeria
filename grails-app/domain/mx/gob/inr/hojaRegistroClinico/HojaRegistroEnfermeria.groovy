package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.utils.AdmisionHospitalaria
import mx.gob.inr.utils.Paciente
import mx.gob.inr.utils.SignoVital

class HojaRegistroEnfermeria {
	
	AdmisionHospitalaria admision
	Paciente paciente
	Date fechaElaboracion = new Date()
	Float peso
	Float talla
	String alergias
	String comorbilidad
	String otros
	
	static hasMany = [turnos:HojaRegistroEnfermeriaTurno]
	
	static mapping = {
		table 'hojaregistroenfermeria'
		version false
		id column:'idhojaregistroenfe'
		id generator:'sequence' ,params:[sequence:'sq_hoja_enfermeria']
		admision column:'idadmision'
		paciente column:'idpaciente'
		fechaElaboracion column:'fechaelaboracion'
		
	}
	
	Boolean has
	Boolean dm
	Boolean nef
	Boolean ic
	Boolean ir
	
	List<SignoVital> signosVitales = new ArrayList<SignoVital>()
	List<RegistroHojaEnfermeria> escalaDolor = new ArrayList<RegistroHojaEnfermeria>()
	List<RegistroHojaEnfermeria> dietas = new ArrayList<RegistroHojaEnfermeria>()
	List<CatRubroNotaEnfermeria> rubrosValoracion
	
	public HojaRegistroEnfermeria(){
		turnos = new HashSet<HojaRegistroEnfermeriaTurno>()
		signosVitales << new SignoVital(hora:1)
	}
	
	static transients = ["has","dm","nef","ic","ir",
		"signosVitales","escalaDolor","dietas","rubrosValoracion"]
	
	void asignarComorbilidad(){
		def result = new StringBuffer("00000")		
		if(has) result.setCharAt(0, '1' as char)
		if(dm) 	result.setCharAt(1, '1' as char)
		if(nef) result.setCharAt(2, '1' as char)
		if(ic) 	result.setCharAt(3, '1' as char)
		if(ir) 	result.setCharAt(4, '1' as char)
		
		comorbilidad = result.toString()		
	}
	
	void desglosarComorbilidad(){
		
		if(comorbilidad){		
			has=comorbilidad[0]=='1'
			dm=comorbilidad[1]=='1'
			nef=comorbilidad[2]=='1'
			ic=comorbilidad[3]=='1'
			ir=comorbilidad[4]=='1'
		}
	}
	
	def afterLoad(){
		desglosarComorbilidad();		
	}
	
	def beforeInsert(){		
	}
	
	def beforeUpdate(){		
	}
	
	
	
}
