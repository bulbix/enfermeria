delete from catprocedimientosnotaenfermeria;
delete from catrubronotaenfermeria;
delete from catsegmentonotaenfermeria;
delete from catnotasenfermeria;

delete from servicio;
delete from area;
delete from diagnostico;
delete from cama;
delete from cie09;
delete from religion;
delete from admisionhospitalaria;
delete from notahospingresohosp;
delete from datospaciente;
delete from paciente;
delete from firmadigital;
delete from registroingresoegresos;
delete from registroshojaenfermeria;
delete from hojaregistroenfermeriaturno;
delete from hojaregistroenfermeria;


insert into catnotasenfermeria values(1,'Notas Enfermeria');

insert into catsegmentonotaenfermeria values(1,'Valoracion Enfermeria',1);
insert into catsegmentonotaenfermeria values(2,'Diagnostico Intervenciones',1);
insert into catsegmentonotaenfermeria values(3,'Indicadores Calidad',1);
insert into catsegmentonotaenfermeria values(4,'Laboratorio Gabinete',1);


--Valoracion de Enfermeria
insert into catrubronotaenfermeria  values(995,'Valoracion Enfermeria 1',1,'check','t');
insert into catprocedimientosnotaenfermeria values(500,'Registro1','',995,'t');
insert into catprocedimientosnotaenfermeria values(501,'Registro2','',995,'t');
insert into catprocedimientosnotaenfermeria values(502,'Registro3','',995,'t');
insert into catprocedimientosnotaenfermeria values(503,'Otro:','',995,'t');
insert into catrubronotaenfermeria  values(996,'Valoracion Enfermeria 2',1,'check','t');
insert into catprocedimientosnotaenfermeria values(504,'Registro1','',996,'t');
insert into catprocedimientosnotaenfermeria values(505,'Registro2','',996,'t');
insert into catprocedimientosnotaenfermeria values(506,'Registro3','',996,'t');
insert into catprocedimientosnotaenfermeria values(507,'Otro:','',996,'t');

--Diagnosticos e Intervenciones
insert into catrubronotaenfermeria  values(997,'Diagnosticos1',2,'check','t');
insert into catprocedimientosnotaenfermeria values(508,'Registro1','',997,'t');
insert into catprocedimientosnotaenfermeria values(509,'Registro2','',997,'t');
insert into catprocedimientosnotaenfermeria values(510,'Registro3','',997,'t');
insert into catprocedimientosnotaenfermeria values(511,'Otro:','',997,'t');
insert into catrubronotaenfermeria  values(998,'Diagnosticos2',2,'check','t');
insert into catprocedimientosnotaenfermeria values(512,'Registro1','',998,'t');
insert into catprocedimientosnotaenfermeria values(513,'Registro2','',998,'t');
insert into catprocedimientosnotaenfermeria values(514,'Registro3','',998,'t');
insert into catprocedimientosnotaenfermeria values(515,'Otro:','',998,'t');

--Indicadores de calidad
insert into catrubronotaenfermeria  values(999,'Indicadores1',3,'radio','t');
insert into catprocedimientosnotaenfermeria values(516,'Registro1','',999,'t');
insert into catprocedimientosnotaenfermeria values(517,'Registro2','',999,'t');
insert into catprocedimientosnotaenfermeria values(518,'Registro3','',999,'t');
insert into catprocedimientosnotaenfermeria values(519,'Otro:','',999,'t');
insert into catrubronotaenfermeria  values(1000,'Indicadores2',3,'radio','t');
insert into catprocedimientosnotaenfermeria values(520,'Registro1','',1000,'t');
insert into catprocedimientosnotaenfermeria values(521,'Registro2','',1000,'t');
insert into catprocedimientosnotaenfermeria values(522,'Registro3','',1000,'t');
insert into catprocedimientosnotaenfermeria values(523,'Otro:','',1000,'t');

