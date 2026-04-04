# 📱 Mobile Money — Application Java Console

Simulation d'un système Mobile Money en Java, avec gestion des clients, des comptes et des opérations financières (dépôt, retrait, transfert, paiement marchand). Toutes les données sont stockées dans une base de données MySQL via JDBC.

---

## 🛠️ Technologies utilisées

- Java 11+
- Programmation Orientée Objet (POO)
- MySQL 8
- JDBC (Java Database Connectivity)
- Architecture en couches : `model` / `dao` / `service` / `database` / `exception` / `ui`

---

## ✅ Prérequis

Avant de lancer le projet, assurez-vous d'avoir installé :

- [Java JDK 11 ou supérieur](https://www.oracle.com/java/technologies/downloads/)
- [MySQL 8](https://dev.mysql.com/downloads/mysql/)
- Le fichier `mysql-connector-java.jar` (connecteur JDBC) — à ajouter au classpath
- Un IDE comme IntelliJ IDEA, Eclipse, ou simplement le terminal

---

## 🚀 Installation

### 1. Cloner ou télécharger le projet

```bash
git clone https://github.com/votre-groupe/mobile-money.git
cd mobile-money
```

### 2. Créer la base de données MySQL

Ouvrez MySQL et exécutez le script SQL fourni :

```bash
mysql -u root -p < sql/schema.sql
```

Ou depuis le client MySQL :

```sql
SOURCE chemin/vers/sql/schema.sql;
```

### 3. Configurer la connexion JDBC

Ouvrez le fichier `src/database/DatabaseConnection.java` et modifiez les lignes suivantes avec vos identifiants MySQL :

```java
private static final String URL  = "jdbc:mysql://localhost:3306/mobile_money_db";
private static final String USER = "root";       // votre utilisateur MySQL
private static final String PASS = "";           // votre mot de passe MySQL
```

### 4. Ajouter le connecteur JDBC

Placez le fichier `mysql-connector-java.jar` dans un dossier `lib/` à la racine du projet, puis ajoutez-le au classpath lors de la compilation.

### 5. Compiler et lancer l'application

```bash
# Compiler
javac -cp lib/mysql-connector-java.jar -d out src/**/*.java

# Lancer
java -cp out:lib/mysql-connector-java.jar Main
```

> Sur Windows, remplacez `:` par `;` dans le classpath.

---

## 📖 Utilisation

Au lancement, un menu console s'affiche :

```
=== MOBILE MONEY ===
1 - Ajouter un client
2 - Afficher les clients
3 - Créer un compte
4 - Dépôt
5 - Retrait
6 - Transfert
7 - Paiement marchand
8 - Liste des opérations
9 - Quitter
Votre choix :
```

### Exemples de scénarios

**Ajouter un client (option 1)**
```
Votre choix : 1
Nom       : Diallo
Prénom    : Mamadou
Téléphone : 771234567
Adresse   : Touba, Sénégal
✅ Client ajouté avec succès.
```

**Créer un compte (option 3)**
```
Votre choix : 3
Téléphone du client : 771234567
✅ Compte créé. Numéro : MM-20260401-001
```

**Faire un dépôt (option 4)**
```
Votre choix : 4
Numéro de compte : MM-20260401-001
Montant          : 50000
✅ Dépôt de 50 000 FCFA effectué. Nouveau solde : 50 000 FCFA
```

**Faire un retrait (option 5)**
```
Votre choix : 5
Numéro de compte : MM-20260401-001
Montant          : 10000
✅ Retrait de 10 000 FCFA effectué. Nouveau solde : 40 000 FCFA
```

**Effectuer un transfert (option 6)**
```
Votre choix : 6
Compte source      : MM-20260401-001
Compte destination : MM-20260401-002
Montant            : 20000
✅ Transfert de 20 000 FCFA effectué avec succès.
```

**Paiement marchand (option 7)**
```
Votre choix : 7
Numéro de compte : MM-20260401-001
Nom du marchand  : Supermarché Touba
Montant          : 5000
✅ Paiement de 5 000 FCFA effectué chez Supermarché Touba.
```

**Afficher l'historique (option 8)**
```
Votre choix : 8
Numéro de compte : MM-20260401-001

--- Historique du compte MM-20260401-001 ---
[2026-04-01] DEPOT       +50 000 FCFA
[2026-04-01] RETRAIT     -10 000 FCFA
[2026-04-01] TRANSFERT   -20 000 FCFA → MM-20260401-002
[2026-04-01] PAIEMENT    -5 000 FCFA  → Supermarché Touba
```

---

## 📁 Structure du projet

```
MobileMoney/
├── src/
│   ├── model/                  # Entités métier (Client, Compte, Operation...)
│   │   ├── Client.java
│   │   ├── Compte.java
│   │   ├── Operation.java      # Classe abstraite
│   │   ├── Depot.java
│   │   ├── Retrait.java
│   │   ├── Transfert.java
│   │   └── Paiement.java
│   ├── dao/                    # Accès base de données (requêtes SQL)
│   │   ├── ClientDAO.java
│   │   ├── CompteDAO.java
│   │   └── OperationDAO.java
│   ├── service/                # Logique métier et règles de gestion
│   │   ├── ClientService.java
│   │   ├── CompteService.java
│   │   └── OperationService.java
│   ├── database/               # Connexion JDBC
│   │   └── DatabaseConnection.java
│   ├── exception/              # Exceptions personnalisées
│   │   ├── SoldeInsuffisantException.java
│   │   └── CompteIntrouvableException.java
│   └── ui/                     # Interface console (menus, saisies)
│       ├── Menu.java
│       ├── ClientMenu.java
│       └── CompteMenu.java
├── sql/
│   └── schema.sql              # Script de création de la base de données
├── lib/
│   └── mysql-connector-java.jar
└── README.md
```

---

## 👥 Auteurs

| Étudiant | Responsabilité |
|----------|---------------|
| [Nom Étudiant 1] | Gestion des clients (model/Client, ClientDAO, ClientService, ClientMenu) |
| [Nom Étudiant 2] | Gestion des comptes et opérations (Compte, Operation, CompteDAO, OperationDAO, services, menus) |
| [Nom Étudiant 3] | Base de données, intégration, Main.java, UML, README, captures d'écran |

---

## 📅 Informations

- **Projet** : Fin de semestre — Java Console
- **Date de rendu** : 10 Avril 2026
- **Établissement** : [Nom de votre école]
