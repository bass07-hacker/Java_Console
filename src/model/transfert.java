package model;

public class Transfert extends Operation {
    private Compte compteSource;
    private Compte compteDestination;

    public Transfert() {
        super("TRANSFERT");
    }

    public Transfert(double montant, Compte compteSource, Compte compteDestination) {
        super("TRANSFERT", montant);
        this.compteSource      = compteSource;
        this.compteDestination = compteDestination;
    }

    public Compte getCompteSource()                        { return compteSource; }
    public void   setCompteSource(Compte compteSource)     { this.compteSource = compteSource; }

    public Compte getCompteDestination()                         { return compteDestination; }
    public void   setCompteDestination(Compte compteDestination) { this.compteDestination = compteDestination; }
}
