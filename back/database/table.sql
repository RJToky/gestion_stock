create database gestion_stock;
\c gestion_stock

drop table unites cascade;
drop table methodes cascade;
drop table articles cascade;
drop table magasins cascade;
drop table type_mouvements cascade;
drop table mouvements cascade;
drop table validations cascade;
drop table sortie_decomposes cascade;

create table unites(
    id serial primary key,
    nom varchar(50) not null,
    abrev varchar(10)
);

create table methodes(
    id serial primary key,
    nom varchar(50) not null
);

create table articles(
    id serial primary key,
    numero varchar(255) unique not null,
    nom varchar(255) not null,
    id_unite int references unites(id),
    id_methode int references methodes(id)
);

create table magasins(
    id serial primary key,
    nom varchar(50) not null
);

create table type_mouvements(
    id serial primary key,
    nom varchar(50) not null
);

create table mouvements(
    id serial primary key,
    id_type_mouvement int references type_mouvements(id),
    id_magasin int references magasins(id),
    id_article int references articles(id),
    quantite double precision not null,
    prix_unitaire double precision, -- null pour mouvement sortie
    date_mouvement date not null,
    etat int not null -- 1 : Annulé | 10 : Saisie | 20 : Validé
);

create table validations(
    id serial primary key,
    id_mouvement int references mouvements(id),
    date_validation date not null
);

create table sortie_decomposes(
    id serial primary key,
    id_mouvement_sortie int references mouvements(id),
    id_mouvement_entree int references mouvements(id),
    quantite double precision not null
);
