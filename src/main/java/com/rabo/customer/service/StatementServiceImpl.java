package com.rabo.customer.service;

import com.rabo.customer.dto.ErrorRecord;
import com.rabo.customer.dto.CustomResponseEntity;
import com.rabo.customer.dto.Transaction;
import com.rabo.customer.handler.ResultCodes;
import com.rabo.customer.handler.InvalidTransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Nagaraju Kommineni
 */
@Service
public class StatementServiceImpl implements StatementService {

    private static final Logger LOGGER= LoggerFactory.getLogger(StatementServiceImpl.class);

	/**
	 * @param transactions the list of transactions
	 * @return CustomResponseEntity
	 */
	@Override
	public CustomResponseEntity processTransactions(List<Transaction> transactions) {
	    LOGGER.info("Begin StatementServiceImpl processTransactions");
		CustomResponseEntity response = new CustomResponseEntity(ResultCodes.SUCCESSFUL, Collections.EMPTY_LIST);
		Predicate<Transaction> isInvalidBalance = transaction -> {
			return transaction.getEndBalance().subtract(transaction.getStartBalance()).compareTo(transaction.getMutation()) != 0;
		};
		Set<Long> referenceSet = new HashSet<>();
		List<ErrorRecord> duplicateRefereces = new ArrayList<>();
		List<ErrorRecord> inCorrectBalanceRecords = transactions.stream()
				.peek(transaction -> {
					if (!referenceSet.add(transaction.getReference()))
						duplicateRefereces.add(new ErrorRecord(transaction));
				})
				.filter(isInvalidBalance)
				.map(ErrorRecord::new)
				.collect(Collectors.toList());
		boolean isDuplicatePresent = !duplicateRefereces.isEmpty();
		boolean isInCorrectBalance = !inCorrectBalanceRecords.isEmpty();
        if (isDuplicatePresent || isInCorrectBalance) {
            response.setErrorRecords(duplicateRefereces);
            response.setResult(ResultCodes.DUPLICATE_REFERENCE);
            if (isInCorrectBalance) {
                response.getErrorRecords().addAll(inCorrectBalanceRecords);
                response.setResult(isDuplicatePresent? ResultCodes.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE: ResultCodes.INCORRECT_END_BALANCE);
            }
            throw new InvalidTransactionException(response);
        }
        LOGGER.info("End StatementServiceImpl processTransactions");
		return response;
	}

}
