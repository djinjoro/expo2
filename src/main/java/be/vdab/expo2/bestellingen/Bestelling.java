package be.vdab.expo2.bestellingen;

public class Bestelling {
    private final int id;
    private final String naam;
    private final int ticketType;

    public Bestelling(int id, String naam, int ticketType) {
        if(id <= 0){
            throw new IllegalArgumentException("id moet positief zijn");
        }
        if(naam.isEmpty()){
            throw new IllegalArgumentException("naam mag niet leeg zijn");
        }
        if(ticketType < 1 || ticketType > 3 ){
            throw new IllegalArgumentException("ticketType moet 1, 2 of 3 zijn");
        }
        this.id = id;
        this.naam = naam;
        this.ticketType = ticketType;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getTicketType() {
        return ticketType;
    }
}
