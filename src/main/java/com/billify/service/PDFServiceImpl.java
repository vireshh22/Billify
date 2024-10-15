// package com.billify.service;

// import java.io.ByteArrayInputStream;
// import java.io.ByteArrayOutputStream;
// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.lowagie.text.Document;
// import com.lowagie.text.DocumentException;
// import com.lowagie.text.Paragraph;
// import com.lowagie.text.Table;
// import com.lowagie.text.pdf.PdfWriter;

// @Service
// public class PDFServiceImpl implements PDFService {

//     @Override
//     public ByteArrayInputStream generateBill(String billNo, String genDate) {
//         List<Object[]> data = new ArrayList<>();
//         data.add(new Object[]{billNo,genDate});
//         data.add(new Object[]{billNo,genDate});
//         data.add(new Object[]{billNo,genDate});
//         data.add(new Object[]{billNo,genDate});
//         data.add(new Object[]{billNo,genDate});
        
//         Document doc = new Document();
//         ByteArrayOutputStream out = new ByteArrayOutputStream();


//         try{
//             PdfWriter.getInstance(doc, out);
//             doc.open();
//             doc.add(new Paragraph("Viresh Hiremath's Bill"));

//             Table table = new Table(2);
//             table.addCell("Bill No");
//             table.addCell("Date");

//             for(Object[] obj : data){
//                 System.out.println(obj);
//                 table.addCell(billNo);
//                 table.addCell(genDate);
//             }

//             doc.add(table);
//         }catch(DocumentException e){
//             e.printStackTrace();
//         }finally{
//             doc.close();
//         }

//         return new ByteArrayInputStream(out.toByteArray());
//     }

// }


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
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PDFServiceImpl implements PDFService {

    @Override
    public ByteArrayInputStream generateBill(String billNo, String genDate) {
        // Sample data based on the image structure
        List<Object[]> services = new ArrayList<>();
        services.add(new Object[]{"Consultation", "1", "$100", "$100"});
        services.add(new Object[]{"X-Ray", "1", "$80", "$80"});
        services.add(new Object[]{"Blood Test", "1", "$50", "$50"});
        
        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(doc, out);
            doc.open();

            // Header: Medical Invoice and Logo placeholder
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("MEDICAL INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_LEFT);
            doc.add(title);

            doc.add(new Paragraph("\n"));

            // Invoice Number and Date
            Font subFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Paragraph invoiceDetails = new Paragraph("Invoice number: " + billNo + "\nDate of Issue: " + genDate, subFont);
            invoiceDetails.setAlignment(Element.ALIGN_LEFT);
            doc.add(invoiceDetails);
            
            doc.add(new Paragraph("\n"));

            // Billing Information
            doc.add(new Paragraph("Billed To:", subFont));
            doc.add(new Paragraph("Client Name\nStreet Address\nCity, State Country\nPin Code\n\n"));

            // Company Information
            doc.add(new Paragraph("Your Company Name", subFont));
            doc.add(new Paragraph("121 Your Street, City, State, Country, Pin Code\nPhone: yourPhone\nEmail: youremail@domain.com\n\n"));

            // Services Information (similar to the list format)
            Font itemFont = new Font(Font.HELVETICA, 10, Font.BOLD);
            doc.add(new Paragraph("Description               Quantity               Price/Unit               Amount", itemFont));
            
            // Loop through services and display
            for (Object[] service : services) {
                doc.add(new Paragraph(
                    service[0].toString() + "               " +  // Service Name
                    service[1].toString() + "               " +   // Quantity
                    service[2].toString() + "               " +   // Unit Price
                    service[3].toString()));                           // Amount
            }

            // Add spacing before total
            doc.add(new Paragraph("\n"));

            // Subtotal, Taxes, and Total Amount
            doc.add(new Paragraph("Subtotal:                        $230", subFont));
            doc.add(new Paragraph("Tax (10%):                      $23", subFont));
            doc.add(new Paragraph("Total Amount:                 $253", subFont));

            // Footer
            doc.add(new Paragraph("\n\nThank you for choosing ABC Hospital.", subFont));

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
