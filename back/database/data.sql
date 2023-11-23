insert into unites values
    (default, 'Kilogramme', 'kg'),
    (default, 'Pièce', 'pcs');

insert into methodes values
    (default, 'FIFO'),
    (default, 'LIFO');

insert into articles values
    (default, 'RIZ001', 'Vary mena', 1, 1),
    (default, 'RIZ0011', 'Vary manalalondo', 1, 2),
    (default, 'RIZ002', 'Vary fotsy', 1, 2),
    (default, 'RIZ0021', 'Vary makalioka', 1, 1);

insert into magasins values
    (default, 'Magasin M'),
    (default, 'Tsena maitso');

insert into type_mouvements values
    (default, 'Entree'),
    (default, 'Sortie');

-- entrees
insert into mouvements values
    (default, 1, 1, 1, 500, 3000, '2023-10-01', 20),
    (default, 1, 1, 1, 300, 3500, '2023-10-03', 20),
    (default, 1, 2, 2, 400, 3100, '2023-10-04', 20),
    (default, 1, 2, 2, 300, 4000, '2023-10-10', 20);

-- sorties
insert into mouvements values
    (default, 2, 1, 1, 400, null, '2023-10-02', 10),
    (default, 2, 1, 1, 300, null, '2023-10-20', 10),
    (default, 2, 1, 1, 100, null, '2023-10-31', 10);

insert into mouvements values
    (default, 1, 1, 1, 500, 3000, '2023-10-01', 20),
    (default, 2, 1, 1, 400, null, '2023-10-02', 10),
    (default, 1, 1, 1, 300, 3500, '2023-10-03', 20),
    (default, 1, 2, 2, 400, 3100, '2023-10-04', 20),
    (default, 1, 2, 2, 300, 4000, '2023-10-10', 20),
    (default, 2, 1, 1, 300, null, '2023-10-20', 10),
    (default, 2, 1, 1, 100, null, '2023-10-31', 10);

----------------------------------------------------------

insert into validations values
    (default, 2, '2023-11-01'),
    (default, 6, '2023-11-02'),
    (default, 7, '2023-11-03');

insert into sortie_decomposes values
    (default, 2, 1, 400),
    (default, 6, 1, 100),
    (default, 6, 3, 200),
    (default, 7, 3, 100);

update mouvements set etat = 20 where id = 2;
update mouvements set etat = 20 where id = 6;
update mouvements set etat = 20 where id = 7;