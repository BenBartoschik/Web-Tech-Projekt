package htw.webtech.projektname.repository;

import htw.webtech.projektname.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// @DataJpaTest existiert in Spring Boot 4.x nicht mehr (wie @WebMvcTest).
// Stattdessen: @SpringBootTest mit WebEnvironment.NONE lädt den vollen Application-Context,
// aber ohne Webserver. Die PostgreSQL-Datasource wird über inline-Properties durch H2 ersetzt,
// sodass der Test ohne laufende Datenbank ausführbar ist.
// @Transactional sorgt dafür, dass jeder Test nach Abschluss automatisch zurückgerollt wird
// und die Tests sich gegenseitig nicht beeinflussen.
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.username=sa",
                "spring.datasource.password=",
                "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        }
)
@Transactional
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Test
    void findByOwner_returnsOnlyTransactionsOfGivenOwner() {
        repository.save(new Transaction("Gehalt",      2000.0, "Income", "owner1@test.de"));
        repository.save(new Transaction("Bonus",        500.0, "Income", "owner1@test.de"));
        repository.save(new Transaction("Fremde Miete", 800.0, "Rent",   "owner2@test.de"));

        List<Transaction> result = repository.findByOwner("owner1@test.de");

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(t -> t.getOwner().equals("owner1@test.de"));
    }

    @Test
    void findByOwner_returnsEmptyList_whenOwnerHasNoTransactions() {
        repository.save(new Transaction("Gehalt", 2000.0, "Income", "owner1@test.de"));

        List<Transaction> result = repository.findByOwner("unbekannt@test.de");

        assertThat(result).isEmpty();
    }

    @Test
    void findByOwner_returnsTitlesCorrectly() {
        repository.save(new Transaction("Miete",       850.0, "Rent",   "owner1@test.de"));
        repository.save(new Transaction("Lebensmittel", 120.0, "Food",  "owner1@test.de"));
        repository.save(new Transaction("Gehalt",      3000.0, "Income", "owner2@test.de"));

        List<Transaction> result = repository.findByOwner("owner1@test.de");

        assertThat(result)
                .extracting(Transaction::getTitle)
                .containsExactlyInAnyOrder("Miete", "Lebensmittel");
    }
}
