package mx.gob.inr.reportes;

import java.awt.Color;
import java.io.Serializable;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;

public class Borde implements PdfPCellEvent, Serializable {
	
	public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas) {
			
		PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
		cb.setColorStroke(Color.BLACK);
		cb.roundRectangle(rect.getLeft() + 4, rect.getBottom(), rect.getWidth() - 8, rect.getHeight() - 4, 4);
		cb.stroke();
	}
}
