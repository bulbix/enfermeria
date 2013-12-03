package mx.gob.inr.seguimientoHosp

import static org.junit.Assert.*
import org.junit.*

class TerapiaTests {
	
	def terapiaService

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void "Testear notas de terapia"() {
		
		def seguimiento = SeguimientoHosp.read(76)
		def resultTipoAgendasTerapia = terapiaService.consultarAgendasTerapia(
			seguimiento.fechaElaboracion, seguimiento.paciente)		
		
		resultTipoAgendasTerapia.tiposAgenda.each{ tipoAgenda->
			switch(tipoAgenda.descripcion){
				case "fisica":
					assertEquals(1,tipoAgenda.agendas.size())
					assertEquals("ERGOMETRO",tipoAgenda.agendas[0].terapias[0].catalogo.descripcion)
					break
				case "ocupacional":
					assertEquals(1,tipoAgenda.agendas.size())
					break				
			}
		}
    }
	
	
}
