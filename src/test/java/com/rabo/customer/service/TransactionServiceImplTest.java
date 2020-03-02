package com.rabo.customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.customer.dto.RaboResponse;
import com.rabo.customer.dto.Transaction;
import com.rabo.customer.handler.TransactionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.rabo.customer.handler.ResultCodes.DUPLICATE_REFERENCE;
import static com.rabo.customer.handler.ResultCodes.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE;
import static com.rabo.customer.handler.ResultCodes.INCORRECT_END_BALANCE;
import static com.rabo.customer.handler.ResultCodes.SUCCESSFUL;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceImplTest {

	@InjectMocks
    StatementServiceImpl statementService;

	@Value("${success_transaction}")
	private String successTransaction;

	@Value("${duplicate_references_transactions}")
	private String duplicateReferenceTransactions;

	@Value("${incorrect_end_balance_transactions}")
	private String incorrectEndBalanceTransactions;

	@Value("${duplicate_incorrect_transactions}")
	private String duplicateIncorrectBalanceTransactions;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
	}

	@Test
	public void testSuccessProcessTransactions() throws JsonProcessingException {
		List<Transaction> transactions = Arrays.asList(mapper.readValue(successTransaction, Transaction[].class));
		RaboResponse response = statementService.processTransactions(transactions);
		assertEquals(SUCCESSFUL ,response.getResult());
	}

	@Test
	public void testDuplicateReferenceTransactions() throws TransactionException, JsonProcessingException {
		List<Transaction> transactions = Arrays.asList(mapper.readValue(duplicateReferenceTransactions, Transaction[].class));
		try {
			statementService.processTransactions(transactions);
		} catch (TransactionException te) {
			assertThat(te.getRaboResponse().getResult(), is(DUPLICATE_REFERENCE));
		}
	}

	@Test
	public void testInCorrectEndBalanceTransaction() throws TransactionException, JsonProcessingException {
		List<Transaction> transactions = Arrays.asList(mapper.readValue(incorrectEndBalanceTransactions, Transaction[].class));
		try {
			statementService.processTransactions(transactions);
		} catch (TransactionException te) {
			assertThat(te.getRaboResponse().getResult(), is(INCORRECT_END_BALANCE));
		}

	}

	@Test
	public void testDuplicateInCorrectEndBalanceTransaction() throws TransactionException, JsonProcessingException {
		List<Transaction> transactions = Arrays.asList(mapper.readValue(duplicateIncorrectBalanceTransactions, Transaction[].class));
		try {
			statementService.processTransactions(transactions);
		} catch (TransactionException te) {
			assertThat(te.getRaboResponse().getResult(), is(DUPLICATE_REFERENCE_INCORRECT_END_BALANCE));
		}

	}
}
