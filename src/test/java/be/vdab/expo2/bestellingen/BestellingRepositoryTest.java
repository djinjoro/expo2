package be.vdab.expo2.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.*;


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
                bestelling -> assertThat(bestelling.getNaam()).isEqualTo("jos"));
    }
    @Test
    void findByOnbestaandIdGeeftFoutmelding(){
        assertThat(bestellingRepository.findById(Integer.MAX_VALUE)).isEmpty();
    }
    @Test
    void findAndLockByIdMetCorrecteIdWerkt(){
        assertThat(bestellingRepository.findAndLockById(idVanBestelling())).hasValueSatisfying(
                bestelling -> assertThat(bestelling.getNaam()).isEqualTo("jos"));
    }
    @Test
    void findAndLockByIdMetIncorrecteIdWerktNiet(){
        assertThat(bestellingRepository.findAndLockById(Integer.MAX_VALUE)).isEmpty();
    }
    @Test
    void deleteVerwijdertBestelling(){
        var id = idVanBestelling();
        bestellingRepository.delete(id);
        var aantalRecordsMetIdVanVerwijderdeBestelling = JdbcTestUtils.countRowsInTableWhere(jdbcClient, BESTELLINGEN_TABLE, "id =" + id);
        assertThat(aantalRecordsMetIdVanVerwijderdeBestelling).isZero();
    }
    @Test
    void createVoegtBestellingToe(){
        var id = bestellingRepository.create(new Bestelling(30, "test99", 2));
        assertThat(id).isPositive();
        var aantalRecordsMetIdVanToegevoegdeBestelling = JdbcTestUtils.countRowsInTableWhere(jdbcClient, BESTELLINGEN_TABLE, "id =" + id);
        assertThat(aantalRecordsMetIdVanToegevoegdeBestelling).isOne();
    }
    @Test
    void UpdateWijzigtEenMens(){
        var id = idVanBestelling();
        var bestelling = new Bestelling(id, "Ace", 2);
        bestellingRepository.update(bestelling);
        var aantalAangepasteRecords = JdbcTestUtils.countRowsInTableWhere(
                jdbcClient, BESTELLINGEN_TABLE, "naam = 'Ace' and id = " + id);
                assertThat(aantalAangepasteRecords).isOne();
    }
}