package model;
public class Retrait {
    private int Numero_compte;
    private double montant;
    private String date;
    private String type="Retrait";

    public Retrait() {
    }

    public Retrait(int Numero_compte, double montant, String date) {
        this.Numero_compte = Numero_compte;
        this.montant = montant;
        this.date = date;
    }

    public int getNumero_compte() {
        return Numero_compte;
    }
    public void setNumero_compte(int Numero_compte) {
        this.Numero_compte = Numero_compte;
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