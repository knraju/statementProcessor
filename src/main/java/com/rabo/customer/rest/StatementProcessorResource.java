package com.rabo.customer.rest;


import com.rabo.customer.dto.Transaction;
import com.rabo.customer.dto.RaboResponse;
import com.rabo.customer.service.StatementService;
import com.rabo.customer.service.StatementServiceImpl;
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

@RestController
@Validated
@RequestMapping("/statement")
public class StatementProcessorResource {

	private static final Logger LOGGER= LoggerFactory.getLogger(StatementProcessorResource.class);
	
	@Autowired
	private StatementService statementService;

	@PostMapping(value = "/processor")
	public ResponseEntity validateTransactions(@RequestBody @Valid @NotEmpty List<Transaction> transactions) {
		LOGGER.info("Begin StatementProcessorResource validateTransactions");
		LOGGER.info("Total transactions : {}", transactions.size());
		RaboResponse response = statementService.processTransactions(transactions);
		LOGGER.info("End StatementProcessorResource validateTransactions");
        return new ResponseEntity(response, HttpStatus.OK);
	}




}