insert into catrubronotaenfermeria  values(8,'Promocion Normalidad',1,'check','f');
insert into catrubronotaenfermeria  values(13,'Igresos',1,'','f');
insert into catrubronotaenfermeria  values(18,'Medicamentos',1,'','f');
insert into catrubronotaenfermeria  values(95,'Escala Glasgow Otros',1,'','f');
insert into catrubronotaenfermeria  values(10,'Temperatura',1,'','f');
insert into catrubronotaenfermeria  values(11,'Frecuencia Cardiaca',1,'','f');
insert into catrubronotaenfermeria  values(12,'Tension Arterial Diatolica',1,'','f');
insert into catrubronotaenfermeria  values(17,'Tension Arterial Sistolica',1,'','f');
insert into catrubronotaenfermeria  values(78,'Frecuencia Respiratoria',1,'','f');
insert into catrubronotaenfermeria  values(85,'Laboratorio Gabinete',1,'','f');
insert into catrubronotaenfermeria  values(15,'Escala Dolor',1,'','f');
insert into catrubronotaenfermeria  values(16,'Dieta',1,'','f');
insert into catrubronotaenfermeria  values(14,'Egresos',1,'','f');
insert into catrubronotaenfermeria  values(65,'Venoclisis Instalada',1,'','f');
insert into catrubronotaenfermeria  values(66,'Prevension Caidas',1,'','f');
insert into catrubronotaenfermeria  values(67,'Sonda Vesical Instalada',1,'','f');
insert into catrubronotaenfermeria  values(68,'Prevension Ulcera Presion',1,'','f');
insert into catrubronotaenfermeria  values(69,'Intervenciones Acceso Venoso',1,'','f');
insert into catrubronotaenfermeria  values(79,'Requisitos',1,'','f');
insert into catrubronotaenfermeria  values(83,'Diagnostico Enfermeria',1,'','f');
insert into catrubronotaenfermeria  values(84,'Observaciones',1,'','f');
insert into catrubronotaenfermeria  values(86,'Otros Datos',1,'','f');
insert into catrubronotaenfermeria  values(93,'Faltante por Pasar',1,'','f');
insert into catrubronotaenfermeria  values(94,'Escala Maddox',4,'','f');

insert into catprocedimientosnotaenfermeria values(426,'Dieta General','',8,'f');
insert into catprocedimientosnotaenfermeria values(116,'Dieta Matutino','',8,'f');
insert into catprocedimientosnotaenfermeria values(117,'Dieta Vespertino','',8,'f');
insert into catprocedimientosnotaenfermeria values(118,'Dieta Nocturno','',8,'f');
--insert into catprocedimientosnotaenfermeria values(417,'Desarrollo','',8,'t');
--insert into catprocedimientosnotaenfermeria values(418,'Desviacion Salud','',8,'t');
insert into catprocedimientosnotaenfermeria values(427,'Fecha Instalacion V','',8,'f');
insert into catprocedimientosnotaenfermeria values(428,'Dias V','',8,'f');
insert into catprocedimientosnotaenfermeria values(534,'Calibre V','',8,'f');
insert into catprocedimientosnotaenfermeria values(537,'Fecha Instalacion V2','',8,'f');
insert into catprocedimientosnotaenfermeria values(538,'Dias V2','',8,'f');
insert into catprocedimientosnotaenfermeria values(539,'Calibre V2','',8,'f');
insert into catprocedimientosnotaenfermeria values(429,'Fecha Instalacion S','',8,'f');
insert into catprocedimientosnotaenfermeria values(430,'Dias S','',8,'f');
insert into catprocedimientosnotaenfermeria values(431,'Material S','',8,'f');
insert into catprocedimientosnotaenfermeria values(432,'Calibre S','',8,'f');
insert into catprocedimientosnotaenfermeria values(433,'Globo S','',8,'f');

