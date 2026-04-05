package model;

public class Retrait extends Operation {
    private Compte compteSource;

    public Retrait() {
        super("RETRAIT");
    }

    public Retrait(double montant, Compte compteSource) {
        super("RETRAIT", montant);
        this.compteSource = compteSource;
    }

    public Compte getCompteSource()                    { return compteSource; }
    public void   setCompteSource(Compte compteSource) { this.compteSource = compteSource; }
}
