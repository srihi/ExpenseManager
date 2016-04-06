package max93n.services.impl;

import max93n.entities.Account;
import max93n.entities.Transaction;
import max93n.repositories.TransactionRepository;
import max93n.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<Transaction> getAllByAccount(Account account) {
        return transactionRepository.getAllByAccount(account);
    }

    @Override
    public Date getFirstDateOfTransaction(Account account) {
        return transactionRepository.getFirstDateOfTransaction(account);
    }

    @Override
    public Date getLastDateOfTransaction(Account account) {
        return transactionRepository.getLastDateOfTransaction(account);
    }

    @Override
    public List<Transaction> getWithSpecification(Specification specification, PageRequest request) {
        return transactionRepository.findAll(specification, request).getContent();
    }

    @Override
    public long getWithSpecificationCount(Specification specification) {
        return  transactionRepository.count(specification);
    }

    @Override
    public List<Transaction> getIncomeTransactionByAccount(Account account) {
        return transactionRepository.getIncomeTransactionByAccount(account);
    }

    @Override
    public List<Transaction> getExpenseTransactionByAccount(Account account) {
        return transactionRepository.getExpenseTransactionByAccount(account);
    }


}
