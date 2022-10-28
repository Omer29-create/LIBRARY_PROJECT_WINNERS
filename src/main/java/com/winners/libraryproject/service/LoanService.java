package com.winners.libraryproject.service;

import com.winners.libraryproject.entity.Category;
import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final static String LOAN_NOT_FOUND = "loan with id %d not found";
    private final LoanRepository loanRepository;
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> findLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public void createLoan(Loan loan) {
        loanRepository.save(loan);
    }

    public void updatedLoan(Long id, Loan loan) {
        Optional<Loan> loanDetails = loanRepository.findById(id);
        if (loanDetails.isPresent()) {
            Loan newLoan= new Loan(loan.getId(), loan.getUserId(), loan.getBookId(), loan.getLoanDate(), loan.getExpireDate(), loan.getReturnDate(), loan.getNotes());
            loanRepository.save(newLoan);
        }

    }

    public void deleteLoanById(Long id) {
        loanRepository.deleteById(id);
    }
}
