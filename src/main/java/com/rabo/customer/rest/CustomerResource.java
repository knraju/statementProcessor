package com.rabo.customer.rest;


import com.rabo.customer.dto.CustomResponseEntity;
import com.rabo.customer.dto.Transaction;
import com.rabo.customer.service.StatementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Nagaraju Kommineni
 */
@RestController
@Validated
public class CustomerResource {

	private static final Logger LOGGER= LoggerFactory.getLogger(CustomerResource.class);
	
	@Autowired
	private StatementService statementService;

	/**
	 * @param transactions the list of transactions
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/statement/processor")
	public ResponseEntity validateTransactions(@RequestBody @Valid @NotEmpty List<Transaction> transactions) {
		LOGGER.info("Begin CustomerResource validateTransactions");
		CustomResponseEntity response = statementService.processTransactions(transactions);
		LOGGER.info("End CustomerResource validateTransactions");
        return new ResponseEntity(response, HttpStatus.OK);
	}
}
