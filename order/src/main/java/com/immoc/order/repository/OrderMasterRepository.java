package com.immoc.order.repository;

import com.immoc.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
}