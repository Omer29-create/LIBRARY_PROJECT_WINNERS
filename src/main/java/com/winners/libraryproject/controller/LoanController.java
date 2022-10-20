package com.winners.libraryproject.controller;


import com.winners.libraryproject.entity.Category;
import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<List<Loan>> findAllLoans() {
        List<Loan> loan = loanService.findAllLoans();
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @GetMapping("/loan/{id}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Optional<Loan>> findLoanById(@PathVariable Long id) {
        Optional<Loan> loan = loanService.findLoanById(id);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Map<String, Boolean>> createLoan(@RequestBody Loan loan){
        loanService.createLoan(loan);

        Map<String, Boolean> map = new HashMap<>();
        map.put("Loan created successfully", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Map<String, Boolean>> updateLoan(@Valid @PathVariable Long id, @RequestBody Loan loan){
        loanService.updatedLoan(id, loan);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<String> deleteLoan(@PathVariable("id") Long id){
        loanService.deleteLoanById(id);

        return new ResponseEntity<>("success",HttpStatus.OK);
    }
}
