select (
    select
        coalesce(sum(quantite), 0)
    from
        mouvements as entrees
    where
        id_type_mouvement = 1
        and id_magasin = 1
        and id_article = 1
        and date_mouvement <= '2023-10-03'
    ) - (
    select
        coalesce(sum(quantite), 0)
    from
        mouvements as sorties
    where
        id_type_mouvement = 2
        and id_magasin = 1
        and id_article = 1
        and etat = 20
        and date_mouvement <= '2023-10-03'
) as quantite_restante;

--------------------------------------------------------------

select (
    select
        coalesce(sum(quantite), 0)
    from
        mouvements as entrees
    where
        id_type_mouvement = 1
        and id_magasin = 1
        and id_article = 1
        and date_mouvement <= '2023-11-02'
    ) - (
    select
        coalesce(sum(quantite), 0)
    from
        mouvements as sorties
    join
        validations v on sorties.id = v.id_mouvement
    where
        id_type_mouvement = 2
        and id_magasin = 1
        and id_article = 1
        and v.date_validation <= '2023-11-02'
) as quantite_restante;


select (
    select
        coalesce(quantite, 0)
    from
        mouvements as entrees
    where
        id = 1
        and date_mouvement <= '2023-11-03'
    ) - (
    select
        coalesce(sum(sd.quantite), 0)
    from
        sortie_decomposes as sd
    join mouvements as sorties on sorties.id = sd.id_mouvement_sortie
    join validations as v on sorties.id = v.id_mouvement
    where
        sd.id_mouvement_entree = 1
        and v.date_validation <= '2023-11-03'
) as quantite_restante;