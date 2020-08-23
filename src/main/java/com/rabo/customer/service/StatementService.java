package com.rabo.customer.service;


import com.rabo.customer.dto.CustomResponseEntity;
import com.rabo.customer.dto.Transaction;

import java.util.List;

/**
 * @author Nagaraju Kommineni
 */
public interface StatementService {

	CustomResponseEntity processTransactions(List<Transaction> transactions);

}
