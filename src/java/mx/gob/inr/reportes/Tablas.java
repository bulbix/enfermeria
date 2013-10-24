package mx.gob.inr.reportes;

import java.io.Serializable;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;

@SuppressWarnings("serial")
public class Tablas extends PdfPageEventHelper implements Serializable {

	public PdfPTable crearTabla(int sup, int inf, int izq, int der, String txt, Font fuente) {
		
		PdfPTable cuadros = new PdfPTable(1);
		cuadros.getDefaultCell().setBorder(0);
		cuadros.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		cuadros.getDefaultCell().setBorderWidthTop(sup);
		cuadros.getDefaultCell().setBorderWidthBottom(inf);
		cuadros.getDefaultCell().setBorderWidthLeft(izq);
		cuadros.getDefaultCell().setBorderWidthRight(der);
		cuadros.addCell(new Paragraph("" + txt, fuente));
		
		return cuadros;
	}
	
	public PdfPTable crearTablaMultiple(int sup, int inf, int izq, int der, int n, float width[], String A[], Font fuente, boolean centrado) {
		
		PdfPTable tabla = new PdfPTable(width);
		
		for(int i = 0; i < n; i++) {
			tabla.getDefaultCell().setBorderWidthTop(sup);
			tabla.getDefaultCell().setBorderWidthBottom(inf);
			tabla.getDefaultCell().setBorderWidthLeft(izq);
			tabla.getDefaultCell().setBorderWidthRight(der);
			
			if(centrado == true) {
				tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				tabla.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			}				
			
			tabla.addCell(new Paragraph("" + A[i], fuente));
			
			izq = 0;
		}
		
		return tabla;
	}
	
	public PdfPTable crearTablaMultiple(int sup, int inf, int izq, int der, int n, float width[], String A[], Font fuente) {
		
		PdfPTable tabla = new PdfPTable(width);
		
		for(int i = 0; i < n; i++) {
			tabla.getDefaultCell().setBorderWidthTop(sup);
			tabla.getDefaultCell().setBorderWidthBottom(inf);
			tabla.getDefaultCell().setBorderWidthLeft(izq);
			tabla.getDefaultCell().setBorderWidthRight(der);
			tabla.addCell(new Paragraph("" + A[i], fuente));
			
			izq = 0;
		}
		
		return tabla;
	}
	
	public PdfPTable crearTablaMultiple(int sup, int inf, int izq, int der, int n, float width[], char A[], Font fuente) {
		
		PdfPTable tabla = new PdfPTable(width);
		
		for(int i = 0; i < n; i++) {
			tabla.getDefaultCell().setBorderWidthTop(sup);
			tabla.getDefaultCell().setBorderWidthBottom(inf);
			tabla.getDefaultCell().setBorderWidthLeft(izq);
			tabla.getDefaultCell().setBorderWidthRight(der);
			tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tabla.addCell(new Paragraph("" + A[i], fuente));
			
			izq = 0;
		}
		
		return tabla;
	}
	
	public PdfPTable subrayado(String txt, Font fuente) {
		
		PdfPTable tabla = new PdfPTable(1);
		
		tabla.getDefaultCell().setBorderWidthTop(0);
		tabla.getDefaultCell().setBorderWidthLeft(0);
		tabla.getDefaultCell().setBorderWidthRight(0);
		tabla.getDefaultCell().setBorderWidthBottom(1);
		tabla.addCell(new Paragraph("" + txt, fuente));
		
		return tabla;
	}
	
	public PdfPTable subrayado(String txt, Font fuente, boolean b) {
		
		PdfPTable tabla = new PdfPTable(1);
		
		if(b)
			tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		
		tabla.getDefaultCell().setBorderWidthTop(0);
		tabla.getDefaultCell().setBorderWidthLeft(0);
		tabla.getDefaultCell().setBorderWidthRight(0);
		tabla.getDefaultCell().setBorderWidthBottom(1);
		tabla.addCell(new Paragraph("" + txt, fuente));					
		
		return tabla;
	}
	
	
}
