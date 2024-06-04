CREATE TABLE UserAdmin(
    idUser SERIAL PRIMARY KEY ,
    nom VARCHAR,
    prenom VARCHAR,
    email VARCHAR UNIQUE NOT NULL ,
    pswd VARCHAR NOT NULL
);
INSERT INTO UserAdmin ( nom, prenom, email, pswd) VALUES
    ('Admin' , 'Mick' , 'admin@test.com' , 'qwerty');

CREATE TABLE equipe(
   idEquipe SERIAL PRIMARY KEY ,
   nom VARCHAR UNIQUE,
   email VARCHAR UNIQUE ,
   pswd VARCHAR NOT NULL
);
CREATE TABLE categories(
    idCategorie SERIAL PRIMARY KEY ,
    nom VARCHAR
);
INSERT INTO categories (nom) VALUES
    ('Homme'),
    ('Femme'),
    ('Junior');

CREATE TABLE genre(
  idGenre SERIAL PRIMARY KEY ,
  nom VARCHAR,
  anotation VARCHAR
);
INSERT INTO genre (nom , anotation) VALUES
    ('Homme' , 'H'),
    ('Femme' , 'F');
CREATE TABLE coureur(
    idCoureur SERIAL PRIMARY KEY ,
    numero VARCHAR UNIQUE ,
    nom VARCHAR,
    dtn DATE,
    idGenre int references genre(idGenre),
    idEquipe int references equipe(idEquipe)
);

CREATE TABLE coureurs_categories(
  idCoureur_cat SERIAL PRIMARY KEY ,
  idCoureur INT references coureur(idCoureur),
  idCategories int references categories(idCategorie)
);

CREATE TABLE etape(
  idEtape SERIAL PRIMARY KEY ,
  rang int,
  nbr_Coureur_Equipe int,
  depart TIMESTAMP,
  lieu VARCHAR,
  longueur double precision
);

CREATE TABLE Composition_etape(
    idComposition SERIAL PRIMARY KEY,
    idEtape int REFERENCES etape(idEtape),
    idEquipe int REFERENCES equipe(idEquipe),
    idCoureur int REFERENCES coureur(idCoureur)
);
CREATE TABLE points(
  idPoint SERIAL PRIMARY KEY ,
  place int,
  point int
);

CREATE TABLE resultat_etape(
  idResultat SERIAL PRIMARY KEY ,
  idCoureur int references coureur(idCoureur),
  idetape int references etape(idEtape),
  fin timestamp
);

CREATE TABLE resultat_import(
    idri SERIAL PRIMARY KEY ,
    rang int,
    numero VARCHAR,
    nom VARCHAR,
    genre VARCHAR,
    dtn DATE,
    equipe VARCHAR,
    arriver TIMESTAMP
);

CREATE TABLE classement_etape(
    idClassement SERIAL PRIMARY KEY ,
    idresultat int references resultat_etape(idResultat),
    place int,
    idCategorie int DEFAULT NULL,
    idCoureur int references coureur(idCoureur),
    idEtape int references etape(idEtape),
    debut timestamp,
    fin timestamp,
    point int
);

INSERT INTO equipe (nom,email, pswd) VALUES
                                         ('EquipeA','equipe1@test.com', 'azerty1'),
                                         ('EquipeB','equipe2@test.com', 'azerty2'),
                                         ('EquipeC','equipe3@test.com', 'azerty3');



INSERT INTO coureur (numero, nom, dtn, idGenre, idEquipe) VALUES
                                                              ('101', 'Jean Martin', '1990-05-15', 1, 1),
                                                              ('102', 'Sophie Durand', '1992-08-10', 2, 1),
                                                              ('103', 'Pierre Lefebvre', '1998-11-25', 1, 1),
                                                              ('201', 'Marie Petit', '1993-06-14', 2, 2),
                                                              ('202', 'Louis Robert', '1996-09-30', 1, 2),
                                                              ('203', 'Emma Dubois', '1994-04-20', 2, 2),
                                                              ('301', 'Thomas Moreau', '1991-03-28', 1, 3),
                                                              ('302', 'Léa Garcia', '1995-12-03', 2, 3),
                                                              ('303', 'Nicolas Fernandez', '1997-07-18', 1, 3),
                                                              ('401', 'Alice Roux', '1993-09-05', 2, 1),
                                                              ('402', 'Julien Bonnet', '1994-10-12', 1, 2),
                                                              ('403', 'Camille Laurent', '1996-02-25', 2, 3);
INSERT INTO coureurs_categories (idCoureur, idCategories) VALUES
                                                              (1, 1),
                                                              (2, 2),
                                                              (3, 3),
                                                              (4, 4),
                                                              (5, 1),
                                                              (6, 3),
                                                              (7, 2),
                                                              (8, 1),
                                                              (9, 3),
                                                              (10, 2),
                                                              (11, 4),
                                                              (12, 1);
INSERT INTO etape (nbr_Coureur_Equipe, depart, lieu, longueur) VALUES
                                                                   (8, '2024-06-01 08:00:00', 'Prologue', 5.0),
                                                                   (6, '2024-06-02 09:00:00', 'Etape 1', 120.0),
                                                                   (7, '2024-06-03 08:30:00', 'Etape 2', 180.0),
                                                                   (5, '2024-06-04 09:00:00', 'Etape 3', 150.0);
INSERT INTO points (place, point) VALUES
                                      (1, 100),
                                      (2, 95),
                                      (3, 90),
                                      (4, 85),
                                      (5, 80),
                                      (6, 75),
                                      (7, 70),
                                      (8, 65),
                                      (9, 60),
                                      (10, 55),
                                      (11, 50),
                                      (12, 45),
                                      (13, 40),
                                      (14, 35),
                                      (15, 30),
                                      (16, 25),
                                      (17, 20),
                                      (18, 15),
                                      (19, 10),
                                      (20, 5),
                                      (21, 4),
                                      (22, 3),
                                      (23, 2),
                                      (24, 1),
                                      (25, 1),
                                      (26, 1),
                                      (27, 1),
                                      (28, 1),
                                      (29, 1),
                                      (30, 1);