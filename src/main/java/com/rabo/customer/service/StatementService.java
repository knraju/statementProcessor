package com.rabo.customer.service;


import com.rabo.customer.dto.RaboResponse;
import com.rabo.customer.dto.Transaction;

import java.util.List;

public interface StatementService {

	RaboResponse processTransactions(List<Transaction> transactions);

}
