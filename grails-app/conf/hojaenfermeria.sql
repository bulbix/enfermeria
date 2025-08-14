insert into catnotasenfermeria(idnotatenfermeria,desnotaenfermeria) values(1,'Notas Enfermeria');

insert into catsegmentonotaenfermeria(idsegmento,dessegmento,idnotatenfermeria) values(1,'Valoracion Enfermeria',1);
insert into catsegmentonotaenfermeria(idsegmento,dessegmento,idnotatenfermeria) values(2,'Diagnostico Intervenciones',1);
insert into catsegmentonotaenfermeria(idsegmento,dessegmento,idnotatenfermeria) values(3,'Indicadores Calidad',1);
insert into catsegmentonotaenfermeria(idsegmento,dessegmento,idnotatenfermeria) values(4,'Laboratorio Gabinete',1);


--Valoracion de Enfermeria
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(995,'Valoracion Enfermeria 1',1,'check','t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(500,'Registro1','',995,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(501,'Registro2','',995,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(502,'Registro3','',995,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(503,'Otro:','',995,'t');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(996,'Valoracion Enfermeria 2',1,'check','t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(504,'Registro1','',996,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(505,'Registro2','',996,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(506,'Registro3','',996,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(507,'Otro:','',996,'t');

--Diagnosticos e Intervenciones
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(997,'Diagnosticos1',2,'check','t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(508,'Registro1','',997,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(509,'Registro2','',997,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(510,'Registro3','',997,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(511,'Otro:','',997,'t');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(998,'Diagnosticos2',2,'check','t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(512,'Registro1','',998,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(513,'Registro2','',998,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(514,'Registro3','',998,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(515,'Otro:','',998,'t');

--Indicadores de calidad
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(999,'Indicadores1',3,'radio','t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(516,'Registro1','',999,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(517,'Registro2','',999,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(518,'Registro3','',999,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(519,'Otro:','',999,'t');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(1000,'Indicadores2',3,'radio','t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(520,'Registro1','',1000,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(521,'Registro2','',1000,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(522,'Registro3','',1000,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(523,'Otro:','',1000,'t');

insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(8,'Promocion Normalidad',1,'check','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(13,'Igresos',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(18,'Medicamentos',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(95,'Escala Glasgow Otros',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(10,'Temperatura',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(11,'Frecuencia Cardiaca',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(12,'Tension Arterial Diatolica',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(17,'Tension Arterial Sistolica',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(78,'Frecuencia Respiratoria',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(85,'Laboratorio Gabinete',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(15,'Escala Dolor',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(16,'Dieta',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(14,'Egresos',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(65,'Venoclisis Instalada',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(66,'Prevension Caidas',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(67,'Sonda Vesical Instalada',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(68,'Prevension Ulcera Presion',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(69,'Intervenciones Acceso Venoso',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(79,'Requisitos',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(83,'Diagnostico Enfermeria',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(84,'Observaciones',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(86,'Otros Datos',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(93,'Faltante por Pasar',1,'','f');
insert into catrubronotaenfermeria(idrubro,desrubro,idsegmento,tipo,vista)  values(94,'Escala Maddox',4,'','f');

insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(426,'Dieta General','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(116,'Dieta Matutino','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(117,'Dieta Vespertino','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(118,'Dieta Nocturno','',8,'f');
--insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(417,'Desarrollo','',8,'t');
--insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(418,'Desviacion Salud','',8,'t');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(427,'Fecha Instalacion V','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(428,'Dias V','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(534,'Calibre V','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(537,'Fecha Instalacion V2','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(538,'Dias V2','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(539,'Calibre V2','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(429,'Fecha Instalacion S','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(430,'Dias S','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(431,'Material S','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(432,'Calibre S','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(433,'Globo S','',8,'f');

insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(419,'Diagnostico Matutino','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(420,'Diagnostico Vespertino','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(421,'Diagnostico Nocturno','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(422,'Observaciones Matutino','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(423,'Observaciones Vespertino','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(424,'Observaciones Nocturno','',8,'f');

insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(7,'Temperatura','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(8,'Frecuencia Cardiaca','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(9,'Tension Arterial Diastolica','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(10,'Tension Arterial Sistolica','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(416,'Frecuencia Respiratoria','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(425,'Laboratorio Gabinete','',8,'f');

insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(476,'Faltante Pasar Matutino','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(477,'Faltante Pasar Vespertino','',8,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(478,'Faltante Pasar Nocturno','',8,'f');

insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(479,'Escala Maddox Matutino','',94,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(480,'Escala Maddox Vespertino','',94,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(481,'Escala Maddox Nocturno','',94,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(355,'Escala Maddox Generico','',94,'f');

insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(417,'Requisito Desarrollo','',79,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(418,'Requisito Desviacion','',79,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(452,'Religion','',8,'f');

insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(600,'0','',15,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(601,'1','',15,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(602,'2','',15,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(603,'3','',15,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(604,'4','',15,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(605,'5','',15,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(606,'6','',15,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(607,'7','',15,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(608,'8','',15,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(609,'9','',15,'f');
insert into catprocedimientosnotaenfermeria(idprocedimiento,desprocedimiento,estatus,idrubro,vista) values(610,'10','',15,'f');


insert into servicio(idservicio,descservicio) values(1,'SERVICIO1');
insert into area(idarea,descarea,clavearea) values(1,'001','AREA1');
insert into diagnostico(iddiagnostico,descdiagnostico) values(1,'DIAGNOSTICO1');
insert into cama(idcama,numerocama,idarea) values(1,1,1);
insert into cie09(iddiagnostico,clavediag,descdiag) values(1,1,'Cie09');
insert into religion(idreligion,descreligion) values(1,'Catolica');

insert into paciente(idpaciente,fechanacimiento,idtipopaciente,materno,nombre,numeroregistro,paterno,sexo) 
values(1,'2000-10-15','N','Flores','Luis','N-000000000000001','Prado','H');
insert into datospaciente(idpaciente,iddatospaciente,idreligion)  values(1,1,1);
insert into admisionhospitalaria(idadmision,idpaciente,idservicio,idarea,estadoadmision,fechaIngreso,fechaEgreso,idcama,iddiagingreso) 
values(1,1,1,1,'I','2015-06-01','2015-06-01',1,1);
insert into notahospingresohosp(idnota,idpaciente,peso,talla) values(1,1,80.0,1.80);

insert into firmadigital(idusuario,tamanio,datos,bloqueada,nombre,passwordfirma,intentosfallidos,tipo) 
values(1,0,NULL,0,'LUIS','egabrag',0,'');

--select * from catsegmentonotaenfermeria ;
--select * from catrubronotaenfermeria;
--select * from catprocedimientosnotaenfermeria ;
--select * from paciente;






