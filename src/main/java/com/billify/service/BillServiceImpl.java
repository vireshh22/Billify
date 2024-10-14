package com.billify.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

// import org.springframework.beans.factory.annotation.Autowired;

import com.billify.entity.Bill;
// import com.billify.repositories.BillRepository;

@Service
public class BillServiceImpl implements BillService {
    // @Autowired
    // private BillRepository billRepository;

    // Sample data (in memory for testing)
    private static List<Bill> billList = new ArrayList<>();

    // Constructor to initialize with some data
    static{
        billList.add(new Bill(1, "Laptop", 2, 50000.0, 2000.0, 104000.0));
        billList.add(new Bill(2, "Printer", 1, 15000.0, 500.0, 15500.0));
        billList.add(new Bill(3, "Monitor", 3, 12000.0, 600.0, 37800.0));
    }

    // Get all bills
    @Override
    public List<Bill> getAllBills() {
        return billList;  // Have to replace with billRepository.findAll() when using a real DB
    }

    // Get a single bill by ID
    @Override
    public Bill getBill(int id) {
        Optional<Bill> bill = billList.stream().filter(b -> b.getId() == id).findFirst();
        return bill.orElse(null);
    }

    // Add a new bill
    @Override
    public Bill addBill(Bill bill) {
        billList.add(bill);  // have to replace with billRepository.save(bill) for real DB
        return bill;
    }

    // Update an existing bill
    @Override
    public Bill updateBill(Bill bill, int id) {
        Bill existingBill = getBill(id);
        if (existingBill != null) {
            existingBill.setEquipmentName(bill.getEquipmentName());
            existingBill.setQuantity(bill.getQuantity());
            existingBill.setPrice(bill.getPrice());
            existingBill.setTax(bill.getTax());
            existingBill.setTotal(bill.getTotal());
        }
        return existingBill;  // have to replace with billRepository.save(existingBill) for real DB
    }

    // Delete a bill by ID
    @Override
    public void deleteBill(int id) {
        billList.removeIf(bill -> bill.getId() == id);  // Have to replace with billRepository.deleteById(id) for real DB
    }
}
