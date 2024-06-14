package eu.miraiworks.cvprojectbackend;

import eu.miraiworks.cvprojectbackend.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/banks")
    public List<String> getBanks(@RequestParam String q) {
        String query = q.toLowerCase();
        return bankService.getBanksByNameStartingWith(query);
    }
}
