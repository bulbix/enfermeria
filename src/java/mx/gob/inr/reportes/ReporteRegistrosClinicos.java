package mx.gob.inr.reportes;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;

import mx.gob.inr.utils.IndicadorCalidad;
import mx.gob.inr.utils.Liquido;
import mx.gob.inr.utils.Paciente;
import mx.gob.inr.utils.ReporteHojaFacadeService;
import mx.gob.inr.utils.SignoVital;
import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.hojaRegistroClinico.HojaRegistroEnfermeria;
import mx.gob.inr.hojaRegistroClinico.RegistroHojaEnfermeria;
import mx.gob.inr.hojaRegistroClinico.RegistroIngresoEgreso;
import mx.gob.inr.seguridad.FirmaDigital;
import mx.gob.inr.seguridad.Usuario;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import mx.gob.inr.utils.Religion;
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*;

public class ReporteRegistrosClinicos extends Tablas implements Serializable {
	Font fuente = FontFactory.getFont("ARIAL", 9, Font.BOLD);
	Font data = FontFactory.getFont("ARIAL", 7, Font.NORMAL);
	Font font = FontFactory.getFont("ARIAL", 7, Font.BOLD);
	Font fontSmall = FontFactory.getFont("ARIAL", 5, Font.BOLD);
	Font fontAzul = FontFactory.getFont("ARIAL", 7, Font.BOLD, Color.BLUE);
	Font fontBigAzul = FontFactory.getFont("ARIAL", 10, Font.BOLD, Color.BLUE);
	Font fontRojo = FontFactory.getFont("ARIAL", 7, Font.BOLD, Color.RED);
	Font fontBigRojo = FontFactory.getFont("ARIAL", 10, Font.BOLD, Color.RED);
	Font fontWhite = FontFactory.getFont("ARIAL", 7, Font.BOLD, Color.WHITE);
	Font fontVerde = FontFactory.getFont("ARIAL", 7, Font.BOLD, new Color(0,120, 0));
	Font fontAmarillo = FontFactory.getFont("ARIAL", 7, Font.BOLD, Color.YELLOW);
	Font font2 = FontFactory.getFont("ARIAL", 6, Font.BOLD);
	
	Float totalTotalIngresos = 0f;
	Paciente paciente;
	
	ReporteHojaFacadeService service;	

	public ReporteRegistrosClinicos(ReporteHojaFacadeService service) {
		this.service = service;
	}

	/***
	 * Proporciona el servicio de firma
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws BadElementException 
	 */
	private Image obtenerFirma(Usuario usuario) throws BadElementException, MalformedURLException, IOException{
		FirmaDigital firma = service.consultarFirma(usuario);
		Image imagenFirma = Image.getInstance(firma.getDatos());
		return imagenFirma;
	}

	public byte[] generarReporte(HojaRegistroEnfermeria model) {

		//ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		
		//HttpSession session = (HttpSession)RequestContextHolder.currentRequestAttributes().getSessionMutex();
		
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		Document documento = new Document(PageSize.LETTER, 10, 10, 10, 10);
		Borde bordes = new Borde();

		try {

			//String path = PropertyReader.readProperty("inr.saihweb.context");
			String logoPath = "web-app/images/logotipo.jpg";
			Image logo = Image.getInstance(logoPath);	
			

			PdfWriter writer = PdfWriter.getInstance(documento, stream);
			addProperties(documento);
			documento.open();

			// Principal
			PdfPTable principal = new PdfPTable(1);
			principal.setWidthPercentage(100);
			principal.getDefaultCell().setBorder(0);

			// Encabezado
			float encabezadoWidth[] = { 5, 8, 8, 50, 5, 20, 4 };
			PdfPTable encabezado = new PdfPTable(encabezadoWidth);
			encabezado.getDefaultCell().setBorder(0);

			PdfPTable titulo = new PdfPTable(1);
			titulo.getDefaultCell().setBorder(0);
			titulo.getDefaultCell()
					.setHorizontalAlignment(Element.ALIGN_CENTER);

			titulo.addCell(new Paragraph(
					"INSTITUTO NACIONAL DE REHABILITACION", fuente));
			titulo.addCell(new Paragraph("DIRECCION QUIRURGICA", fuente));
			titulo.addCell(new Paragraph("SUBDIRECCION DE ENFERMERIA", fuente));
			titulo.addCell(new Paragraph(
					"HOJA DE REGISTROS CLINICOS DE ENFERMERIA", fuente));

			PdfPTable alergias = new PdfPTable(1);
			alergias.getDefaultCell().setBorder(0);

			float datosAlergiasWidth[] = { 2, 20, 18, 20, 18, 20, 2 };
			PdfPTable datosAlergias = new PdfPTable(datosAlergiasWidth);
			datosAlergias.getDefaultCell().setBorder(0);

			datosAlergias.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_CENTER);

			datosAlergias.addCell(new Paragraph(" ", font));
			datosAlergias.getDefaultCell().setColspan(5);
			datosAlergias.getDefaultCell().setBorderWidthBottom(1);
			datosAlergias.addCell(new Paragraph("ALERGIAS", font));
			datosAlergias.getDefaultCell().setBorderWidthBottom(0);
			datosAlergias.getDefaultCell().setColspan(0);
			datosAlergias.addCell(new Paragraph("", font));

			datosAlergias.addCell(new Paragraph(" ", font));
			datosAlergias.getDefaultCell().setColspan(5);
			datosAlergias.getDefaultCell().setBorderWidthBottom(1);
			datosAlergias.addCell(new Paragraph(model.getAlergias(), fontRojo));
			datosAlergias.getDefaultCell().setBorderWidthBottom(0);
			datosAlergias.getDefaultCell().setColspan(0);
			datosAlergias.addCell(new Paragraph(" ", font));

			datosAlergias.addCell(new Paragraph(" ", font));
			datosAlergias.getDefaultCell().setColspan(5);
			datosAlergias.getDefaultCell().setBorderWidthBottom(1);
			datosAlergias.addCell(new Paragraph(model.getOtros(), fontAzul));
			datosAlergias.getDefaultCell().setBorderWidthBottom(0);
			datosAlergias.getDefaultCell().setColspan(0);
			datosAlergias.addCell(new Paragraph(" ", font));

			datosAlergias.addCell(new Paragraph(model.getAlergias(), font));
			datosAlergias.getDefaultCell().setColspan(5);
			datosAlergias.getDefaultCell().setBorderWidthBottom(1);
			datosAlergias.addCell(new Paragraph("COMORBILIDAD", font));
			datosAlergias.getDefaultCell().setBorderWidthBottom(0);
			datosAlergias.getDefaultCell().setColspan(0);
			datosAlergias.addCell(new Paragraph(" ", font));

			datosAlergias.getDefaultCell().setColspan(1);
			datosAlergias.addCell(new Paragraph(" ", font));
			datosAlergias.getDefaultCell().setBorderWidthRight(1);

			String comorbilidad = model.getComorbilidad();

			if (comorbilidad.charAt(0) == '1')
				datosAlergias.addCell(subrayado("HAS", font2));
			else
				datosAlergias.addCell(new Paragraph("HAS", font2));

			if (comorbilidad.charAt(1) == '1')
				datosAlergias.addCell(subrayado("DM", font2));
			else
				datosAlergias.addCell(new Paragraph("DM", font2));

			if (comorbilidad.charAt(2) == '1')
				datosAlergias.addCell(subrayado("NEF", font2));
			else
				datosAlergias.addCell(new Paragraph("NEF", font2));

			if (comorbilidad.charAt(3) == '1')
				datosAlergias.addCell(subrayado("IC", font2));
			else
				datosAlergias.addCell(new Paragraph("IC", font2));

			datosAlergias.getDefaultCell().setBorderWidthRight(0);

			if (comorbilidad.charAt(4) == '1')
				datosAlergias.addCell(subrayado("IR", font2));
			else
				datosAlergias.addCell(new Paragraph("IR", font2));

			datosAlergias.addCell(new Paragraph(" ", font2));

			alergias.getDefaultCell().setCellEvent(bordes);
			alergias.addCell(datosAlergias);

			encabezado.addCell(new Paragraph(" ", fuente));
			encabezado.addCell(logo);
			encabezado.addCell(new Paragraph(" ", fuente));
			encabezado.addCell(titulo);
			encabezado.addCell(new Paragraph(" ", fuente));
			encabezado.addCell(alergias);
			encabezado.addCell(new Paragraph(" ", fuente));
			// End Encabezado

			// Datos Paciente
			PdfPTable datosPaciente = new PdfPTable(1);
			datosPaciente.getDefaultCell().setBorder(0);
			datosPaciente.getDefaultCell().setPadding(1);

			PdfPTable contenidoDatosPaciente = new PdfPTable(1);
			contenidoDatosPaciente.getDefaultCell().setBorder(0);
			contenidoDatosPaciente.getDefaultCell().setVerticalAlignment(
					Element.ALIGN_BOTTOM);

			float nombreWidth[] = { 1, 7, 30, 10, 10, 5, 5, 5, 11, 5, 11, 3 };
			PdfPTable nombre = new PdfPTable(nombreWidth);
			nombre.getDefaultCell().setBorder(0);

			nombre.addCell(new Paragraph(" ", font));
			nombre.addCell(new Paragraph("NOMBRE ", font));

			paciente = model.getAdmision().getPaciente();

			String descPaciente = paciente.getNombre() + " "
					+ paciente.getPaterno() + " " + paciente.getMaterno();
			nombre.addCell(subrayado(descPaciente, fontAzul));

			String fechaNac = Util.getDateString(paciente.getFechanacimiento());
			nombre.addCell(new Paragraph("FECHA NAC ", font));
			nombre.addCell(subrayado(fechaNac, fontAzul));

			String edad = Util.getEdad(model.getPaciente().getFechanacimiento()) + "";
			nombre.addCell(new Paragraph("EDAD ", font));
			nombre.addCell(subrayado(edad, fontAzul));

			String sexo = model.getAdmision().getPaciente().getSexo();
			nombre.addCell(new Paragraph("SEXO ", font));
			nombre.addCell(subrayado(sexo, fontAzul));

			String cama = model.getAdmision().getCama().getNumerocama().toString();
			nombre.addCell(new Paragraph("CAMA ", font));
			nombre.addCell(subrayado(cama, fontAzul));
			nombre.addCell(new Paragraph(" ", font));

			float registroWidth[] = { 1, 9, 18, 8, 25, 12, 34, 3 };
			PdfPTable registro = new PdfPTable(registroWidth);
			registro.getDefaultCell().setBorder(0);

			registro.addCell(new Paragraph(" ", font));

			String numRegistro = model.getAdmision().getPaciente().getNumeroregistro();
			registro.addCell(new Paragraph("REGISTRO ", font));
			registro.addCell(subrayado(numRegistro, fontAzul));

			String servicio = model.getAdmision().getServicio().getDescripcion();
			registro.addCell(new Paragraph("SERVICIO", font));
			registro.addCell(subrayado(servicio, fontAzul));

			String diagnostico = model.getAdmision().getDiagnosticoIngreso().getDescripcion();
			
			registro.addCell(new Paragraph("DIAGNOSTICO", font));
			registro.addCell(subrayado(diagnostico, fontAzul));
			registro.addCell(new Paragraph(" ", fontAzul));

			float estanciaWidth[] = { 1, 35, 14, 13, 6, 28, 3 };
			PdfPTable estancia = new PdfPTable(estanciaWidth);
			estancia.getDefaultCell().setBorder(0);

			estancia.addCell(new Paragraph(" ", font));
			estancia.addCell(subrayado(" ", font));
			estancia.addCell(new Paragraph("DIAS DE ESTANCIA", font));
			estancia.addCell(subrayado(model.getAdmision().getDiasHosp().toString(), fontAzul));
			estancia.addCell(new Paragraph("FECHA", font));
			estancia.addCell(subrayado(Util.getDateString(model.getFechaElaboracion()), fontAzul));
			estancia.addCell(new Paragraph(" ", font));

			contenidoDatosPaciente.addCell(nombre);
			contenidoDatosPaciente.addCell(registro);
			contenidoDatosPaciente.addCell(estancia);

			datosPaciente.getDefaultCell().setCellEvent(bordes);
			datosPaciente.addCell(contenidoDatosPaciente);
			// End Datos Paciente

			// Carga las tablas con los registros
			PdfPTable laboratorio = this.signosVitales(model.getSignosVitales(), model.getPeso(),model.getTalla());

			PdfPTable dietas = this.dieta(model.getDietas());			
			 
			List<RegistroHojaEnfermeria> registrosDolor = service.consultarEscalaDolor(model.getId());
			
			PdfPTable dolor = this.escalaDolor(registrosDolor);

			PdfPTable ingresos = this.ingresos(model.getId(), model.getIngresos());
			
			
			PdfPTable egresos = this.egresos(model.getId(), model.getEgresos());
			
			
			PdfPTable medicamentos = this.medicamentos(model.getId(), model);
			
			
			PdfPTable valoracionEnfermeria = this.valoracionEnfermeria(model.getId(), model.getRubrosValoracion(),
			model.getRequisitos(), new Religion());

			PdfPTable diagIntervenciones = this.diagnosticosIntervenciones(model.getId(),model.getRubrosDiagnostico());	
			
			IndicadorCalidad[] indicadores = new IndicadorCalidad[model.getIndicadores().size()];
			indicadores = model.getIndicadores().toArray(indicadores);
			RegistroHojaEnfermeria[] escalaMadox = new RegistroHojaEnfermeria[model.getEscalaMadox().size()];
			escalaMadox = model.getEscalaMadox().toArray(escalaMadox);
			
			PdfPTable indicadoresOpcion = this.indicadoresCalidadOptionCheck(
					model.getId(), model.getRubrosIndicador(),indicadores,	escalaMadox);
			
			RegistroHojaEnfermeria[] planeacionObservaciones = new RegistroHojaEnfermeria[model.getDiagEnfermeriaObservaciones().size()];
			planeacionObservaciones = model.getDiagEnfermeriaObservaciones().toArray(planeacionObservaciones);

			PdfPTable diagEnfermeriaObservaciones = this
					.diagEnfermeriaObservaciones(planeacionObservaciones, model);

			principal.addCell(encabezado);
			principal.addCell(datosPaciente);
			principal.addCell(laboratorio);
			principal.addCell(dolor);
			principal.addCell(dietas);
			principal.addCell(ingresos);

			PdfPTable subPrincipal = new PdfPTable(1);
			subPrincipal.getDefaultCell().setBorderWidth(0);
			subPrincipal.addCell(egresos);
			subPrincipal.addCell(medicamentos);
			subPrincipal.addCell(new Paragraph(" "));
			subPrincipal.addCell(valoracionEnfermeria);
			subPrincipal.addCell(diagIntervenciones);
			subPrincipal.addCell(indicadoresOpcion);
			subPrincipal.addCell(diagEnfermeriaObservaciones);

			principal.addCell(subPrincipal);

			documento.add(principal);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			documento.close();
			//session.setAttribute("Content-Type", "application/pdf");
			
			
			/*session.setAttribute("FileNameReport","ReporteHoja" + cadenaRandom +".pdf");
			byte[] b = stream.toByteArray();
			session.setAttribute("ReportInBytesCode", b);
			ReporteByteArrayWrapper reporte = new ReporteByteArrayWrapper("ReporteHoja_" + cadenaRandom, b);
			session.setAttribute("Reporte", reporte);*/
		}

