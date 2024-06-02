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
INSERT INTO equipe (nom,email, pswd) VALUES
    ('Les Foudres','equipe1@test.com', 'azerty1'),
    ('Les Cheetahs','equipe2@test.com', 'azerty2'),
    ('Les Guerriers','equipe3@test.com', 'azerty3'),
    ('Les Eclairs','equipe4@test.com', 'azerty4'),
    ('Les Lynx','equipe5@test.com', 'azerty5');
CREATE TABLE categories(
    idCategorie SERIAL PRIMARY KEY ,
    nom VARCHAR
);
INSERT INTO categories (nom) VALUES
    ('Homme'),
    ('Femme'),
    ('Junior'),
    ('Senior');
CREATE TABLE genre(
  idGenre SERIAL PRIMARY KEY ,
  nom VARCHAR
);
INSERT INTO genre (nom) VALUES
    ('Homme'),
    ('Femme'),
    ('Non specifie');

CREATE TABLE coureur(
    idCoureur SERIAL PRIMARY KEY ,
    numero VARCHAR UNIQUE ,
    nom VARCHAR,
    dtn DATE,
    idGenre int references genre(idGenre),
    idEquipe int references equipe(idEquipe)
);
INSERT INTO coureur (numero, nom, dtn, idGenre, idEquipe) VALUES
    ('101', 'Randrianarisoa Andry', '1990-05-14', 1, 1),
    ('100', 'Randrianarisoa Taky', '1990-05-14', 1, 1),
    ('102', 'Rasolofo Marie', '1992-08-22', 2, 2),
    ('103', 'Rakoto Jean', '1985-11-30', 1, 3),
    ('104', 'Ranaivo Hanta', '1998-01-17', 2, 4),
    ('105', 'Ravelo Eric', '1990-06-25', 1, 5);

CREATE TABLE coureurs_categories(
  idCoureur_cat SERIAL PRIMARY KEY ,
  idCoureur INT references coureur(idCoureur),
  idCategories int references categories(idCategorie)
);
INSERT INTO coureurs_categories (idCoureur, idCategories) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 1);
CREATE TABLE etape(
  idEtape SERIAL PRIMARY KEY ,
  nbr_Coureur_Equipe int,
  depart TIMESTAMP,
  lieu VARCHAR,
  longueur double precision
);
INSERT INTO etape (nbr_Coureur_Equipe, depart, lieu, longueur) VALUES
    (5, '2024-06-01 08:00:00', 'Antananarivo', 10.5),
    (4, '2024-06-02 08:00:00', 'Fianarantsoa', 12.3),
    (6, '2024-06-03 08:00:00', 'Toliara', 8.4),
    (5, '2024-06-04 08:00:00', 'Antsiranana', 15.0);

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
INSERT INTO points (place, point) VALUES
    (1, 100),
    (2, 80),
    (3, 60),
    (4, 40),
    (5, 20);

CREATE TABLE resultat_etape(
  idResultat SERIAL PRIMARY KEY ,
  idCoureur int references coureur(idCoureur),
  idetape int references etape(idEtape),
  fin timestamp
);


CREATE TABLE classement_etape(
    idClassement SERIAL PRIMARY KEY ,
    idresultat int references resultat_etape(idResultat),
    place int,
    idCoureur int references coureur(idCoureur),
    idEtape int references etape(idEtape),
    debut timestamp,
    fin timestamp,
    point int
);

select coureur.idCoureur , coureur.numero , coureur.nom , coureur.dtn , genre.nom , coureur.idEquipe
from coureur
         join genre on genre.idGenre=coureur.idGenre
where idEquipe=1 and idCoureur not in (
    select Composition_etape.idCoureur
    from Composition_etape
             join coureur on coureur.idCoureur=Composition_etape.idCoureur
             join equipe on equipe.idEquipe=Composition_etape.idEquipe
             join etape on etape.idEtape=Composition_etape.idEtape
    where etape.idEtape=1 and equipe.idEquipe=1
);

select classement_etape.idClassement, coureur.idCoureur, coureur.nom, equipe.idEquipe , equipe.nom, classement_etape.idetape, etape.lieu, classement_etape.fin, classement_etape.debut, EXTRACT(EPOCH FROM (classement_etape.fin - classement_etape.debut)) AS time_difference , classement_etape.point
    from classement_etape
        join coureur on coureur.idCoureur=classement_etape.idCoureur
        join equipe on equipe.idEquipe=coureur.idEquipe
        join etape on etape.idEtape=classement_etape.idEtape
    where classement_etape.idEtape=1
    ORDER BY point ASC;


select coureur.idCoureur , coureur.numero , coureur.nom , coureur.dtn , genre.nom , coureur.idEquipe
from Composition_etape
         join coureur on coureur.idCoureur=Composition_etape.idCoureur
         join genre on genre.idGenre=coureur.idGenre
         join equipe on equipe.idEquipe=Composition_etape.idEquipe
         join etape on etape.idEtape=Composition_etape.idEtape
where etape.idEtape=1 and equipe.idEquipe=1


select equipe.idEquipe , equipe.nom,  SUM(classement_etape.point) as point , SUM(EXTRACT(EPOCH FROM (classement_etape.fin - classement_etape.debut)))AS time_difference
    from classement_etape
        join coureur on coureur.idCoureur=classement_etape.idCoureur
        join equipe on equipe.idEquipe=coureur.idEquipe
    where equipe.idEquipe=?
    Group By equipe.idEquipe
    Order By point ASC;

select coureur.idCoureur , coureur.numero , coureur.nom , coureur.dtn , genre.nom , coureur.idEquipe
from coureur
         join genre on genre.idGenre=coureur.idGenre
where numero='101';

SELECT re.idResultat, re.idCoureur, coureur.nom, equipe.idEquipe , equipe.nom, re.idetape, re.fin, e.depart, EXTRACT(EPOCH FROM (re.fin - e.depart)) AS time_difference
FROM resultat_etape re
         JOIN etape e ON re.idetape = e.idEtape
         join coureur on coureur.idCoureur=re.idCoureur
         join equipe on equipe.idEquipe=coureur.idEquipe
ORDER BY time_difference ASC;