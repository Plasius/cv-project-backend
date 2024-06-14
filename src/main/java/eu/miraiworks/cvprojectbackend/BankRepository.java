package eu.miraiworks.cvprojectbackend;

import eu.miraiworks.cvprojectbackend.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    // Define custom query methods if needed
    List<Bank> findTop5ByNameStartingWith(String name);
}