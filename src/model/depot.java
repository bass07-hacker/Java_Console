package model;

public class Depot extends Operation {
    private Compte compteDestination;

    public Depot() {
        super("DEPOT");
    }

    public Depot(double montant, Compte compteDestination) {
        super("DEPOT", montant);
        this.compteDestination = compteDestination;
    }

    public Compte getCompteDestination()                         { return compteDestination; }
    public void   setCompteDestination(Compte compteDestination) { this.compteDestination = compteDestination; }
}