		return stream.toByteArray();
	}

	/****
	 * Regresa un arreglo con los signos vitales
	 * 
	 * @param listaTemperatura
	 * @param listaFrecuencia
	 * @return
	 */
	private PdfPTable signosVitales(List<SignoVital> signosVitales, Float peso,
			Float talla) {

		/** RELLENAMOS LA MATRIX CON LOS SIGNOS VITALES ES LA LOGICA DE NEGOCIO */
		Integer[] colSignos = { 0, 20, 21, 22, 23, 24, 25, 26, 3, 4, 5, 6, 7,
				8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
		Integer[] colLabGab = { 0, 18, 19, 20, 21, 22, 23, 24, 1, 2, 3, 4, 5,
				6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		/********************************************* 8*9 ***************************************/
		String[][] matrixDatos = new String[5][27];

		String[] vectorLabGabinete = new String[27];

		// Cargamos las cabeceras
		matrixDatos[0][0] = "Frecuencia Cardiaca";
		matrixDatos[1][0] = "Frecuencia Respiratoria";
		matrixDatos[2][0] = "Tension Arterial Sistolica";
		matrixDatos[3][0] = "Tension Arterial Diastolica";
		matrixDatos[4][0] = "Temperatura";

		// Llenamos los signos vitales
		for (SignoVital signoVital : signosVitales) {

			if (signoVital.getCardiaca().getProcedimiento().getDescripcion() != null) {

				// Obtenemos el turno y lo concatenamos
				String turno = turno(signoVital.getHora());
				matrixDatos[0][colSignos[signoVital.getHora()]] = turno
						+ signoVital.getCardiaca().getOtro();
			}

			// Cargamos la frecuencia respiratoria
			if (signoVital.getRespiracion() != null
					&& !signoVital.getRespiracion().getOtro()
							.isEmpty()) {

				// Obtenemos el turno y lo concatenamos
				String turno = turno(signoVital.getHora());
				matrixDatos[1][colSignos[signoVital.getHora()]] = turno
						+ signoVital.getRespiracion().getOtro();
			}

			if (signoVital.getSistolica().getProcedimiento()
					.getDescripcion() != null) {

				// Obtenemos el turno y lo concatenamos
				String turno = turno(signoVital.getHora());
				matrixDatos[2][colSignos[signoVital.getHora()]] = turno
						+ signoVital.getSistolica().getOtro();
			}

			if (signoVital.getDiastolica().getProcedimiento()
					.getDescripcion() != null) {
				// Obtenemos el turno y lo concatenamos
				String turno = turno(signoVital.getHora());
				matrixDatos[3][colSignos[signoVital.getHora()]] = turno
						+ signoVital.getDiastolica().getOtro();

			}

			if (signoVital.getTemperatura().getProcedimiento()
					.getDescripcion() != null) {

				// Obtenemos el turno y lo concatenamos
				String turno = turno(signoVital.getHora());
				matrixDatos[4][colSignos[signoVital.getHora()]] = turno
						+ signoVital.getTemperatura().getOtro();
			}

			// Cargamos el Laboratorio y Gabinete
			if (signoVital.getGabinete() != null
					&& !signoVital.getGabinete().getOtro().isEmpty()) {

				// Obtenemos el turno y lo concatenamos
				String turno = turno(signoVital.getHora());
				vectorLabGabinete[colLabGab[signoVital.getHora()]] = turno
						+ signoVital.getGabinete().getOtro();
			}

		}

		/*** CARGAMOS LA MATRIZ EN LA TABLA **/
		float w = 3.5F;

		// Laboratorio
		PdfPTable laboratorio = new PdfPTable(1);
		laboratorio.getDefaultCell().setBorder(0);
		laboratorio.getDefaultCell().setPadding(0);

		float labWidth[] = { 1, 2, 6, 3, 3, w, w, w, w, w, w, w, w, w, w, w, w,
				w, w, w, w, w, w, w, w, w, w, w, w, 1 };// 30
		PdfPTable tablaLab = new PdfPTable(labWidth);
		tablaLab.getDefaultCell().setPadding(1);
		tablaLab.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

		int n = 8;

		for (int y = 0; y < 2; y++) {

			if (y == 0) {
				tablaLab.getDefaultCell().setBorderWidth(0);
				tablaLab.addCell(new Paragraph(" ", font));
				tablaLab.getDefaultCell().setBorderWidth(1);
				tablaLab.getDefaultCell().setColspan(4);
				tablaLab.getDefaultCell().setBorderWidthBottom(0);
				tablaLab.addCell(new Paragraph("LABORATORIO", font));
				tablaLab.getDefaultCell().setBorderWidthBottom(1);
				tablaLab.getDefaultCell().setColspan(1);
			} else {
				tablaLab.getDefaultCell().setBorderWidthBottom(0);
				tablaLab.getDefaultCell().setBorderWidth(0);
				tablaLab.addCell(new Paragraph(" ", font));
				tablaLab.getDefaultCell().setBorderWidthBottom(1);
				tablaLab.getDefaultCell().setBorderWidth(1);
				tablaLab.getDefaultCell().setColspan(4);
				tablaLab.getDefaultCell().setBorderWidthTop(0);
				tablaLab.addCell(new Paragraph("Y GABINETE\n\n", font));
				tablaLab.getDefaultCell().setBorderWidthTop(1);
				tablaLab.getDefaultCell().setColspan(1);
			}

			for (int i = 0; i < 24; i++) {

				if (n > 24)
					n = 1;
				if (y == 0)
					tablaLab.addCell(new Paragraph("" + n++, font));
				else {

					if (vectorLabGabinete[i + 1] != null) {
						// para pintar de acuerdo al turno
						if (vectorLabGabinete[i + 1].indexOf("@m") != -1)
							tablaLab.addCell(new Paragraph(
									vectorLabGabinete[i + 1].substring(2),
									fontAzul));
						else if (vectorLabGabinete[i + 1].indexOf("@t") != -1)
							tablaLab.addCell(new Paragraph(
									vectorLabGabinete[i + 1].substring(2),
									fontVerde));
						else if (vectorLabGabinete[i + 1].indexOf("@n") != -1)
							tablaLab.addCell(new Paragraph(
									vectorLabGabinete[i + 1].substring(2),
									fontRojo));
						else
							tablaLab.addCell(new Paragraph(
									vectorLabGabinete[i + 1], font));
					} else
						tablaLab.addCell("");

				}
			}

			if (y == 0) {
				tablaLab.getDefaultCell().setBorderWidthBottom(0);
				tablaLab.getDefaultCell().setBorderWidth(0);
				tablaLab.addCell(new Paragraph(" ", font));
				tablaLab.getDefaultCell().setBorderWidthBottom(1);
			} else {
				tablaLab.getDefaultCell().setBorderWidthTop(0);
				tablaLab.getDefaultCell().setBorderWidthBottom(0);
				tablaLab.getDefaultCell().setBorderWidth(0);

				tablaLab.addCell(new Paragraph("", font));
				tablaLab.getDefaultCell().setBorderWidthBottom(1);
			}
		}

		PdfPTable tablaTitleSignos = new PdfPTable(1);
		tablaTitleSignos.getDefaultCell().setBorder(0);
		tablaTitleSignos.getDefaultCell().setHorizontalAlignment(
				Element.ALIGN_CENTER);
		tablaTitleSignos.addCell(new Paragraph("SIGNOS VITALES", font));

		float signosWidth[] = { 5, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w,
				w, w, w, w, w, w, w, w, w, w, w };// 27
		PdfPTable signos = new PdfPTable(signosWidth);
		signos.getDefaultCell().setBorderWidth(1);

		for (int row = 0; row < matrixDatos.length; row++) {
			for (int col = 0; col < matrixDatos[row].length; col++) {
				// Hacemos un colspan para la frecuencia respiratoria
				// if (row == 15) {
				if (col >= 0 && col <= 2) {
					if (col == 2) {
						signos.getDefaultCell().setColspan(3);
						signos.getDefaultCell().setHorizontalAlignment(
								Element.ALIGN_LEFT);
						signos
								.addCell(new Paragraph(matrixDatos[row][0],
										font));
						signos.getDefaultCell().setColspan(1);
						signos.getDefaultCell().setHorizontalAlignment(
								Element.ALIGN_CENTER);
					}
				} else if (matrixDatos[row][col] != null) {
					// contiene color azul o rojo y tiene varios
					if (matrixDatos[row][col].contains("a")
							|| matrixDatos[row][col].contains("r"))
						signos.addCell(this.colorSignos(matrixDatos[row][col]));
					else if (matrixDatos[row][col].indexOf("@m") != -1)
						signos.addCell(new Paragraph(matrixDatos[row][col]
								.substring(2), fontAzul));
					else if (matrixDatos[row][col].indexOf("@t") != -1)
						signos.addCell(new Paragraph(matrixDatos[row][col]
								.substring(2), fontVerde));
					else if (matrixDatos[row][col].indexOf("@n") != -1)
						signos.addCell(new Paragraph(matrixDatos[row][col]
								.substring(2), fontRojo));
					else
						signos.addCell(new Paragraph(matrixDatos[row][col],
								font));
				} else
					signos.addCell("");
			}
		}

		tablaLab.addCell(new Paragraph(" ", font));
		tablaLab.addCell(tablaTitleSignos);
		tablaLab.getDefaultCell().setColspan(27);
		tablaLab.addCell(signos);
		tablaLab.addCell(new Paragraph(" ", font));
		laboratorio.addCell(tablaLab);

		// Agregamos el peso y la talla
		PdfPTable tablaPesoTalla = new PdfPTable(new float[] { 4, 4, 4, 4 });
		tablaPesoTalla.getDefaultCell().setBorderWidth(0);
		tablaPesoTalla.getDefaultCell().setHorizontalAlignment(1);
		tablaPesoTalla.addCell(new Paragraph("PESO:", font));
		tablaPesoTalla
				.addCell(new Paragraph(peso.toString() + "Kg.", fontAzul));
		tablaPesoTalla.addCell(new Paragraph("TALLA:", font));
		tablaPesoTalla
				.addCell(new Paragraph(talla.toString() + "m.", fontAzul));

		laboratorio.addCell(tablaPesoTalla);

		return laboratorio;
	}

	private PdfPTable escalaDolor(List<RegistroHojaEnfermeria> escalaDolor) throws MalformedURLException, IOException,	DocumentException {
		PdfPTable principal = new PdfPTable(new float[] { 2.5f, 3, 3, 3, 3, 3,
				3, 3, 3, 3, 3, 3, 2.5f });
		String[] imagenes = { "Dolor0.png", "DolorOtro.png", "Dolor2.png",
				"DolorOtro.png", "Dolor4.png", "DolorOtro.png", "Dolor6.png",
				"DolorOtro.png", "Dolor8.png", "DolorOtro.png", "Dolor10.png" };

		principal.getDefaultCell().setBorderWidth(0);
		principal.getDefaultCell().setHorizontalAlignment(1);

		principal.addCell("");
		int index = 0;
		for (String imagen : imagenes) {
			principal.addCell(new Paragraph((index++) + "", font));
		}
		principal.addCell("");

		principal.addCell(new Paragraph("SIN DOLOR", font));
		//String path = PropertyReader.readProperty("inr.saihweb.context");

		// Cargamos las imagenes
		for (String imagen : imagenes) {
			String logoPath = "web-app/images/escaladolor/" + imagen;
			Image img = Image.getInstance(logoPath);
			principal.addCell(img);
		}

		PdfPTable[] tablas = { new PdfPTable(1), new PdfPTable(1),
				new PdfPTable(1), new PdfPTable(1), new PdfPTable(1),
				new PdfPTable(1), new PdfPTable(1), new PdfPTable(1),
				new PdfPTable(1), new PdfPTable(1), new PdfPTable(1) };

		principal.addCell(new Paragraph("PEOR DOLOR", font));

		// Pintamos las x's
		principal.addCell("");

		for (RegistroHojaEnfermeria registro : escalaDolor) {
			String turno = turno(registro.getHoraregistrodiagva());

			Integer escala = Integer.parseInt(registro.getProcedimiento()
					.getDescripcion().trim());

			tablas[escala].getDefaultCell().setHorizontalAlignment(1);
			tablas[escala].getDefaultCell().setBorderWidth(0);

			// Lo agregamos la tabla que pertenece
			if (turno.equals("@m"))
				tablas[escala].addCell(new Paragraph("X", fontAzul));

			if (turno.equals("@t"))
				tablas[escala].addCell(new Paragraph("X", fontVerde));

			if (turno.equals("@n"))
				tablas[escala].addCell(new Paragraph("X", fontRojo));

		}

		// Agregamos las celdas a la tabla principal
		for (PdfPTable tabla : tablas)
			principal.addCell(tabla);

		principal.addCell("");

		return principal;

	}

	/******
	 * La tablade las dietas
	 * 
	 * @param dietas
	 * @return
	 */
	private PdfPTable dieta(List<RegistroHojaEnfermeria> dietas) {
		PdfPTable principal = new PdfPTable(new float[] { 2, 40, 40, 40, 40 });

		// principal.getDefaultCell().setBorderWidth(0);
		principal.addCell(new Paragraph("DIETA", font));
		// principal.getDefaultCell().setBorderWidth(1);

		for (RegistroHojaEnfermeria dieta : dietas) {

			String turno = turno(dieta.getHoraregistrodiagva());

			if (turno.equals("@m") || turno.equals("@o"))
				principal.addCell(new Paragraph(dieta.getOtro(), fontAzul));

			if (turno.equals("@t"))
				principal.addCell(new Paragraph(dieta.getOtro(), fontVerde));

			if (turno.equals("@n"))
				principal.addCell(new Paragraph(dieta.getOtro(), fontRojo));
		}

		return principal;

	}

	private Liquido getIngresoByDescripcion(
			List<Liquido> ingresos, String descripcion) {
		Liquido result = null;

		for (Liquido ingreso : ingresos) {
			if (ingreso.getDescripcion().trim().equals(descripcion.trim())) {
				result = ingreso;
			}
		}

		return result;

	}

	/***
	 * @param ingresos
	 * @return
	 */
	private PdfPTable ingresos(Long idHoja, List<Liquido> ingresos) {

		/* Los cabeceras y las horas */
		String[] horasIngresoEgreso = { "8", "9", "10", "11", "12", "13", "14",
				"TP", "15", "16", "17", "18", "19", "20", "TP", "21", "22",
				"23", "24", "1", "2", "3", "4", "5", "6", "7", "TP" };
		Integer[] columnas = { 0, 20, 21, 22, 23, 24, 25, 26, 1, 2, 3, 4, 5, 6,
				7, 9, 10, 11, 12, 13, 14, 16, 17, 18, 19 };

		String[][] matrixDatos = new String[1 + ingresos.size()][28];

		int fila = 1;

		Float totalMatutino = 0f;
		Float totalVespertino = 0f;
		Float totalNocturno = 0f;

		// Etiqueta de ingresos
		matrixDatos[0][0] = "Soluciones/Hora";

		// Cargamos las etiquetas de las horas
		for (int cabecera = 0; cabecera < horasIngresoEgreso.length; cabecera++) {
			matrixDatos[0][cabecera + 1] = horasIngresoEgreso[cabecera];
		}

		// Cargamos los ingresos
		for (Liquido ingreso : ingresos) {
			// Ponemos la descripcion
			matrixDatos[fila][0] = ingreso.getDescripcion();

			// Limpiamos
			ingreso.setTotalMatutino(0f);
			ingreso.setTotalVespertino(0f);
			ingreso.setTotalNocturno(0f);		
			 
			List<RegistroIngresoEgreso> subregistros  = service.consultarDetalleIngreso(idHoja,ingreso.getDescripcion());

			for (RegistroIngresoEgreso registro : subregistros) {

				// Obtenemos el turno y lo concatenamos
				String turno = turno(registro.getHora());

				// Cargamos los datos de los ingresos
				matrixDatos[fila][columnas[registro.getHora()]] = turno
						+ registro.getTotalingresar();

				// Calculamos los totales parciales para los turnos
				if (turno.equals("@m")) {
					if (Util.isFloatNumber(registro.getTotalingresar()))
						ingreso.setTotalMatutino(ingreso.getTotalMatutino()
								+ Float.parseFloat(registro.getTotalingresar()
										.trim()));
				}
				if (turno.equals("@t")) {
					if (Util.isFloatNumber(registro.getTotalingresar()))
						ingreso.setTotalVespertino(ingreso.getTotalVespertino()
								+ Float.parseFloat(registro.getTotalingresar()
										.trim()));
				}
				if (turno.equals("@n")) {
					if (Util.isFloatNumber(registro.getTotalingresar()))
						ingreso.setTotalNocturno(ingreso.getTotalNocturno()
								+ Float.parseFloat(registro.getTotalingresar()
										.trim()));
				}

			}

			// Pintamos los totales parciales m , v , n
			matrixDatos[fila][8] = "@m" + ingreso.getTotalMatutino().toString();
			matrixDatos[fila][15] = "@t"
					+ ingreso.getTotalVespertino().toString();
			matrixDatos[fila][27] = "@n"
					+ ingreso.getTotalNocturno().toString();

			totalMatutino += ingreso.getTotalMatutino();
			totalVespertino += ingreso.getTotalVespertino();
			totalNocturno += ingreso.getTotalNocturno();

			++fila;

		}

		float w = 2.0F;

		float ingresosWidth[] = { 10, w, w, w, w, w, w, w, w, w, w, w, w, w, w,
				w, w, w, w, w, w, w, w, w, w, w, w, w };
		PdfPTable tablaIngresos = new PdfPTable(ingresosWidth);
		tablaIngresos.getDefaultCell().setBorderWidth(1);

		for (int row = 0; row < matrixDatos.length; row++) {
			for (int col = 0; col < matrixDatos[row].length; col++) {
				if (matrixDatos[row][col] != null) {
					if (col != 0) { /* No tome en cuenta las descripciones */
						tablaIngresos.getDefaultCell()
								.setHorizontalAlignment(2);
						if (matrixDatos[row][col].indexOf("@m") != -1)
							tablaIngresos.addCell(new Paragraph(
									matrixDatos[row][col].substring(2),
									fontAzul));
						else if (matrixDatos[row][col].indexOf("@t") != -1)
							tablaIngresos.addCell(new Paragraph(
									matrixDatos[row][col].substring(2),
									fontVerde));
						else if (matrixDatos[row][col].indexOf("@n") != -1)
							tablaIngresos.addCell(new Paragraph(
									matrixDatos[row][col].substring(2),
									fontRojo));
						else
							tablaIngresos.addCell(new Paragraph(
									matrixDatos[row][col], font));
					} else {
						tablaIngresos.getDefaultCell()
								.setHorizontalAlignment(0);

						PdfPTable tablaFaltante = new PdfPTable(3);
						tablaFaltante.getDefaultCell().setColspan(3);
						tablaFaltante.addCell(new Paragraph(
								matrixDatos[row][col], font));

						if (!matrixDatos[row][col].equals("Soluciones/Hora")) {
							Liquido ingreso = getIngresoByDescripcion(
									ingresos, matrixDatos[row][col]);
							tablaFaltante.getDefaultCell().setColspan(1);
							tablaFaltante.addCell(new Paragraph(
									ingreso.getFxpM() == null ? "0" : ingreso.getFxpM(),fontAzul));
							
							tablaFaltante.addCell(new Paragraph(
									ingreso.getFxpV() == null ? "0" : ingreso.getFxpV(),fontVerde));
							
							tablaFaltante.addCell(new Paragraph(
									ingreso.getFxpN() == null ? "0" : ingreso.getFxpN(),fontRojo));							

							tablaIngresos.addCell(tablaFaltante);
						} else {
							PdfPTable tablaCabecera = new PdfPTable(3);
							tablaCabecera.getDefaultCell().setColspan(3);
							tablaCabecera.addCell(new Paragraph(
									"Soluciones/Hora", font));
							tablaCabecera.getDefaultCell().setColspan(1);
							tablaCabecera.addCell(new Paragraph("FxP M",
									fontAzul));
							tablaCabecera.addCell(new Paragraph("FxP V",
									fontVerde));
							tablaCabecera.addCell(new Paragraph("FxP N",
									fontRojo));
							tablaIngresos.addCell(tablaCabecera);
						}
					}

				} else
					tablaIngresos.addCell("");
			}
		}

		tablaIngresos.getDefaultCell().setHorizontalAlignment(0);
		tablaIngresos.getDefaultCell().setColspan(1);
		tablaIngresos.addCell(new Paragraph("INGRESOS PARCIALES", font));

		tablaIngresos.getDefaultCell().setColspan(5);
		tablaIngresos.addCell("");

		tablaIngresos.getDefaultCell().setColspan(1);
		tablaIngresos.addCell(new Paragraph("TM", font));

		tablaIngresos.getDefaultCell().setColspan(2);
		tablaIngresos.getDefaultCell().setHorizontalAlignment(2);
		tablaIngresos
				.addCell(new Paragraph(totalMatutino.toString(), fontAzul));

		tablaIngresos.getDefaultCell().setHorizontalAlignment(0);
		tablaIngresos.getDefaultCell().setColspan(4);
		tablaIngresos.addCell("");

		tablaIngresos.getDefaultCell().setColspan(1);
		tablaIngresos.addCell(new Paragraph("TV", font));

		tablaIngresos.getDefaultCell().setColspan(2);
		tablaIngresos.getDefaultCell().setHorizontalAlignment(2);
		tablaIngresos.addCell(new Paragraph(totalVespertino.toString(),
				fontVerde));

		tablaIngresos.getDefaultCell().setHorizontalAlignment(0);
		tablaIngresos.getDefaultCell().setColspan(9);
		tablaIngresos.addCell("");

		tablaIngresos.getDefaultCell().setColspan(1);
		tablaIngresos.addCell(new Paragraph("TN", font));

		tablaIngresos.getDefaultCell().setColspan(2);
		tablaIngresos.getDefaultCell().setHorizontalAlignment(2);
		tablaIngresos
				.addCell(new Paragraph(totalNocturno.toString(), fontRojo));

		PdfPTable principal = new PdfPTable(new float[] { 1, 50, 1 });

		PdfPTable tablaTitleIngresos = new PdfPTable(1);
		tablaTitleIngresos.getDefaultCell().setBorder(0);
		tablaTitleIngresos.getDefaultCell().setHorizontalAlignment(
				Element.ALIGN_CENTER);
		tablaTitleIngresos.addCell(new Paragraph("INGRESOS", font));

		PdfPTable tablaTotalIgresos = new PdfPTable(1);
		tablaTotalIgresos.getDefaultCell().setBorder(0);
		tablaTotalIgresos.getDefaultCell().setHorizontalAlignment(
				Element.ALIGN_CENTER);
		tablaTotalIgresos.addCell(new Paragraph("TOTAL", font));

		// Sumamos todos los totales
		totalTotalIngresos = totalMatutino + totalVespertino + totalNocturno;

		tablaTotalIgresos.addCell(new Paragraph(totalTotalIngresos + "",
				fontRojo));

		principal.getDefaultCell().setBorderWidth(0);
		principal.addCell(tablaTitleIngresos);
		principal.addCell(tablaIngresos);
		principal.addCell(tablaTotalIgresos);

		return principal;

	}

	private PdfPTable egresos(Long idHoja, List<Liquido> egresos) {

		/* Los cabeceras y las horas */
		String[] horasIngresoEgreso = { "8", "9", "10", "11", "12", "13", "14",
				"TP", "15", "16", "17", "18", "19", "20", "TP", "21", "22",
				"23", "24", "1", "2", "3", "4", "5", "6", "7", "TP" };

		Integer[] columnas = { 0, 20, 21, 22, 23, 24, 25, 26, 1, 2, 3, 4, 5, 6,
				7, 9, 10, 11, 12, 13, 14, 16, 17, 18, 19 };

		String[][] matrixDatos = new String[9][28];

		// Etiqueta de ingresos
		matrixDatos[0][0] = "Soluciones/Hora";

		Float totalMatutino = 0f;
		Float totalVespertino = 0f;
		Float totalNocturno = 0f;

		Float totalTotalEgresos = 0f;

		// Cargamos las etiquetas de las horas
		for (int cabecera = 0; cabecera < horasIngresoEgreso.length; cabecera++) {
			matrixDatos[0][cabecera + 1] = horasIngresoEgreso[cabecera];
		}

		int fila = 1;

		// Cargamos los egresos
		for (Liquido egreso : egresos) {

			matrixDatos[fila][0] = egreso.getDescripcion();

			// Limpiamos
			egreso.setTotalMatutino(0f);
			egreso.setTotalVespertino(0f);
			egreso.setTotalNocturno(0f);			
			 
			List<RegistroIngresoEgreso> subregistros  = service.consultarDetalleEgreso(idHoja,egreso.getDescripcion());

			for (RegistroIngresoEgreso registro : subregistros) {

				// Obtenemos el turno y lo concatenamos
				String turno = turno(registro.getHora());

				String valor = registro.getTotalingresar().trim();

				if (egreso.getDescripcion().equals("Vomito")
						|| egreso.getDescripcion().equals("Diuresis")) {
					if (registro.getTotalingresar().trim()
							.equals("No presenta"))
						valor = "_/";
					else
						valor = registro.getTotalingresar();
				}

				matrixDatos[fila][columnas[registro.getHora()]] = turno + valor;

				// Calculamos los totales parciales para los turnos
				if (turno.equals("@m")) {
					if (Util.isFloatNumber(valor))
						egreso.setTotalMatutino(egreso.getTotalMatutino()
								+ Float.parseFloat(valor));
				}
				if (turno.equals("@t")) {
					if (Util.isFloatNumber(valor))
						egreso.setTotalVespertino(egreso.getTotalVespertino()
								+ Float.parseFloat(valor));
				}
				if (turno.equals("@n")) {
					if (Util.isFloatNumber(valor))
						egreso.setTotalNocturno(egreso.getTotalNocturno()
								+ Float.parseFloat(valor));
				}
			}

			// Pintamos los totales parciales m , v , n
			matrixDatos[fila][8] = "@m" + egreso.getTotalMatutino().toString();
			matrixDatos[fila][15] = "@t"
					+ egreso.getTotalVespertino().toString();
			matrixDatos[fila][27] = "@n" + egreso.getTotalNocturno().toString();

			totalMatutino += egreso.getTotalMatutino();
			totalVespertino += egreso.getTotalVespertino();
			totalNocturno += egreso.getTotalNocturno();

			++fila;
		}

		float w = 2.0F;

		float egresosWidth[] = { 10, w, w, w, w, w, w, w, w, w, w, w, w, w, w,
				w, w, w, w, w, w, w, w, w, w, w, w, w };
		PdfPTable tablaEgresos = new PdfPTable(egresosWidth);
		tablaEgresos.getDefaultCell().setBorderWidth(1);

		for (int row = 0; row < matrixDatos.length; row++) {
			for (int col = 0; col < matrixDatos[row].length; col++) {
				if (matrixDatos[row][col] != null) {
					if (col != 0) { /* No tome en cuenta las descripciones */

						tablaEgresos.getDefaultCell().setHorizontalAlignment(2);

						if (matrixDatos[row][col].indexOf("@m") != -1)
							tablaEgresos.addCell(new Paragraph(
									matrixDatos[row][col].substring(2),
									fontAzul));
						else if (matrixDatos[row][col].indexOf("@t") != -1)
							tablaEgresos.addCell(new Paragraph(
									matrixDatos[row][col].substring(2),
									fontVerde));
						else if (matrixDatos[row][col].indexOf("@n") != -1)
							tablaEgresos.addCell(new Paragraph(
									matrixDatos[row][col].substring(2),
									fontRojo));
						else
							tablaEgresos.addCell(new Paragraph(
									matrixDatos[row][col], font));
					} else {
						tablaEgresos.getDefaultCell().setHorizontalAlignment(0);
						tablaEgresos.addCell(new Paragraph(
								matrixDatos[row][col], font));
					}

				} else
					tablaEgresos.addCell("");

			}
		}

		// Sumamos todos los totales
		totalTotalEgresos = totalMatutino + totalVespertino + totalNocturno;

		tablaEgresos.getDefaultCell().setColspan(1);
		tablaEgresos.addCell(new Paragraph("EGRESOS PARCIALES", font));

		tablaEgresos.getDefaultCell().setColspan(6);
		tablaEgresos.addCell(new Paragraph("TM", font));

		tablaEgresos.getDefaultCell().setColspan(2);
		tablaEgresos.getDefaultCell().setHorizontalAlignment(2);
		tablaEgresos.addCell(new Paragraph(totalMatutino.toString(), fontAzul));

		tablaEgresos.getDefaultCell().setColspan(5);
		tablaEgresos.addCell(new Paragraph("TV", font));

		tablaEgresos.getDefaultCell().setColspan(2);
		tablaEgresos.getDefaultCell().setHorizontalAlignment(2);
		tablaEgresos.addCell(new Paragraph(totalVespertino.toString(),
				fontVerde));

		tablaEgresos.getDefaultCell().setColspan(10);
		tablaEgresos.addCell(new Paragraph("TN", font));

		tablaEgresos.getDefaultCell().setColspan(2);
		tablaEgresos.getDefaultCell().setHorizontalAlignment(2);
		tablaEgresos.addCell(new Paragraph(totalNocturno.toString(), fontRojo));

		// Balance total de 24 horas
		tablaEgresos.getDefaultCell().setColspan(27);
		tablaEgresos.getDefaultCell().setHorizontalAlignment(0);
		tablaEgresos.addCell(new Paragraph("BALANCE TOTAL DE 24 HORAS", font));

		tablaEgresos.getDefaultCell().setColspan(1);
		tablaEgresos.getDefaultCell().setHorizontalAlignment(2);
		tablaEgresos.addCell(new Paragraph(
				(totalTotalIngresos - totalTotalEgresos) + "", font));

		PdfPTable principal = new PdfPTable(new float[] { 1, 50, 1 });

		PdfPTable tablaTitleEgresos = new PdfPTable(1);
		tablaTitleEgresos.getDefaultCell().setBorder(0);
		tablaTitleEgresos.getDefaultCell().setHorizontalAlignment(
				Element.ALIGN_CENTER);
		tablaTitleEgresos.addCell(new Paragraph("EGRESOS", font));

		PdfPTable tablaTotalEgresos = new PdfPTable(1);
		tablaTotalEgresos.getDefaultCell().setBorder(0);
		tablaTotalEgresos.getDefaultCell().setHorizontalAlignment(
				Element.ALIGN_CENTER);
		tablaTotalEgresos.addCell(new Paragraph("TOTAL", font));

		tablaTotalEgresos.addCell(new Paragraph(totalTotalEgresos + "",
				fontRojo));

		principal.getDefaultCell().setBorderWidth(0);
		principal.addCell(tablaTitleEgresos);
		principal.addCell(tablaEgresos);
		principal.addCell(tablaTotalEgresos);

		return principal;

	}

	@SuppressWarnings("unchecked")
	private PdfPTable valoracionEnfermeria(Long idHoja,
			List<CatRubroNotaEnfermeria> listaRubrosValoracionEnfermeria,
			List<RegistroHojaEnfermeria> requisitos, Religion religion) {

		PdfPTable principal = new PdfPTable(new float[] { 50, 50 });
		principal.getDefaultCell().setBorderWidth(0);
		principal.getDefaultCell().setColspan(2);
		principal.getDefaultCell().setHorizontalAlignment(1);
		principal.addCell(new Paragraph("VALORACION DE ENFERMERIA", fuente));

		// Ponemos de senuelo
		//listaRubrosValoracionEnfermeria.addLast(new CatRubroNotaEnfermeria(79, "Requisitos"));

		for (CatRubroNotaEnfermeria rubro : listaRubrosValoracionEnfermeria) {

			PdfPTable tabla = null;

			Long idRubro = rubro.getId();

			if (idRubro != R_REQUISITOS) {
				
				List<RegistroHojaEnfermeria> registros = service.consultarCheckRubro(idHoja, idRubro);

				// Cargamos las tablas vacias
				//if (registros.size() == 0) {
					//registros = MultitableController.cargarProcedimientosCheckBoxs(idRubro);
				//}

				// Salte la opcion de seleccione
				if (registros.size() == 0)
					continue;

				tabla = new PdfPTable(new float[] { 5, 0.5f, 0.5f, 0.5f });
				// Agregamos el encabezado
				tabla.getDefaultCell().setColspan(4);
				tabla.addCell(new Paragraph(rubro.getDescripcion(), fuente));

				tabla.getDefaultCell().setColspan(1);
				tabla.addCell(new Paragraph("TURNO", font));
				tabla.getDefaultCell().setHorizontalAlignment(1);
				tabla.addCell(new Paragraph("M", font));
				tabla.addCell(new Paragraph("V", font));
				tabla.addCell(new Paragraph("N", font));

				for (RegistroHojaEnfermeria registro : registros) {
					tabla.getDefaultCell().setHorizontalAlignment(0);
					tabla.getDefaultCell().setColspan(1);

					if (registro.getRegistrodiagvalora() != null) {

						tabla
								.addCell(new Paragraph(registro.getProcedimiento()
										.getDescripcion(), data));

						// Matutino
						if (registro.getRegistrodiagvalora()
								.charAt(0) == '1') {
							tabla.getDefaultCell().setHorizontalAlignment(1);
							tabla.addCell(new Paragraph("x", fontAzul));
						} else
							tabla.addCell("");

						// Vespertino
						if (registro.getRegistrodiagvalora()
								.charAt(1) == '1') {
							tabla.getDefaultCell().setHorizontalAlignment(1);
							tabla.addCell(new Paragraph("x", fontVerde));
						} else
							tabla.addCell("");

						// Nocturno
						if (registro.getRegistrodiagvalora()
								.charAt(2) == '1') {
							tabla.getDefaultCell().setHorizontalAlignment(1);
							tabla.addCell(new Paragraph("x", fontRojo));
						} else
							tabla.addCell("");
					} else // Campo de otro, etapa de duelo, religion y
					// practicas reliogiosas
					{
						tabla.getDefaultCell().setColspan(1);
						tabla
								.addCell(new Paragraph(registro.getProcedimiento()
										.getDescripcion(), data));
						tabla.getDefaultCell().setColspan(3);
						
						if(registro.getProcedimiento().getDescripcion().equals("Religion")){
							tabla.addCell(new Paragraph(religion.getDescripcion(), data));
						}
						else{
							tabla.addCell(new Paragraph(registro.getOtro(), data));

						}
					}
				}
			} else {// Requisitos son dos campos
				tabla = new PdfPTable(new float[] { 10 });
				tabla.getDefaultCell();
				tabla.addCell(new Paragraph("REQUISITOS DE DESARROLLO", font));
				tabla.addCell(new Paragraph(
						requisitos.get(0).getOtro() != null ? requisitos.get(0)
								.getOtro().trim() : "", data));
				tabla.addCell(new Paragraph(
						"REQUISITOS DE DESVIACION DE LA SALUD", font));
				tabla.addCell(new Paragraph(
						requisitos.get(1).getOtro() != null ? requisitos.get(1)
								.getOtro().trim() : "", data));
			}

			principal.getDefaultCell().setBorderWidth(0);
			principal.getDefaultCell().setColspan(1);
			principal.addCell(tabla);
		}

		// Quitamos el senuelo
		//listaRubrosValoracionEnfermeria.removeLast();

		return principal;

	}

	@SuppressWarnings("unchecked")
	private PdfPTable diagnosticosIntervenciones(Long idHoja,
			List<CatRubroNotaEnfermeria> listaRubrosDiagIntervenciones) {
		PdfPTable principal = new PdfPTable(new float[] { 50, 50, 50 });

		principal.getDefaultCell().setBorderWidth(0);
		principal.getDefaultCell().setColspan(3);
		principal.getDefaultCell().setHorizontalAlignment(1);
		principal.addCell(new Paragraph(
				"DIAGNOSTICOS E INTERVENCIONES DE ENFERMERIA", fuente));

		int countTablas = 0;

		for (CatRubroNotaEnfermeria rubro : listaRubrosDiagIntervenciones) {

			++countTablas;

			//UtilService service;	//inyectar el servicio		
			List<RegistroHojaEnfermeria> registros = service.consultarCheckRubro(idHoja, rubro.getId());

			// Cargamos las tablas vacias
			/*if (registros.size() == 0) {
				registros = MultitableController
						.cargarProcedimientosCheckBoxs(Short.parseShort(rubro
								.getValue().toString()));
			}*/

			// Saltamos el seleccione
			if (registros.size() == 0)
				continue;

			PdfPTable tabla = new PdfPTable(new float[] { 5, 0.5f, 0.5f, 0.5f });
			// Agregamos el encabezado
			tabla.getDefaultCell().setColspan(4);
			tabla.addCell(new Paragraph(rubro.getDescripcion(), fuente));

			tabla.getDefaultCell().setColspan(1);
			tabla.addCell(new Paragraph("TURNO", font));
			tabla.getDefaultCell().setHorizontalAlignment(1);
			tabla.addCell(new Paragraph("M", font));
			tabla.addCell(new Paragraph("V", font));
			tabla.addCell(new Paragraph("N", font));

			// Falta poner celdas para los diagnosticos los multiplos de 3

			for (RegistroHojaEnfermeria registro : registros) {
				tabla.getDefaultCell().setHorizontalAlignment(0);
				tabla.getDefaultCell().setColspan(1);

				if (registro.getRegistrodiagvalora() != null) {

					tabla.addCell(new Paragraph(registro.getProcedimiento()
									.getDescripcion(), data));

					// Matutino
					if (registro.getRegistrodiagvalora()
							.charAt(0) == '1') {
						tabla.getDefaultCell().setHorizontalAlignment(1);
						tabla.addCell(new Paragraph("x", fontAzul));
					} else
						tabla.addCell("");

					// Vespertino
					if (registro.getRegistrodiagvalora()
							.charAt(1) == '1') {
						tabla.getDefaultCell().setHorizontalAlignment(1);
						tabla.addCell(new Paragraph("x", fontVerde));
					} else
						tabla.addCell("");

					// Nocturno
					if (registro.getRegistrodiagvalora()
							.charAt(2) == '1') {
						tabla.getDefaultCell().setHorizontalAlignment(1);
						tabla.addCell(new Paragraph("x", fontRojo));
					} else
						tabla.addCell("");
					
				} else{ // Campo de otro				
					tabla.getDefaultCell().setColspan(1);
					tabla.addCell(new Paragraph(registro.getProcedimiento()
									.getDescripcion(), data));
					tabla.getDefaultCell().setColspan(3);				
					
					tabla.addCell(new Paragraph(registro.getOtro(), data));
					
				}
			}
			
			principal.getDefaultCell().setBorderWidth(0);
			principal.getDefaultCell().setColspan(1);
			principal.addCell(tabla);

			

			// Ultima tabla
			if (listaRubrosDiagIntervenciones.size() == countTablas) {

				principal.addCell("");
				principal.addCell("");
			}

		}

		return principal;
	}

	@SuppressWarnings("unchecked")
	private PdfPTable indicadoresCalidadOptionCheck(Long idHoja,
			List<CatRubroNotaEnfermeria> listaRubrosIndicadoresCalidad,
			IndicadorCalidad[] indicadores,
			RegistroHojaEnfermeria[] escalaMaddox) {

		PdfPTable principal = new PdfPTable(new float[] { 50, 50, 50, 50 });

		principal.getDefaultCell().setBorderWidth(0);
		principal.getDefaultCell().setColspan(4);
		principal.getDefaultCell().setHorizontalAlignment(1);
		principal.addCell(new Paragraph("INDICADORES DE CALIDAD", fuente));

		principal.getDefaultCell().setBorderWidth(0);
		principal.getDefaultCell().setColspan(1);

		PdfPTable tabla = null;

		rubros: for (CatRubroNotaEnfermeria rubro : listaRubrosIndicadoresCalidad) {

			Short idRubro = rubro.getId().shortValue();

			List<RegistroHojaEnfermeria> registros;

			switch (idRubro) {
			
			case R_VENOCLISIS_INSTALADA:				
			case R_SONDA_VESICAL_INSTALADA:
			case R_PREVENSION_ULCERA_PRESION:
				
				registros = service.consultarCheckRubro(idHoja, rubro.getId());

				// Cargamos las tablas vacias
				/*if (registros.size() == 0) {
					registros = MultitableController.cargarProcedimientosCheckBoxs(Short.parseShort(rubro.getValue().toString()));
				}*/

				tabla = new PdfPTable(new float[] { 5, 1f, 1f });

				// Agregamos el encabezado
				tabla.getDefaultCell().setColspan(3);
				tabla.addCell(new Paragraph(rubro.getDescripcion(), fuente));

				if (idRubro == R_VENOCLISIS_INSTALADA) {// Veniclosis Instalada
					tabla.getDefaultCell().setColspan(3);
					tabla
							.addCell(new Paragraph(
									"Fecha de instalacion: "
											+ (indicadores[0]
													.getFechaInstalacion() == null ? ""
													: indicadores[0].getFechaInstalacion()),
									font));
					
					tabla.getDefaultCell().setColspan(1);
					
					tabla
							.addCell(new Paragraph(
									"Dias Consec: "
											+ (indicadores[0]
													.getDiasConsecutivos() == null ? ""
													: indicadores[0]
															.getDiasConsecutivos()),
									font));
					
					tabla.getDefaultCell().setColspan(2);
					tabla.addCell(new Paragraph("Calibre: "
							+ (indicadores[0].getCalibre() == null ? ""
									: indicadores[0].getCalibre()), font));
					
					
					tabla.getDefaultCell().setColspan(1);
					tabla.addCell(new Paragraph("Factores de Riesgo", font));
					tabla.addCell(new Paragraph("SI", font));
					tabla.addCell(new Paragraph("NO", font));

				} else if (idRubro == R_PREVENSION_CAIDAS
						|| idRubro == R_PREVENSION_ULCERA_PRESION) {// Prevencion
					// de
					// Caidas o
					// Prevencion
					// Ulceras por
					// Presion
					tabla.getDefaultCell().setColspan(1);
					tabla.addCell(new Paragraph("Factores de Riesgo", font));
					tabla.addCell(new Paragraph("SI", font));
					tabla.addCell(new Paragraph("NO", font));

				} else if (idRubro == R_SONDA_VESICAL_INSTALADA) {// Sonda
					// Vesical
					// Instalada
					tabla.getDefaultCell().setColspan(3);
					tabla
							.addCell(new Paragraph(
									"Fecha de instalacion: "
											+ (indicadores[1]
													.getFechaInstalacion() == null ? ""
													: indicadores[1]
																	.getFechaInstalacion()),
									font));

					tabla.getDefaultCell().setColspan(1);
					tabla
							.addCell(new Paragraph(
									"Dias Conse: "
											+ (indicadores[1]
													.getDiasConsecutivos() == null ? ""
													: indicadores[1]
															.getDiasConsecutivos()),
									font));
					tabla.getDefaultCell().setColspan(2);
					tabla.addCell(new Paragraph("Material: "
							+ (indicadores[1].getMaterial() == null ? ""
									: indicadores[1].getMaterial()), font));

					tabla.getDefaultCell().setColspan(1);
					tabla.addCell(new Paragraph("Calibre: "
							+ (indicadores[1].getCalibre() == null ? ""
									: indicadores[1].getCalibre()), font));
					tabla.getDefaultCell().setColspan(2);
					tabla.addCell(new Paragraph("Globo: "
							+ (indicadores[1].getGlobo() == null ? ""
									: indicadores[1].getGlobo()), font));

					tabla.getDefaultCell().setColspan(1);
					tabla.addCell(new Paragraph("Factores de Riesgo", font));
					tabla.addCell(new Paragraph("SI", font));
					tabla.addCell(new Paragraph("NO", font));

				}

				// Cargamos
				for (RegistroHojaEnfermeria registro : registros) {

					tabla.getDefaultCell().setColspan(1);
					tabla.addCell(new Paragraph(registro.getProcedimiento().getDescripcion(), data));

					if(registro.getRegistrodiagvalora() != null){
						if (registro.getRegistrodiagvalora().equals("SI"))
							tabla.addCell(new Paragraph("x", fontAzul));
						else
							tabla.addCell("");
						
						if(registro.getRegistrodiagvalora().equals("NO"))
							tabla.addCell(new Paragraph("x", fontAzul));
						else
							tabla.addCell("");
					}
					else{
					
						tabla.getDefaultCell().setColspan(2);
						tabla.addCell(new Paragraph(registro.getOtro(), data));
						tabla.getDefaultCell().setColspan(1);
					}
				}

				break;
			default:// Para las intervenciones

				if (idRubro == R_DIAGNOSTICO_ENFERMERIA
						|| idRubro == R_OBSERVACIONES
						|| idRubro == R_OTROS_DATOS || idRubro == SIN_SELECCION) // Otros
					// datos
					// que
					// no
					// interesan
					continue rubros;

				registros = service.consultarCheckRubro(idHoja, rubro.getId());

				// Cargamos las tablas vacias
				/*if (registros.size() == 0) {
					registros = MultitableController
							.cargarProcedimientosCheckBoxs(Short
									.parseShort(rubro.getValue().toString()));
				}*/

				tabla = new PdfPTable(new float[] { 5, 0.5f, 0.5f, 0.5f });
				// Agregamos el encabezado
				tabla.getDefaultCell().setColspan(4);
				tabla.addCell(new Paragraph(rubro.getDescripcion() ,fuente));

				tabla.getDefaultCell().setColspan(1);
				tabla.addCell(new Paragraph("", font));
				tabla.addCell(new Paragraph("M", font));
				tabla.addCell(new Paragraph("V", font));
				tabla.addCell(new Paragraph("N", font));

				int[][] primeraValoracion = getPrimeraValoracion(idHoja);
				int index = 0;

				for (RegistroHojaEnfermeria registro : registros) {
					tabla.getDefaultCell();
					tabla.addCell(new Paragraph(registro.getProcedimiento().getDescripcion(), data));

					if (registro.getRegistrodiagvalora() != null) {			
					
						//Matutino
						if (registro.getRegistrodiagvalora().charAt(0) == '1') {							
							tabla.addCell(new Paragraph("x", fontAzul));
						} 
						else 
							tabla.addCell("");						
	
						// Vespertino
						if ( registro.getRegistrodiagvalora().charAt(1) == '1') {							
							tabla.addCell(new Paragraph("x", fontVerde));
						} 
						else 
							tabla.addCell("");
						
	
						// Nocturno
						if (registro.getRegistrodiagvalora().charAt(2) == '1') {							
							tabla.addCell(new Paragraph("x", fontRojo));
						} 
						else 							
							tabla.addCell("");
						
					}
					else{
						
						if(registro.getProcedimiento().getDescripcion().trim().equals("Otro:")){
							tabla.getDefaultCell().setColspan(3);
							tabla.addCell(new Paragraph(registro.getOtro(), data));
							tabla.getDefaultCell().setColspan(1);
							
						}
						else
						{
							Font[] fonts = {fontAzul,fontVerde,fontRojo};
							
							for(int intTurno =0; intTurno <3; intTurno++ )
							{
								
								
								
								switch (idRubro) {
									case R_PREVENSION_CAIDAS:
										tabla.addCell(new Paragraph(
														primeraValoracion[intTurno][index] != 0 ? "x"
																: "", fonts[intTurno]));
										break;
									case R_INTERVENCIONES_ACCESO_VENOSO:
										if (registro.getProcedimiento()
												.getId() == P_ESCALA_MADDOX_GENERICO)
											tabla.addCell(new Paragraph(escalaMaddox[intTurno]
													.getOtro(), fontAzul));
										else
											tabla.addCell("");
										break;
									default:
										tabla.addCell("");
										
								}
							}
						}
				 }

					if (idRubro == R_PREVENSION_CAIDAS)
						++index;
				}

				if (idRubro == R_PREVENSION_CAIDAS) {
					tabla.getDefaultCell();
					tabla.addCell(new Paragraph("Calificacion total", data));
					tabla.getDefaultCell().setBackgroundColor(
							getFontPrevencionCaidas(primeraValoracion[0][5])
									.getColor());
					tabla.addCell(new Paragraph(primeraValoracion[0][5] + "",
							data));
					tabla.getDefaultCell().setBackgroundColor(
							getFontPrevencionCaidas(primeraValoracion[1][5])
									.getColor());
					tabla.addCell(new Paragraph(primeraValoracion[1][5] + "",
							data));
					tabla.getDefaultCell().setBackgroundColor(
							getFontPrevencionCaidas(primeraValoracion[2][5])
									.getColor());
					tabla.addCell(new Paragraph(primeraValoracion[2][5] + "",
							data));
					tabla.getDefaultCell().setBackgroundColor(Color.WHITE);
				}

			}

			principal.addCell(tabla);
		}

		return principal;

	}

	/**
	 *La primera valoracion asocada a la prevencion de caidas
	 * 
	 * @param idHoja
	 * @return
	 */
	private int[][] getPrimeraValoracion(Long idHoja) {

		int sumatoria[][] = this.valorPrevencionCaidas(idHoja);

		int[][] primeraValoracion = new int[3][6];

		for (int index = 8; index <= 14; index++) {
			if (sumatoria[index][5] != 0) {// sumatoria
				primeraValoracion[0] = sumatoria[index];
				break;
			}
		}

		for (int index = 15; index <= 20; index++) {
			if (sumatoria[index][5] != 0) {// sumatoria
				primeraValoracion[1] = sumatoria[index];
				break;
			}
		}

		for (int index = 21; index <= 24; index++) {
			if (sumatoria[index][5] != 0) {// sumatoria
				primeraValoracion[2] = sumatoria[index];
				break;
			}
		}

		if (primeraValoracion[2][5] == 0) {
			for (int index = 1; index <= 7; index++) {
				if (sumatoria[index][5] != 0) {// sumatoria
					primeraValoracion[2] = sumatoria[index];
					break;
				}
			}
		}

		return primeraValoracion;
	}

	private Font getFontPrevencionCaidas(int calif) {
		Font result = fontWhite;
		if (calif == 1)
			result = fontVerde;
		else if (calif >= 2 && calif <= 3)
			result = fontAmarillo;
		else if (calif >= 4 && calif <= 10)
			result = fontRojo;
		return result;
	}

	@SuppressWarnings("unchecked")
	private PdfPTable diagEnfermeriaObservaciones(
			RegistroHojaEnfermeria[] diagEnfermeriaObservaciones,
			HojaRegistroEnfermeria hoja) {

		PdfPTable principal = new PdfPTable(new float[] { 50, 50, 50 });
		principal.getDefaultCell().setBorderWidth(0);

		principal.getDefaultCell().setHorizontalAlignment(1);
		principal.addCell(new Paragraph("TM", font));
		principal.addCell(new Paragraph("TV", font));
		principal.addCell(new Paragraph("TN", font));

		principal.addCell(new Paragraph("Planeacion del alta", font));
		principal.addCell(new Paragraph("Planeacion del alta", font));
		principal.addCell(new Paragraph("Planeacion del alta", font));

		principal.getDefaultCell().setHorizontalAlignment(0);
		principal.addCell(new Paragraph(diagEnfermeriaObservaciones[0]
				.getOtro(), fontAzul));
		principal.addCell(new Paragraph(diagEnfermeriaObservaciones[1]
				.getOtro(), fontVerde));
		principal.addCell(new Paragraph(diagEnfermeriaObservaciones[2]
				.getOtro(), fontRojo));

		principal.getDefaultCell().setColspan(3);
		principal.addCell(new Paragraph("OBSERVACIONES", font));

		principal.getDefaultCell().setColspan(1);

		principal.addCell(new Paragraph(diagEnfermeriaObservaciones[3]
				.getOtro(), fontAzul));
		principal.addCell(new Paragraph(diagEnfermeriaObservaciones[4]
				.getOtro(), fontVerde));
		principal.addCell(new Paragraph(diagEnfermeriaObservaciones[5]
				.getOtro(), fontRojo));

		hoja.establecerTurnos();

		principal.getDefaultCell().setBorder(1);

		principal.addCell(firmas('u', hoja));
		principal.addCell(firmas('j', hoja));
		principal.addCell(firmas('s', hoja));

		return principal;
	}

	private PdfPTable firmas(char tipo, HojaRegistroEnfermeria hoja) {

		PdfPTable tabla = new PdfPTable(new float[] { 3, 3, 3 });
		tabla.getDefaultCell().setBorder(0);

		Usuario[] usuarios = new Usuario[12];

		String cabecera = "";

		switch (tipo) {
		case 'u':	
			
			usuarios[0] = hoja.getTurnoMatutino().getUsuario();
			usuarios[1] = hoja.getTurnoVespertino().getUsuario();
			usuarios[2] = hoja.getTurnoNocturno().getUsuario();
			
			//traslado1
			usuarios[3] = hoja.getTurnoMatutino().getTraslado1();
			usuarios[4] = hoja.getTurnoVespertino().getTraslado1();
			usuarios[5] = hoja.getTurnoNocturno().getTraslado1();
			
			//traslado2
			usuarios[6] = hoja.getTurnoMatutino().getTraslado2();
			usuarios[7] = hoja.getTurnoVespertino().getTraslado2();
			usuarios[8] = hoja.getTurnoNocturno().getTraslado2();
			
			//traslado3
			usuarios[9] = hoja.getTurnoMatutino().getTraslado3();
			usuarios[10] = hoja.getTurnoVespertino().getTraslado3();
			usuarios[11] = hoja.getTurnoNocturno().getTraslado3();			
			
			cabecera = "ENFERMERAS";
			break;
		case 'j':
			usuarios[0] = hoja.getTurnoMatutino().getJefe();
			usuarios[1] = hoja.getTurnoVespertino().getJefe();
			usuarios[2] = hoja.getTurnoNocturno().getJefe();
			cabecera = "JEFES";
			break;
			
		case 's':
			usuarios[0] = hoja.getTurnoMatutino().getSupervisor();
			usuarios[1] = hoja.getTurnoVespertino().getSupervisor();
			usuarios[2] = hoja.getTurnoNocturno().getSupervisor();
			cabecera = "SUPERVISORES";
			break;
		}

		tabla.getDefaultCell().setColspan(3);
		tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tabla.addCell(new Paragraph(cabecera, font));
		tabla.getDefaultCell().setColspan(1);
		tabla.addCell(new Paragraph("MATUTINO", fontAzul));
		tabla.addCell(new Paragraph("VESPERTINO", fontVerde));
		tabla.addCell(new Paragraph("NOCTURNO", fontRojo));

		for (Usuario tmp : usuarios) {
			if (tmp != null && tmp.getId() != null) {
				try {
					
					PdfPTable firma = new PdfPTable(1);
					firma.getDefaultCell().setBorder(0);
					firma.addCell(this.obtenerFirma(tmp));
					firma.addCell(new Paragraph(tmp.getNombre(), fontSmall));
					firma.addCell(new Paragraph(tmp.getCedula(), fontSmall));
					
					tabla.addCell(firma);
					
					
				} catch (Exception e) {
					tabla.addCell(new Paragraph("", font));
				}
			} else {
				tabla.addCell(new Paragraph("", font));
			}
		}
		
		return tabla;

	}

	private ArrayList<String> fusionarListas(
			List<String> lista1,
			List<String> lista2) {
		ArrayList<String> result = new ArrayList<String>(
				lista1);
		result.addAll(lista2);
		return result;
	}

	private PdfPTable medicamentos(Long idHoja, HojaRegistroEnfermeria model) {

		/* Los cabeceras y las horas */
		String[] horas = { "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23", "24", "1", "2", "3",
				"4", "5", "6", "7" };

		// 25
		Integer[] columnas = { 0, 18, 19, 20, 21, 22, 23, 24, 1, 2, 3, 4, 5, 6,
				7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };

		ArrayList<String> listaFusion = fusionarListas(model.getMedicamentos(), model.getEscalaOtros());

		// Mas dos por las dos cabeceras
		String[][] matrixDatos = new String[listaFusion.size() + 2][25];

		matrixDatos[0][0] = "MEDICAMENTOS";

		// Cargamos las etiquetas de las horas
		for (int cabecera = 0; cabecera < horas.length; cabecera++) {
			matrixDatos[0][cabecera + 1] = horas[cabecera];
		}

		int fila = 1;

		int filaEscala = 0;

		// Cargamos los egresos
		for (String descripcion : listaFusion) {

			// Cargamos el encabezado de la escala de glasgow
			if (descripcion.equals("Respuesta Motora")) {
				matrixDatos[fila][0] = "Escala de Glasgow";

				// La fila de la escala de glasgow
				filaEscala = fila;
				++fila;
			}

			matrixDatos[fila][0] = descripcion;
			
			List<RegistroIngresoEgreso> subregistros = null;
			
			if(model.getEscalaOtros().contains(descripcion)){
				subregistros  = service.consultarDetalleEscalaOtro(idHoja, descripcion);
			}
			else if(model.getMedicamentos().contains(descripcion)) {
				subregistros  = service.consultarDetalleMedicamento(idHoja, descripcion);
			}
			
			

			for (RegistroIngresoEgreso subRegistro : subregistros) {

				// Obtenemos el turno y lo concatenamos
				String turno = turno(subRegistro.getHora());

				String valor = subRegistro.getTotalingresar();

				// Hacemos las sumas para la escala de glsgow
				if (descripcion.equals("Respuesta Motora")
						|| descripcion.equals("Respuesta Ocular")
						|| descripcion.equals("Respuesta Verbal")) {

					Float suma = 0f;

					if (matrixDatos[filaEscala][columnas[subRegistro.getHora()]] != null) {
						if (Util
								.isFloatNumber(matrixDatos[filaEscala][columnas[subRegistro
										.getHora()]]))
							suma = Float
									.parseFloat(matrixDatos[filaEscala][columnas[subRegistro
											.getHora()]]);
					}

					if (Util.isFloatNumber(valor))
						matrixDatos[filaEscala][columnas[subRegistro.getHora()]] = suma
								+ Float
								.parseFloat(valor) + "";
				}

				matrixDatos[fila][columnas[subRegistro.getHora()]] = turno
						+ valor;
			}

			++fila;
		}

		float w = 2.0F;

		float medicamentosWidth[] = { 10, w, w, w, w, w, w, w, w, w, w, w, w,
				w, w, w, w, w, w, w, w, w, w, w, w };
		PdfPTable tablaMedicamentos = new PdfPTable(medicamentosWidth);
		tablaMedicamentos.getDefaultCell().setBorderWidth(1);

		for (int row = 0; row < matrixDatos.length; row++) {
			for (int col = 0; col < matrixDatos[row].length; col++) {
				if (matrixDatos[row][col] != null) {
					if (matrixDatos[row][col].equals("MEDICAMENTOS")
							|| matrixDatos[row][col]
									.equals("Escala de Glasgow")) {
						tablaMedicamentos.getDefaultCell()
								.setHorizontalAlignment(1);
						tablaMedicamentos.addCell(new Paragraph(
								matrixDatos[row][col], fuente));
					} else {
						tablaMedicamentos.getDefaultCell()
								.setHorizontalAlignment(0);

						if (col != 0) { /* No tome en cuenta las descripciones */
							if (matrixDatos[row][col].indexOf("@m") != -1)
								tablaMedicamentos.addCell(new Paragraph(
										matrixDatos[row][col].substring(2),
										fontAzul));
							else if (matrixDatos[row][col].indexOf("@t") != -1)
								tablaMedicamentos.addCell(new Paragraph(
										matrixDatos[row][col].substring(2),
										fontVerde));
							else if (matrixDatos[row][col].indexOf("@n") != -1)
								tablaMedicamentos.addCell(new Paragraph(
										matrixDatos[row][col].substring(2),
										fontRojo));
							else
								tablaMedicamentos.addCell(new Paragraph(
										matrixDatos[row][col], font));
						} else { // las descripciones
							tablaMedicamentos.addCell(new Paragraph(
									matrixDatos[row][col], font));
						}
					}
				} else {
					tablaMedicamentos.addCell("");
				}

			}
		}

		// ##################DOLOR (EVA###########################
		tablaMedicamentos.getDefaultCell().setHorizontalAlignment(1);
		tablaMedicamentos.addCell(new Paragraph("Dolor(EVA)", fuente));

		String[] dolor = new String[25];
		
		 
		List<RegistroHojaEnfermeria> registrosDolor = service.consultarEscalaDolor(model.getId());

		for (RegistroHojaEnfermeria registro : registrosDolor) {
			int hora = registro.getHoraregistrodiagva();
			String valor = registro.getProcedimiento().getDescripcion()
					.trim();
			dolor[hora] = valor;
		}

		for (int index = 8; index <= 24; index++) {
			tablaMedicamentos.addCell(new Paragraph(
					dolor[index] != null ? dolor[index] + "" : "",
					turnoFont(index)));
		}

		for (int index = 1; index <= 7; index++) {
			tablaMedicamentos.addCell(new Paragraph(
					dolor[index] != null ? dolor[index] + "" : "",
					turnoFont(index)));
		}

		// ##################RIESGO DE CAIDA###########################
		tablaMedicamentos.getDefaultCell().setHorizontalAlignment(1);
		tablaMedicamentos.addCell(new Paragraph("Riesgo de Caida", fuente));

		int[][] califsPrevCaida = valorPrevencionCaidas(idHoja);

		for (int index = 8; index <= 24; index++) {
			tablaMedicamentos.getDefaultCell().setBackgroundColor(
					getFontPrevencionCaidas(califsPrevCaida[index][5])
							.getColor());
			tablaMedicamentos.addCell(new Paragraph(
					califsPrevCaida[index][5] != 0 ? califsPrevCaida[index][5]
							+ "" : "", data));
		}

		for (int index = 1; index <= 7; index++) {
			tablaMedicamentos.getDefaultCell().setBackgroundColor(
					getFontPrevencionCaidas(califsPrevCaida[index][5])
							.getColor());
			tablaMedicamentos.addCell(new Paragraph(
					califsPrevCaida[index][5] != 0 ? califsPrevCaida[index][5]
							+ "" : "", data));
		}

		tablaMedicamentos.getDefaultCell().setBackgroundColor(Color.WHITE);

		PdfPTable principal = new PdfPTable(1);
		principal.getDefaultCell().setBorderWidth(0);
		principal.addCell(tablaMedicamentos);

		return principal;

	}

	/***
	 * 
	 * @param idHoja
	 * @param sumandos
	 *            arreglo por horas de los sumandos de la suma
	 * @return la suma de los elementos de la prevencion
	 */
	private int[][] valorPrevencionCaidas(Long idHoja) {

		final long rubro = R_PREVENSION_CAIDAS;
		int[][] result = new int[25][6];		
		
		List<RegistroIngresoEgreso> registros = service.consultarIngresoRubro(idHoja, rubro);		

		int renglon = 0;
		for (RegistroIngresoEgreso registro : registros) {
				int valor = 0;
				
				if(!registro.getTotalingresar().isEmpty() && registro.getHora() !=null ){
					valor = Integer.parseInt(registro.getTotalingresar().trim());				
					result[registro.getHora()][renglon] = valor;
					result[registro.getHora()][5] += valor;
					//result[subRegistro.getHora()]+=valor;
				}
				++renglon;
		}

		return result;

	}

	/*
	 * Regresa una tabla con los signos pintados o los que tenga
	 */
	private PdfPTable colorSignos(String cadenaSignos) {
		PdfPTable signosTable = new PdfPTable(new float[] { 0.2f, 0.2f });
		switch (cadenaSignos.length()) {
		case 2:
			signosTable.getDefaultCell().setBorderWidth(0);
			signosTable.addCell(new Paragraph(cadenaSignos.charAt(0) + "", this
					.colorSigno(cadenaSignos.charAt(1))));
			signosTable.addCell("");
			break;
		case 4:
			signosTable.getDefaultCell().setBorderWidth(0);
			signosTable.addCell(new Paragraph(cadenaSignos.charAt(0) + "", this
					.colorSigno(cadenaSignos.charAt(1))));
			signosTable.addCell(new Paragraph(cadenaSignos.charAt(2) + "", this
					.colorSigno(cadenaSignos.charAt(3))));
			signosTable.addCell("");
			signosTable.addCell("");
			break;
		case 6:
			signosTable.getDefaultCell().setBorderWidth(0);
			signosTable.addCell(new Paragraph(cadenaSignos.charAt(0) + "", this
					.colorSigno(cadenaSignos.charAt(1))));
			signosTable.addCell(new Paragraph(cadenaSignos.charAt(2) + "", this
					.colorSigno(cadenaSignos.charAt(3))));
			signosTable.addCell(new Paragraph(cadenaSignos.charAt(4) + "", this
					.colorSigno(cadenaSignos.charAt(5))));
			signosTable.addCell("");
			break;

		case 8:
			signosTable.getDefaultCell().setBorderWidth(0);
			signosTable.addCell(new Paragraph(cadenaSignos.charAt(0) + "", this
					.colorSigno(cadenaSignos.charAt(1))));
			signosTable.addCell(new Paragraph(cadenaSignos.charAt(2) + "", this
					.colorSigno(cadenaSignos.charAt(3))));
			signosTable.addCell(new Paragraph(cadenaSignos.charAt(4) + "", this
					.colorSigno(cadenaSignos.charAt(5))));
			signosTable.addCell(new Paragraph(cadenaSignos.charAt(6) + "", this
					.colorSigno(cadenaSignos.charAt(7))));
			break;

		}

		return signosTable;
	}

	/****
	 * Devuelve el color de acuerdo al signo
	 * 
	 * @param car
	 * @return
	 */
	private Font colorSigno(char car) {
		Font fuente = null;
		switch (car) {

		case 'r':
			fuente = fontBigRojo;
			break;
		case 'a':
			fuente = fontBigAzul;
			break;
		}

		return fuente;

	}

	/*******
	 * Devuelve un catalgo solo con las descripciones
	 * 
	 * @param catalogo
	 * @return
	 */
	/*private LinkedList<String> transformarCatalogo(
			LinkedList<SelectItem> catalogo) {
		LinkedList<String> result = new LinkedList<String>();

		for (SelectItem item : catalogo)
			// Distinto del seleccione
			if (Integer.parseInt(item.getValue().toString()) != SIN_SELECCION)
				result.add(item.getLabel());

		return result;

	}*/

	/*******
	 * Devuelve el turno en curso
	 * 
	 * @param hora
	 * @return
	 */
	private String turno(Integer hora) {

		if (hora == null)
			hora = 0;

		String turno = "@o";

		if (hora >= H_INICIO_MATUTINO && hora <= H_FIN_MATUTINO)
			turno = "@m";

		if (hora >= H_INICIO_VESPERTINO && hora <= H_FIN_VESPERTINO)
			turno = "@t";

		if ((hora >= 21 && hora <= 24) || (hora >= 1 && hora <= 7))
			turno = "@n";

		return turno;
	}

	private Font turnoFont(Integer hora) {

		if (hora == null)
			hora = 0;

		Font turno = data;

		if (hora >= H_INICIO_MATUTINO && hora <= H_FIN_MATUTINO)
			turno = fontAzul;

		if (hora >= H_INICIO_VESPERTINO && hora <= H_FIN_VESPERTINO)
			turno = fontVerde;

		if ((hora >= 21 && hora <= 24) || (hora >= 1 && hora <= 7))
			turno = fontRojo;

		return turno;
	}

	public void addProperties(Document doc) {
		doc.addTitle("Reporte Registros Clinicos");
		doc.addSubject("Reporte Registros Clinicos");
		doc.addKeywords("Itext API");
		doc.addCreator("Desarrollo INR");
		doc.addAuthor("Fernando Javier Santillan Mendoza and Bulbo");
	}
}
