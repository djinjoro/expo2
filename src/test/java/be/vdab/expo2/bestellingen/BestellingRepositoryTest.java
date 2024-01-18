package be.vdab.expo2.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
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
    private final JdbcClient jdbcClient;

    private int idVanBestelling(){
        return jdbcClient.sql("select id from bestellingen where naam = 'jos'")
                .query(Integer.class).single();
    }

    BestellingRepositoryTest(BestellingRepository bestellingRepository, JdbcClient jdbcClient) {
        this.bestellingRepository = bestellingRepository;
        this.jdbcClient = jdbcClient;
    }
    @Test
    void findAllGeeftAlleBestellingenGesorteerdOpNaam(){
        var aantalRecords = JdbcTestUtils.countRowsInTable(jdbcClient, BESTELLINGEN_TABLE);
        assertThat(bestellingRepository.findAll())
                .hasSize(aantalRecords)
                .extracting(Bestelling::getNaam)
                .isSorted();
    }
    @Test
    void findByIdGeeftDeCorrecteNaam() {
        assertThat(bestellingRepository.findById(idVanBestelling())).hasValueSatisfying(
                festival -> assertThat(festival.getNaam()).isEqualTo("jos"));
    }

}