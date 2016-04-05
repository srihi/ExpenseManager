package max93n.services.impl;

import max93n.entities.Account;
import max93n.entities.Transaction;
import max93n.repositories.TransactionRepository;
import max93n.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("transactionService")
public class transactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void add(Transaction transaction) {
        transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public Transaction getInitial(Account account) {
        return transactionRepository.getInitial(account);
    }
}
