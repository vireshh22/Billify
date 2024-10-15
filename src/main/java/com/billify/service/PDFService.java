package com.billify.service;

import java.io.ByteArrayInputStream;

public interface PDFService {
    ByteArrayInputStream generateBill(String billNo,String genDate);
}
