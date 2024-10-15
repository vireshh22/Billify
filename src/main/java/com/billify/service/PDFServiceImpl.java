package com.billify.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PDFServiceImpl implements PDFService {

    @Override
    public ByteArrayInputStream generateBill(String billNo, String genDate) {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{billNo,genDate});
        data.add(new Object[]{billNo,genDate});
        data.add(new Object[]{billNo,genDate});
        data.add(new Object[]{billNo,genDate});
        data.add(new Object[]{billNo,genDate});
        
        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();


        try{
            PdfWriter.getInstance(doc, out);
            doc.open();
            doc.add(new Paragraph("Viresh Hiremath's Bill"));

            Table table = new Table(2);
            table.addCell("Bill No");
            table.addCell("Date");

            for(Object[] obj : data){
                System.out.println(obj);
                table.addCell(billNo);
                table.addCell(genDate);
            }

            doc.add(table);
        }catch(DocumentException e){
            e.printStackTrace();
        }finally{
            doc.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
