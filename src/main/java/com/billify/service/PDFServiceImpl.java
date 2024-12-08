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
        // Patient and invoice details
        String patientName = "Balaji Alle";
        String patientNo = "157";
        String admissionDate = "3/10/24";
        String dischargeDate = "31/10/24";
        double totalAmount = 900;

        // Items data
        List<Object[]> items = new ArrayList<>();
        items.add(new Object[] { 1, "RL 500ml", 1, 64, 64 });
        items.add(new Object[] { 2, "5ml sy.", 1, 20, 20 });
        items.add(new Object[] { 3, "5cc IP", 1, 20, 20 });
        items.add(new Object[] { 4, "IV set", 1, 200, 200 });
        items.add(new Object[] { 5, "multivitam Inj.", 2, 50, 100 });
        items.add(new Object[] { 6, "NT (120ml)", 1, 40, 40 });
        items.add(new Object[] { 7, "OPD(1)", 1, 150, 150 });
        items.add(new Object[] { 8, "OPD(2)", 1, 120, 120 });
        items.add(new Object[] { 9, "IPD", 1, 300, 300 });

        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(doc, out);
            doc.open();

            Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font subHeaderFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font regularFont = new Font(Font.HELVETICA, 10, Font.NORMAL);

            // Header
            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[] { 2, 6, 2 });

            PdfPCell regCell = new PdfPCell(new Paragraph("Reg. No: 98684", regularFont));
            regCell.setBorder(Rectangle.NO_BORDER);
            regCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(regCell);

            PdfPCell clinicCell = new PdfPCell(new Paragraph("Harsh Clinic", headerFont));
            clinicCell.setBorder(Rectangle.NO_BORDER);
            clinicCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(clinicCell);

            PdfPCell phoneCell = new PdfPCell(new Paragraph("Phone: 9860840343", regularFont));
            phoneCell.setBorder(Rectangle.NO_BORDER);
            phoneCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            headerTable.addCell(phoneCell);
            doc.add(headerTable);

            // Add doctor's details
            Paragraph doctorName = new Paragraph("Dr. Vanita Vasant Myakal", subHeaderFont);
            doctorName.setAlignment(Element.ALIGN_CENTER);
            doc.add(doctorName);

            Paragraph doctorProfession = new Paragraph("M.B.B.S., M.D. (Community Medicine)", regularFont);
            doctorProfession.setAlignment(Element.ALIGN_CENTER);
            doc.add(doctorProfession);

            doc.add(new Paragraph("\n"));

            // Invoice details
            PdfPTable invoiceTable = new PdfPTable(3);
            invoiceTable.setWidthPercentage(100);
            invoiceTable.setWidths(new float[] { 3, 3, 3 });

            PdfPCell billNoCell = new PdfPCell(new Paragraph("Bill No: " + "016", regularFont));
            billNoCell.setBorder(Rectangle.NO_BORDER);
            billNoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            invoiceTable.addCell(billNoCell);

            PdfPCell invoiceTitleCell = new PdfPCell(new Paragraph("Invoice", headerFont));
            invoiceTitleCell.setBorder(Rectangle.NO_BORDER);
            invoiceTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            invoiceTable.addCell(invoiceTitleCell);

            PdfPCell dateCell = new PdfPCell(new Paragraph("Date: " + "03/11/2024", regularFont));
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            invoiceTable.addCell(dateCell);
            doc.add(invoiceTable);

            doc.add(new Paragraph("\n"));

            // Patient details
            doc.add(new Paragraph("Patient Name: " + patientName, regularFont));
            doc.add(new Paragraph("Patient No: " + patientNo, regularFont));
            doc.add(new Paragraph("Admission Date: " + admissionDate, regularFont));
            doc.add(new Paragraph("Discharge Date: " + dischargeDate, regularFont));
            doc.add(new Paragraph("\n"));

            // Table for items
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[] { 1, 4, 1, 1, 1 });

            table.addCell(new PdfPCell(new Paragraph("S. No", boldFont)));
            table.addCell(new PdfPCell(new Paragraph("Particulars & Details", boldFont)));
            table.addCell(new PdfPCell(new Paragraph("Qty", boldFont)));
            table.addCell(new PdfPCell(new Paragraph("Rate", boldFont)));
            table.addCell(new PdfPCell(new Paragraph("Amount", boldFont)));

            for (Object[] item : items) {
                for (Object field : item) {
                    table.addCell(new PdfPCell(new Paragraph(field.toString(), regularFont)));
                }
            }
            doc.add(table);

            // Total Section
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[] { 9, 1 });

            PdfPCell totalLabelCell = new PdfPCell(new Paragraph("Total:", boldFont));
            totalLabelCell.setBorder(Rectangle.NO_BORDER);
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalLabelCell.setPaddingRight(20); // Reduce gap
            totalTable.addCell(totalLabelCell);

            PdfPCell totalValueCell = new PdfPCell(new Paragraph(String.valueOf(totalAmount), boldFont));
            totalValueCell.setBorder(Rectangle.NO_BORDER);
            totalValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalTable.addCell(totalValueCell);
            doc.add(totalTable);

            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));

            // Footer Section
            PdfPTable footerTable = new PdfPTable(2);
            footerTable.setWidthPercentage(100);
            footerTable.setWidths(new float[] { 1, 1 });

            PdfPCell guardianSignCell = new PdfPCell(new Paragraph("Guardian / Care of Sign", regularFont));
            guardianSignCell.setBorder(Rectangle.NO_BORDER);
            guardianSignCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            footerTable.addCell(guardianSignCell);

            PdfPCell adminSignCell = new PdfPCell(new Paragraph("Signature for Admin", regularFont));
            adminSignCell.setBorder(Rectangle.NO_BORDER);
            adminSignCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(adminSignCell);

            doc.add(footerTable);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
