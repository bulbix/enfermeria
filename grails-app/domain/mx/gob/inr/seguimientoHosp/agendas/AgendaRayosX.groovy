package mx.gob.inr.seguimientoHosp.agendas

import mx.gob.inr.seguimientoHosp.Agenda;

class AgendaRayosX extends Agenda {
  
   
   static mapping = {
	   table 'agendarayosx'
	   id column:'idcita'
	   paciente column:'idpaciente'
	   estudio column:'estudio'
	   version false
   }
}
