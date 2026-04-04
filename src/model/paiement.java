package model;
public class Paiement {
    private int Numero_compte;
    private String Nom_Marchand;
    private double montant;
    private String date;
    private String type="Paiement";

    public Paiement() {
    }

    public Paiement(int source, int destination, double montant, String date) {
        this.source = source;
        this.destination = destination;
        this.montant = montant;
        this.date = date;
    }

    public int getSource() {
        return source;
    }
    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }
    public void setDestination(int destination) {
        this.destination = destination;
    }

    public double getMontant() {
        return montant;
    }
    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}