package com.billify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billify.entity.Bill;

public interface BillRepository extends JpaRepository<Bill,Integer> {

}
