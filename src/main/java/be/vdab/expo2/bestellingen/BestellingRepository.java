package be.vdab.expo2.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class BestellingRepository {
    private final JdbcClient jdbcClient;

    public BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    public Optional<Bestelling> findById(int id) {
        try {
            var sql = """
                    select id, naam, ticketType
                    from bestellingen
                    where id = ?
                    """;
            return jdbcClient.sql(sql)
                    .param(id)
                    .query(Bestelling.class)
                    .optional();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        }

    public Optional<Bestelling> findAndLockById(int id) {
        try {
            var sql = """
                    select id, naam, ticketType
                    from Bestellingen
                    where id = ?
                    for update
                    """;
            return jdbcClient.sql(sql)
                    .param(id)
                    .query(Bestelling.class)
                    .optional();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Bestelling> findAll() {
        var sql = """
                select id, naam, ticketType
                from Bestellingen
                order by naam
                """;
        return jdbcClient.sql(sql)
                .query(Bestelling.class)
                .list();
    }

    public void delete(int id) {
        var sql = """
                delete from Bestellingen
                where id = ?
                """;
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }

    public void update(Bestelling bestelling) {
        var sql = """
                update Bestellingen
                set naam = ?, ticketType = ?
                where id = ?
                """;
        if (jdbcClient.sql(sql)
                .params(bestelling.getNaam(), bestelling.getTicketType(), bestelling.getId())
                .update() == 0) {
            throw new BestellingNietGevondenException();
        }
    }

    public int create(Bestelling bestelling) {
        var sql = """
                insert into Bestellingen(naam, ticketType)
                values(?,?)
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .params(bestelling.getNaam(), bestelling.getTicketType())
                .update(keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}