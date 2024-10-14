package com.billify.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.billify.entity.Bill;
import com.billify.service.BillService;


@RestController
public class BillController {

    @Autowired
    private BillService billService;

    // Getting all bills
    @GetMapping("/bills")
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billService.getAllBills();
        if (bills.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(bills));
    }

    // Getting a single bill by ID
    @GetMapping("/bills/{id}")
    public ResponseEntity<Bill> getBill(@PathVariable("id") int id) {
        Bill bill = billService.getBill(id);
        if (bill == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(bill));
    }

    // Adding a new bill
    @PostMapping("/bills")
    public ResponseEntity<Bill> addBill(@RequestBody Bill bill) {
        try {
            Bill newBill = billService.addBill(bill);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBill);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Updating an existing bill
    @PutMapping("/bills/{id}")
    public ResponseEntity<Bill> updateBill(@RequestBody Bill bill, @PathVariable("id") int id) {
        try {
            Bill updatedBill = billService.updateBill(bill, id);
            return ResponseEntity.ok().body(updatedBill);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Deleting a bill by ID
    @DeleteMapping("/bills/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable("id") int id) {
        try {
            billService.deleteBill(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}