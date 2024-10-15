package com.billify.service;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;

@Service
public class PDFServiceImpl implements PDFService {

    @Override
    public ByteArrayInputStream generateBill(String billNo, String genDate) {
        return null;
    }

}
