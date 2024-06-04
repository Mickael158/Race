\c postgres;
drop database race;
create database race;
\c race;
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
    ('Homme' , 'M'),
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
CREATE TABLE penalite(
  idPenalite SERIAL PRIMARY KEY ,
  idEtape int references etape(idEtape),
  idEquipe int references equipe(idEquipe),
  temps TIME
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
    point int,
    penalite TIME,
    tfinal timestamp
);

select classement_etape.idClassement, coureur.idCoureur, coureur.nom, equipe.idEquipe , equipe.nom, classement_etape.idetape, etape.lieu, classement_etape.fin, classement_etape.debut, EXTRACT(EPOCH FROM (classement_etape.tfinal - classement_etape.debut)) AS time_difference , classement_etape.point , classement_etape.place , classement_etape.penalite
    from classement_etape
        join coureur on coureur.idCoureur=classement_etape.idCoureur
        join equipe on equipe.idEquipe=coureur.idEquipe
        join etape on etape.idEtape=classement_etape.idEtape
        left join penalite on equipe.idEquipe = penalite.idEquipe and etape.idEtape = penalite.idEtape
    where classement_etape.idEtape=4 and classement_etape.idCategorie=1
    ORDER BY point DESC;

SELECT
        re.idResultat,
        re.idCoureur,
        coureur.nom,
        equipe.idEquipe,
        equipe.nom,
        re.idetape,
        re.fin,
        e.depart,
        EXTRACT(EPOCH FROM (re.fin - e.depart)) AS time_difference,
        EXTRACT(EPOCH FROM (re.fin + COALESCE(SUM(penalite.temps), '00:00:00'::interval))) AS time_difference_final,
        COALESCE(SUM(penalite.temps), '00:00:00'::interval) AS penalite_t,
        (re.fin + COALESCE(SUM(penalite.temps), '00:00:00'::interval)) AS final_time_with_penalty
    FROM  resultat_etape re
        JOIN  etape e ON re.idetape = e.idEtape
        JOIN  coureur ON coureur.idCoureur = re.idCoureur
        JOIN  equipe ON equipe.idEquipe = coureur.idEquipe
        LEFT JOIN  penalite ON penalite.idEquipe = equipe.idEquipe AND penalite.idEtape = e.idEtape
    GROUP BY  re.idResultat, re.idCoureur, coureur.nom, equipe.idEquipe, re.idetape, e.depart
    ORDER BY time_difference_final ASC;

SELECT
    re.idResultat,
    re.idCoureur,
    coureur.nom,
    equipe.idEquipe,
    equipe.nom,
    re.idetape,
    re.fin,
    e.depart,
    CASE
        WHEN re.fin IS NULL THEN NULL
        ELSE EXTRACT(EPOCH FROM (re.fin - e.depart))
        END AS time_difference,
    CASE
        WHEN re.fin IS NULL THEN NULL
        ELSE EXTRACT(EPOCH FROM (re.fin + COALESCE(SUM(penalite.temps), '00:00:00'::interval)))
        END AS time_difference_final,
    COALESCE(SUM(penalite.temps), '00:00:00'::interval) AS penalite_t,
    CASE
        WHEN re.fin IS NULL THEN NULL
        ELSE (re.fin + COALESCE(SUM(penalite.temps), '00:00:00'::interval))
        END AS final_time_with_penalty
FROM resultat_etape re
         JOIN etape e ON re.idetape = e.idEtape
         JOIN coureur ON coureur.idCoureur = re.idCoureur
         JOIN equipe ON equipe.idEquipe = coureur.idEquipe
         LEFT JOIN penalite ON penalite.idEquipe = equipe.idEquipe AND penalite.idEtape = e.idEtape
GROUP BY re.idResultat, re.idCoureur, coureur.nom, equipe.idEquipe, re.idetape, e.depart
ORDER BY time_difference_final ASC;
