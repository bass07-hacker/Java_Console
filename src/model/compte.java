package model;
public class Client {
        private int id;
        private int Numero_compte;
        private int solde;
        private String nom_Client;



        public Compte() {
        }

        public Compte(int id, String nom_Client, int Numero_compte, int solde) {
            this.id = id;
            this.nom_Client = nom_Client;
            this.Numero_compte = Numero_compte;
            this.solde = solde;
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }

        public String getNom_Client() {
            return nom_Client;
        }
        public void setNom_Client(String nom_Client) {
            this.nom_Client = nom_Client;
        }

        public int getNumero_compte() {
            return Numero_compte;
        }
        public void setNumero_compte(int Numero_compte) {
            this.Numero_compte = Numero_compte;
        }

        public int getSolde() {
            return solde;
        }
        public void setSolde(int solde) {
            this.solde = solde;
        }

        

}
   