insert into catprocedimientosnotaenfermeria values(419,'Diagnostico Matutino','',8,'f');
insert into catprocedimientosnotaenfermeria values(420,'Diagnostico Vespertino','',8,'f');
insert into catprocedimientosnotaenfermeria values(421,'Diagnostico Nocturno','',8,'f');
insert into catprocedimientosnotaenfermeria values(422,'Observaciones Matutino','',8,'f');
insert into catprocedimientosnotaenfermeria values(423,'Observaciones Vespertino','',8,'f');
insert into catprocedimientosnotaenfermeria values(424,'Observaciones Nocturno','',8,'f');

insert into catprocedimientosnotaenfermeria values(7,'Temperatura','',8,'f');
insert into catprocedimientosnotaenfermeria values(8,'Frecuencia Cardiaca','',8,'f');
insert into catprocedimientosnotaenfermeria values(9,'Tension Arterial Diastolica','',8,'f');
insert into catprocedimientosnotaenfermeria values(10,'Tension Arterial Sistolica','',8,'f');
insert into catprocedimientosnotaenfermeria values(416,'Frecuencia Respiratoria','',8,'f');
insert into catprocedimientosnotaenfermeria values(425,'Laboratorio Gabinete','',8,'f');

insert into catprocedimientosnotaenfermeria values(476,'Faltante Pasar Matutino','',8,'f');
insert into catprocedimientosnotaenfermeria values(477,'Faltante Pasar Vespertino','',8,'f');
insert into catprocedimientosnotaenfermeria values(478,'Faltante Pasar Nocturno','',8,'f');

insert into catprocedimientosnotaenfermeria values(479,'Escala Maddox Matutino','',94,'f');
insert into catprocedimientosnotaenfermeria values(480,'Escala Maddox Vespertino','',94,'f');
insert into catprocedimientosnotaenfermeria values(481,'Escala Maddox Nocturno','',94,'f');
insert into catprocedimientosnotaenfermeria values(355,'Escala Maddox Generico','',94,'f');

insert into catprocedimientosnotaenfermeria values(417,'Requisito Desarrollo','',79,'f');
insert into catprocedimientosnotaenfermeria values(418,'Requisito Desviacion','',79,'f');
insert into catprocedimientosnotaenfermeria values(452,'Religion','',8,'f');

insert into catprocedimientosnotaenfermeria values(600,'0','',15,'f');
insert into catprocedimientosnotaenfermeria values(601,'1','',15,'f');
insert into catprocedimientosnotaenfermeria values(602,'2','',15,'f');
insert into catprocedimientosnotaenfermeria values(603,'3','',15,'f');
insert into catprocedimientosnotaenfermeria values(604,'4','',15,'f');
insert into catprocedimientosnotaenfermeria values(605,'5','',15,'f');
insert into catprocedimientosnotaenfermeria values(606,'6','',15,'f');
insert into catprocedimientosnotaenfermeria values(607,'7','',15,'f');
insert into catprocedimientosnotaenfermeria values(608,'8','',15,'f');
insert into catprocedimientosnotaenfermeria values(609,'9','',15,'f');
insert into catprocedimientosnotaenfermeria values(610,'10','',15,'f');


insert into servicio values(1,'SERVICIO1');
insert into area values(1,'001','AREA1');
insert into diagnostico values(1,'DIAGNOSTICO1');
insert into cama values(1,1,1);
insert into cie09 values(1,1,'Cie09');
insert into religion values(1,'Catolica');

insert into paciente values(1,'2000-10-15','N','Flores','Luis','N-000000000000001','Prado','H');
insert into datospaciente  values(1,1,1);
insert into admisionhospitalaria values(1,1,1,1,'I','2015-06-01','2015-06-01',1,1);
insert into notahospingresohosp values(1,1,80.0,1.80);

insert into firmadigital values(1,0,NULL,0,'LUIS','egabrag',0,'');

--select * from catsegmentonotaenfermeria ;
select * from catrubronotaenfermeria;
--select * from catprocedimientosnotaenfermeria ;
--select * from paciente;






