package be.vdab.expo2.bestellingen;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BestellingTest {
    @Test
    void eenLegeNaamGeeftEenException(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                ()-> new Bestelling(90, "", 3));
    }
    @Test
    void ticketType0BesteldGeeftEenException(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                ()-> new Bestelling(90, "bob", 0));
    }
    @Test
    void ticketType4BesteldGeeftEenException(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                ()-> new Bestelling(90, "bob", 4));
    }
    @Test
    void ticketType2BesteldGeeftGeenException(){
        new Bestelling(900, "obob", 2);
    }
}
