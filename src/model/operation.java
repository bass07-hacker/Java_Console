package model;

import java.time.LocalDate;

// ─────────────────────────────────────────────
//  Classe de base Operation
// ─────────────────────────────────────────────
public class Operation {
    private int       id;
    private String    typeOperation;
    private double    montant;
    private LocalDate dateOperation;

    public Operation() {}

    public Operation(String typeOperation) {
        this.typeOperation = typeOperation;
        this.dateOperation = LocalDate.now();
    }

    public Operation(String typeOperation, double montant) {
        this.typeOperation = typeOperation;
        this.montant       = montant;
        this.dateOperation = LocalDate.now();
    }

    public int       getId()                   { return id; }
    public void      setId(int id)             { this.id = id; }

    public String    getTypeOperation()                        { return typeOperation; }
    public void      setTypeOperation(String typeOperation)    { this.typeOperation = typeOperation; }

    public double    getMontant()                { return montant; }
    public void      setMontant(double montant)  { this.montant = montant; }

    public LocalDate getDateOperation()                        { return dateOperation; }
    public void      setDateOperation(LocalDate dateOperation) { this.dateOperation = dateOperation; }
}
