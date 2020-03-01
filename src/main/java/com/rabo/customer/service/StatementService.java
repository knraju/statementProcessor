package com.rabo.customer.service;


import com.rabo.customer.dto.Transaction;
import com.rabo.customer.dto.RaboResponse;

import java.util.List;

public interface StatementService {

	RaboResponse processTransactions(List<Transaction> transactions);

}
