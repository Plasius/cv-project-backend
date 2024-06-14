package eu.miraiworks.cvprojectbackend;
import eu.miraiworks.cvprojectbackend.entities.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    // Method to find cities by name starting with 'query'
    public List<String> getBanksByNameStartingWith(String query) {
        List<String> bankNames = new ArrayList<>();
        List<Bank> banks = bankRepository.findTop5ByNameStartingWith(query.toLowerCase());

        for (Bank bank : banks) {
            bankNames.add(bank.getName());
        }

        return bankNames;
    }
}
