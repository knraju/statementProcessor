package com.rabo.customer.service;

import com.rabo.customer.dto.ErrorRecord;
import com.rabo.customer.dto.RaboResponse;
import com.rabo.customer.dto.Transaction;
import com.rabo.customer.handler.ResultCodes;
import com.rabo.customer.handler.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class StatementServiceImpl implements StatementService {

    private static final Logger LOGGER= LoggerFactory.getLogger(StatementServiceImpl.class);

	Predicate<Transaction> isInvalidBalance = transaction -> {
        return transaction.getEndBalance().subtract(transaction.getStartBalance()).compareTo(transaction.getMutation()) != 0;
    };

	@Override
	public RaboResponse processTransactions(List<Transaction> transactions) {
	    LOGGER.info("Begin StatementServiceImple processTransactions");
		RaboResponse response = new RaboResponse();
		Set<Long> referenceSet = new HashSet<>();
		List<ErrorRecord> duplicateRefereces = new ArrayList<>();
		/*List<ErrorRecord> duplicateRefereces = transactions.stream().filter(cs -> !references.add(cs.getReference()))
				.map(ErrorRecord::new)
				.collect(Collectors.toList());*/

		List<ErrorRecord> inCorrectBalanceRecords = transactions.stream()
				.peek(transaction -> {
					if (!referenceSet.add(transaction.getReference()))
						duplicateRefereces.add(new ErrorRecord(transaction));
				})
				.filter(isInvalidBalance)
				.map(ErrorRecord::new)
				.collect(Collectors.toList());
		LOGGER.info("duplicateRefereces tranactions count : {}", duplicateRefereces.size());
		LOGGER.info("inCorrectEndBalance tranactions count : {}",inCorrectBalanceRecords.size());
		boolean isDuplicatePresent = !duplicateRefereces.isEmpty();
		boolean isInCorrectBalance = !inCorrectBalanceRecords.isEmpty();
        if (isDuplicatePresent || isInCorrectBalance) {
            response.setErrorRecords(duplicateRefereces);
            response.setResult(ResultCodes.DUPLICATE_REFERENCE);
            if (isInCorrectBalance) {
                response.getErrorRecords().addAll(inCorrectBalanceRecords);
                response.setResult(isDuplicatePresent? ResultCodes.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE: ResultCodes.INCORRECT_END_BALANCE);
            }
            throw new TransactionException(response);
        }

        // If No duplicate reference and inCorrectEndBalance then it will continue
        // Here there is no other operations so returning success response.
        LOGGER.info("End StatementServiceImple processTransactions");
		return response;
	}

}
