package mx.gob.inr.seguimientoHosp

import static org.junit.Assert.*
import org.junit.*
import mx.gob.inr.seguimientoHosp.agendas.*

class EstudioTests {
	
	def estudioService

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testAllAgendasAndCatalogos() {
        AgendaMedicinaNuclear.all
		AgendaRayosX.all		
		AgendaResonanciaMagnetica.all
		AgendaTomografia.all
		AgendaUltraSonido.all
		CatEstudiosLabora.all
    }
	
	@Test
	void "Consultar Agendas"(){
		def seguimiento = SeguimientoHosp.read(68)
		def agendasTipo = estudioService.consultarTipoAgendas(seguimiento.fechaElaboracion,seguimiento.paciente)
		
		agendasTipo.each{ agendaTipo->
			switch(agendaTipo.descripcion){
				case "Resonancia Magnetica":
					assertEquals(2,agendaTipo.agendas.size())
					break
				case "Rayos X":
					assertEquals(2,agendaTipo.agendas.size())
					break
				case "Tomografia":
					assertEquals(2,agendaTipo.agendas.size())
					break
			}		
		}
	}
	
}
