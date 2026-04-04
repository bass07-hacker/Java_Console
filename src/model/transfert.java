package model;
public class Transfert {
    private int source;
    private int destination;
    private double montant;
    private String date;
    private String type="Transfert";

    public Transfert() {
    }

    public Transfert(int source, int destination, double montant, String date) {
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