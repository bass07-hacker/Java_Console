package model;

public class Compte {
    private int    id;
    private String numeroCompte;   // String, pas int
    private double solde;          // double, pas int
    private Client client;         // référence Client, pas juste le nom

    public Compte() {}

    public Compte(String numeroCompte, double solde, Client client) {
        this.numeroCompte = numeroCompte;
        this.solde        = solde;
        this.client       = client;
    }

    public Compte(int id, String numeroCompte, double solde, Client client) {
        this.id           = id;
        this.numeroCompte = numeroCompte;
        this.solde        = solde;
        this.client       = client;
    }

    public int    getId()          { return id; }
    public void   setId(int id)    { this.id = id; }

    public String getNumeroCompte()                    { return numeroCompte; }
    public void   setNumeroCompte(String numeroCompte) { this.numeroCompte = numeroCompte; }

    public double getSolde()             { return solde; }
    public void   setSolde(double solde) { this.solde = solde; }

    public Client getClient()              { return client; }
    public void   setClient(Client client) { this.client = client; }

    @Override
    public String toString() {
        return "Compte{id=" + id + ", numero='" + numeroCompte +
               "', solde=" + solde + "}";
    }
}
