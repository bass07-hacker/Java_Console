package model;
public class Client (){
        private int id;
        private int Numero_telephone;
        private String nom;
        private String prenom;
        private String adresse;


        public Client() {
        }

        public Client(int id, String nom, String prenom, int Numero_telephone,String adresse) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.Numero_telephone = Numero_telephone;
            this.adresse = adresse;     
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }
        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }
        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public int getNumero_telephone() {
            return Numero_telephone;
        }
        public void setNumero_telephone(int Numero_telephone) {
            this.Numero_telephone = Numero_telephone;
        }

        public String getAdresse() {
            return adresse;
        }
        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

}
   
// @Override
//     public String toString() {
//         return "Client{" +
//             "id=" + id +
//             ", nom='" + nom + '\'' +
//             ", prenom='" + prenom + '\'' +
//             ", telephone='" + telephone + '\'' +
//             ", adresse='" + adresse + '\'' +
//             '}';
//     }