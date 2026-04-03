CREATE DATABASE Mobile_Money;
USE Mobile_Money;

CREATE TABLE CLIENT (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    telephone VARCHAR(20),
    adresse VARCHAR(255)
);

CREATE TABLE COMPTE (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero_compte VARCHAR(50) UNIQUE,
    solde DECIMAL(10,2),
    client_id INT,
    
    FOREIGN KEY (client_id) REFERENCES CLIENT(id)
);

CREATE TABLE OPERATION (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type_operation VARCHAR(50),
    montant DECIMAL(10,2),
    date_operation DATE,
    compte_source INT,
    compte_destination INT,
    marchand VARCHAR(100),

    FOREIGN KEY (compte_source) REFERENCES COMPTE(id),
    FOREIGN KEY (compte_destination) REFERENCES COMPTE(id)
);