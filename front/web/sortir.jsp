<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="mapping.*, java.util.List" %>
<%
    List<Magasins> magasins = (List<Magasins>) request.getAttribute("magasins");
    List<Articles> articles = (List<Articles>) request.getAttribute("articles");
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
          <a class="nav-link" href="etat">ETAT STOCK</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="sortir" aria-current="page"
            >SORTIR STOCK</a
          >
        </li>
        <li class="nav-item">
          <a class="nav-link" href="valider"
            >VALIDER STOCK</a
          >
        </li>
      </ul>

      <h1 class="mb-3">Sortie de stock</h1>

      <form class="row" action="sortir" method="post">
        <div class="col-lg-4 col-md-6">
          <div class="mb-3">
            <label for="" class="form-label">Magasin (*)</label>
            <select class="form-select" name="id_magasin" required>
              <% for(Magasins magasin : magasins) { %>
              <option value="<%= magasin.getId() %>"><%= magasin.getNom() %></option>
              <% } %>
            </select>
          </div>
          <div class="mb-3">
            <label for="" class="form-label">Article (*)</label>
            <select class="form-select" name="id_article" required>
              <% for(Articles article : articles) { %>
              <option value="<%= article.getId() %>"><%= article.getNom() %></option>
              <% } %>
            </select>
          </div>
          <div class="mb-3">
            <label for="" class="form-label">Quantité (*)</label>
            <input
              type="text"
              class="form-control"
              name="quantite_sortie"
              placeholder="Quantité"
              required
            />
          </div>
          <div class="mb-3">
            <label for="" class="form-label">Date de sortie</label>
            <input
              type="date"
              class="form-control"
              name="date_sortie"
              required
            />
          </div>
          <button type="submit" class="btn btn-outline-primary mt-1 w-100">
            Valider
          </button>
        </div>
      </form>
    </div>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
