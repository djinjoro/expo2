package be.vdab.expo2.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Import(BestellingRepository.class)
@Sql("/bestellingen.sql")
class BestellingRepositoryTest {
    private  static final String BESTELLINGEN_TABLE = "bestellingen";
    private final BestellingRepository bestellingRepository;


    BestellingRepositoryTest(BestellingRepository bestellingRepository) {
        this.bestellingRepository = bestellingRepository;
    }


    @Test
    public void test(){

    }

    @Test
    void findById() {
        bestellingRepository.findById(1);
    }

    @Test
    void findAll() {
    }
}