package com.rabo.customer.service;

import com.rabo.customer.dto.RaboResponse;
import com.rabo.customer.dto.Transaction;
import com.rabo.customer.handler.ResultCodes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {
	@InjectMocks
    StatementServiceImpl statementService;

	@Before
	public void setUp() {
	}

	@Test
	public void testSuccessProcessTransactions() {
		List<Transaction> transactions = Arrays.asList(Transaction.builder()
				.reference(111l)
				.accountNumber("1212")
				.startBalance(new BigDecimal("123"))
				.mutation(new BigDecimal("50"))
				.endBalance(new BigDecimal("173"))
				.description("Sample")
				.build());
		RaboResponse response = statementService.processTransactions(transactions);
		assertEquals(ResultCodes.SUCCESSFUL ,response.getResult());
	}

	@Test
	public void testDuplicateReferenceTransactions() {
		Transaction statement1 = Transaction.builder()
				.reference(111l)
				.accountNumber("1212")
				.startBalance(new BigDecimal("123"))
				.mutation(new BigDecimal("123"))
				.endBalance(new BigDecimal("123"))
				.description("Duplicate Reference")
				.build();

		Transaction statement2 = Transaction.builder()
				.reference(111l)
				.accountNumber("1213")
				.startBalance(new BigDecimal("123"))
				.mutation(new BigDecimal("20"))
				.endBalance(new BigDecimal("143"))
				.description("Duplicate Reference")
				.build();
		List<Transaction> transactions = Arrays.asList(statement1, statement2);
		RaboResponse response = statementService.processTransactions(transactions);
		assertEquals(ResultCodes.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE,response.getResult());
	}
}
