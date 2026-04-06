package model;

public class Compte {
    private int    id;
    private String numeroCompte;
    private double solde;
    private Client client;
    private String typeCompte;   // "CLIENT" ou "MARCHAND"

    public Compte() {}

    public Compte(String numeroCompte, double solde, Client client, String typeCompte) {
        this.numeroCompte = numeroCompte;
        this.solde        = solde;
        this.client       = client;
        this.typeCompte   = typeCompte;
    }

    public Compte(int id, String numeroCompte, double solde, Client client, String typeCompte) {
        this.id           = id;
        this.numeroCompte = numeroCompte;
        this.solde        = solde;
        this.client       = client;
        this.typeCompte   = typeCompte;
    }

    // Compatibilité — CLIENT par défaut
    public Compte(String numeroCompte, double solde, Client client) {
        this(numeroCompte, solde, client, "CLIENT");
    }

    public int    getId()          { return id; }
    public void   setId(int id)    { this.id = id; }

    public String getNumeroCompte()                    { return numeroCompte; }
    public void   setNumeroCompte(String numeroCompte) { this.numeroCompte = numeroCompte; }

    public double getSolde()             { return solde; }
    public void   setSolde(double solde) { this.solde = solde; }

    public Client getClient()              { return client; }
    public void   setClient(Client client) { this.client = client; }

    public String getTypeCompte()                  { return typeCompte; }
    public void   setTypeCompte(String typeCompte) { this.typeCompte = typeCompte; }

    public boolean isMarchand() { return "MARCHAND".equalsIgnoreCase(typeCompte); }

    @Override
    public String toString() {
        return "Compte{id=" + id + ", numero='" + numeroCompte +
               "', solde=" + solde + ", type='" + typeCompte + "'}";
    }
}
