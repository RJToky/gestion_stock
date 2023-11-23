<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="mapping.*, spec.*, java.util.List, java.util.ArrayList" %>
<%
    EtatStock etatStock = (EtatStock) request.getAttribute("etat_stock");
    List<Magasins> magasins = (List<Magasins>) request.getAttribute("magasins");
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Gestion stock</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="./assets/css/style.css" />
  </head>
  <body>
    <div class="container py-5">
      <ul class="nav nav-tabs mb-4">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="etat"
            >ETAT STOCK</a
          >
        </li>
        <li class="nav-item">
          <a class="nav-link" href="sortir">SORTIR STOCK</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="valider"
            >VALIDER STOCK</a
          >
        </li>
      </ul>

      <h1 class="mb-3">Etat de stock</h1>

      <form class="row align-items-end" action="etat" method="get">
        <div class="col-lg-3 col-4">
          <label for="" class="form-label">Date debut</label>

          <% if(etatStock != null) { %>
            <input type="date" class="form-control" name="date_debut" value="<%= etatStock.getDateDebut() %>" required />
          <% } else { %>
            <input type="date" class="form-control" name="date_debut" value="" required />
          <% } %>

        </div>
        <div class="col-lg-3 col-4 ps-0">
          <label for="" class="form-label">Date fin</label>

          <% if(etatStock != null) { %>
            <input type="date" class="form-control" name="date_fin" value="<%= etatStock.getDateFin() %>" required />
          <% } else { %>
            <input type="date" class="form-control" name="date_fin" value="" required />
          <% } %>

        </div>
        <div class="col-lg-3 col-4 ps-0">
          <label for="" class="form-label">Magasin</label>
          <select class="form-select" name="id_magasin" required>
            <% int idMagasin = request.getAttribute("id_magasin") != null ? (int) request.getAttribute("id_magasin") : 0; %>
            
            <% if(idMagasin == 0) { %>
              <option value="0" selected>Tous les magasins</option>
            <% } else { %>
              <option value="0">Tous les magasins</option>
            <% } %>

            <% for(Magasins magasin : magasins) { %>
              <% if(idMagasin == magasin.getId()) { %>
                <option value="<%= magasin.getId() %>" selected><%= magasin.getNom() %></option>
              <% } else { %>
                <option value="<%= magasin.getId() %>"><%= magasin.getNom() %></option>
              <% } %>
            <% } %>

          </select>
        </div>
        <div class="col-auto ps-lg-0 mt-lg-0 mt-3">
          <button type="submit" class="btn btn-outline-primary">Valider</button>
        </div>
      </form>

      <hr class="my-4" />

      <% if(etatStock != null) { %>
        <div class="row mb-4">
          <p class="mb-0"><b>Date debut : </b><%= etatStock.getDateDebut() %></p>
          <p class="mb-0"><b>Date fin : </b><%= etatStock.getDateFin() %></p>
        </div>

        <div class="table-responsive mb-3">
          <table class="table">
            <thead class="table-light">
              <tr>
                <th>Magasin</th>
                <th>Numéro article</th>
                <th>Nom article</th>
                <th>Unité</th>
                <th>Quantité initiale</th>
                <th>Quantité restante</th>
                <th>Prix unitaire</th>
                <th>Montant</th>
              </tr>
            </thead>
            <tbody>
              <% for(Stock stock : etatStock.getStocks()) { %>
              <tr>
                <td><%= stock.getMagasin().getNom() %></td>
                <td><%= stock.getArticle().getNumero() %></td>
                <td><%= stock.getArticle().getNom() %></td>
                <td><%= stock.getArticle().getUnite().getAbrev() %></td>
                <td><%= stock.getQuantiteInitiale() %></td>
                <td><%= stock.getQuantiteRestante() %></td>
                <td><%= stock.getPrixUnitaire() + " Ar" %></td>
                <td><%= stock.getMontantTotal() + " Ar" %></td>
              </tr>
              <% } %>
            </tbody>
          </table>
        </div>

        <div class="d-flex w-100 justify-content-end">
          <div class="card">
            <div class="card-body"><b>TOTAL : </b><%= etatStock.getMontantTotal() + " Ar" %></div>
          </div>
        </div>
      <% } %>

    </div>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
