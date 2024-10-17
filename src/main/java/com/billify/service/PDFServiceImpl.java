package com.billify.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PDFServiceImpl implements PDFService {

    @Override
    public ByteArrayInputStream generateBill(String billNo, String genDate) {
        // Sample items data with [S.No, Item Name, HSN, Rate, MRP, Tax, Amount]
        List<Object[]> items = new ArrayList<>();
        items.add(new Object[]{1, "Sample item 243846", "3846", "xxxx", "xxxx", "xxxx", "xxxx"});
        items.add(new Object[]{2, "Sample item 937539", "9796", "xxxx", "xxxx", "xxxx", "xxxx"});
        items.add(new Object[]{3, "Sample item 425695", "5672", "xxxx", "xxxx", "xxxx", "xxxx"});
        
        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(doc, out);
            doc.open();

            // Set font styles
            Font headerFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font regularFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
            
            // Header - Title and Company Information
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            PdfPCell titleCell = new PdfPCell(new Paragraph("TAX INVOICE", headerFont));
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(titleCell);

            // Company information in the right corner
            PdfPCell companyInfoCell = new PdfPCell(new Paragraph(
                "Medical Invoice\nAddress: Church Street Bengaluru\nPhone: +91-1075314648 +91-8029924749", regularFont));
            companyInfoCell.setBorder(Rectangle.NO_BORDER);
            companyInfoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            headerTable.addCell(companyInfoCell);
            doc.add(headerTable);

            doc.add(new Paragraph("\n"));

            // Party Details (Name, Address, GSTIN No.)
            doc.add(new Paragraph("Party's Name", boldFont));
            doc.add(new Paragraph("Name: John Doe", regularFont));
            doc.add(new Paragraph("Address: 123 Street, City, State, Zip", regularFont));
            doc.add(new Paragraph("GSTIN NO: 27ABCDE1234FZ1", regularFont));
            
            doc.add(new Paragraph("\n"));

            // Itemized Table (S.No, Items, HSN, Rate, MRP, Tax, Amount)
            PdfPTable table = new PdfPTable(7);  // 7 columns
            table.setWidthPercentage(100);

            // Add table header
            table.addCell(new PdfPCell(new Paragraph("S.No", boldFont)));
            table.addCell(new PdfPCell(new Paragraph("Items", boldFont)));
            table.addCell(new PdfPCell(new Paragraph("HSN", boldFont)));
            table.addCell(new PdfPCell(new Paragraph("RATE", boldFont)));
            table.addCell(new PdfPCell(new Paragraph("MRP", boldFont)));
            table.addCell(new PdfPCell(new Paragraph("TAX", boldFont)));
            table.addCell(new PdfPCell(new Paragraph("Amount", boldFont)));

            // Add item rows
            for (Object[] item : items) {
                for (Object field : item) {
                    table.addCell(new PdfPCell(new Paragraph(field.toString(), regularFont)));
                }
            }
            
            doc.add(table);

            doc.add(new Paragraph("\n"));

            // Subtotal and other details
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);

            PdfPCell subtotalLabel = new PdfPCell(new Paragraph("Sub Total", boldFont));
            subtotalLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
            subtotalLabel.setBorder(Rectangle.NO_BORDER);
            totalTable.addCell(subtotalLabel);
            totalTable.addCell(new PdfPCell(new Paragraph("xxxx", regularFont)));  // Placeholder for subtotal

            doc.add(totalTable);

            // Amount in words and terms
            doc.add(new Paragraph("Amount in words: Five Hundred Thirty Dollars", regularFont));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("Terms and Conditions", boldFont));
            doc.add(new Paragraph("1. Goods once sold will not be taken back.", regularFont));
            doc.add(new Paragraph("2. Payment due within 30 days.", regularFont));
            
            // Seal and signature
            PdfPTable sealTable = new PdfPTable(1);
            sealTable.setWidthPercentage(100);
            PdfPCell sealCell = new PdfPCell(new Paragraph("Seal & Signature", boldFont));
            sealCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            sealCell.setBorder(Rectangle.NO_BORDER);
            sealTable.addCell(sealCell);
            
            doc.add(sealTable);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
