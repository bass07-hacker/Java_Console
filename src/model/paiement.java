package model;

public class Paiement extends Operation {
    private Compte compteSource;
    private String nomMarchand;

    public Paiement() {
        super("PAIEMENT");
    }

    public Paiement(double montant, Compte compteSource, String nomMarchand) {
        super("PAIEMENT", montant);
        this.compteSource = compteSource;
        this.nomMarchand  = nomMarchand;
    }

    public Compte getCompteSource()                    { return compteSource; }
    public void   setCompteSource(Compte compteSource) { this.compteSource = compteSource; }

    public String getNomMarchand()                   { return nomMarchand; }
    public void   setNomMarchand(String nomMarchand) { this.nomMarchand = nomMarchand; }
}
