package com.billify.service;

import java.util.List;

import com.billify.entity.Bill;

public interface BillService {
    List<Bill> getAllBills();

    Bill getBill(int id);

    Bill addBill(Bill bill);

    Bill updateBill(Bill bill, int id);

    void deleteBill(int id);
}
