package mx.gob.inr.seguimientoHosp.agendas

class AgendaRayosX extends Agenda {
  
   
   static mapping = {
	   table 'agendarayosx'
	   id column:'idcita'
	   paciente column:'idpaciente'
	   estudio column:'estudio'
	   version false
   }
}
