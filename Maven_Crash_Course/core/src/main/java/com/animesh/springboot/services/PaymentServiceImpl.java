package com.animesh.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.animesh.springboot.dao.PaymentDAO;

@Service
public class PaymentServiceImpl implements PaymentService {
		
	@Autowired
	private PaymentDAO paymentDAO;

	public PaymentDAO getPaymentDAO() {
		return paymentDAO;
	}

	public void setPaymentDAO(PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}
	
}
