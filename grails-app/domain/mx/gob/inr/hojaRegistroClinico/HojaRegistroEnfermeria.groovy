package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.utils.AdmisionHospitalaria
import mx.gob.inr.utils.Liquido
import mx.gob.inr.utils.Paciente
import mx.gob.inr.utils.SignoVital
import mx.gob.inr.utils.Turno

class HojaRegistroEnfermeria {
	
	
	Long id
	
	AdmisionHospitalaria admision
	Paciente paciente
	Date fechaElaboracion = new Date()
	Float peso
	Float talla
	String alergias
	String comorbilidad
	String otros	
	String turnoActual
	
	//List<HojaRegistroEnfermeriaTurno> turnos
	
	HojaRegistroEnfermeriaTurno turnoMatutino = new HojaRegistroEnfermeriaTurno();
	HojaRegistroEnfermeriaTurno turnoVespertino = new HojaRegistroEnfermeriaTurno();
	HojaRegistroEnfermeriaTurno turnoNocturno = new HojaRegistroEnfermeriaTurno();
	
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
	List<RegistroHojaEnfermeria> dietas = new ArrayList<RegistroHojaEnfermeria>()
	List<CatRubroNotaEnfermeria> rubrosValoracion
	List<CatRubroNotaEnfermeria> rubrosDiagnostico
	List<CatRubroNotaEnfermeria> rubrosIndicador
	
	List<RegistroHojaEnfermeria> requisitos = new ArrayList<RegistroHojaEnfermeria>()
	List<Liquido> ingresos = new ArrayList<Liquido>()
	
	List<Liquido> egresos = new ArrayList<Liquido>()
	List<String> medicamentos = new ArrayList<String>()
	List<String> escalaOtros = new ArrayList<String>()
	
	
	public HojaRegistroEnfermeria(){
		turnos = new ArrayList<HojaRegistroEnfermeriaTurno>()
		signosVitales << new SignoVital(hora:1)
		
		requisitos << new RegistroHojaEnfermeria() << new RegistroHojaEnfermeria(); 
		
		ingresos << new Liquido(descripcion:'Medicamento Oral') << new Liquido(descripcion:'Via Oral')		
		
		egresos << new Liquido(descripcion:'Diuresis') << new Liquido(descripcion:'Cateterismo') <<
		new Liquido(descripcion:'Fuga') << new Liquido(descripcion:'Evacuacion') << new Liquido(descripcion:'Drenajes') <<
		new Liquido(descripcion:'Vomito') << new Liquido(descripcion:'Sangrado') << new Liquido(descripcion:'Sonda Vesical')
		 
		medicamentos << ""
		
		escalaOtros << "Respuesta Motora" << "Respuesta Ocular" << "Respuesta Verbal" << "Posicion en cama" << "Perimetros" << "Glucosa Capilar"
	}
	
	static transients = ["has","dm","nef","ic","ir",
		"signosVitales","dietas","rubrosValoracion","rubrosDiagnostico","requisitos",
		"ingresos","egresos","medicamentos","escalaOtros","rubrosIndicador","turnoActual","turnoMatutino","turnoVespertino","turnoNocturno"]
	
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
	
	public void establecerTurnos() {
		if (turnos != null) {
			for (HojaRegistroEnfermeriaTurno turno : getTurnos()) {
				if (Turno.MATUTINO.equals(turno.getTurno())) {
					turnoMatutino = turno;
					continue;
				}
				if (Turno.VESPERTINO.equals(turno
						.getTurno())) {
					turnoVespertino = turno;
					continue;
				}
				if (Turno.NOCTURNO.equals(turno
						.getTurno())) {
					turnoNocturno = turno;
					continue;
				}
			}			
		}
	}
	
	
	
	def afterLoad(){
		desglosarComorbilidad();
		establecerTurnos();		
	}
	
	def beforeInsert(){		
	}
	
	def beforeUpdate(){		
	}
	
	
	
}
