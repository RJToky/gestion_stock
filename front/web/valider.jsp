<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="mapping.*, spec.*, java.util.List" %>
<%
    List<Sorties> sorties = (List<Sorties>) request.getAttribute("sorties");
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
          <a class="nav-link" href="sortir"
            >SORTIR STOCK</a
          >
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="valider" aria-current="page"
            >VALIDER STOCK</a
          >
        </li>
      </ul>

      <h1 class="mb-3">Validation de stock</h1>

      <div class="table-responsive mb-3">
        <table class="table">
          <thead class="table-light">
            <tr>
              <th>Date de saisie</th>
              <td>Magasin</td>
              <th>Numéro article</th>
              <th>Nom article</th>
              <th>Quantité</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <% for(Sorties sortie : sorties) { %>
            <tr>
              <td><%= sortie.getDateMouvement() %></td>
              <td><%= sortie.getMagasin().getNom() %></td>
              <td><%= sortie.getArticle().getNumero() %></td>
              <td><%= sortie.getArticle().getNom() %></td>
              <td><%= sortie.getQuantite() %></td>
              <td>
                <% if(sortie.isValid()) { %>
                  <button type="button" class="btn btn-outline-success mb-lg-0 mb-1" data-bs-toggle="modal" data-bs-target="#validerModal<%= sortie.getId() %>">Valider</button>
                  <% } else { %>
                    <button type="button" class="btn btn-outline-secondary mb-lg-0 mb-1" disabled>Valider</button>
                <% } %>

                <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#annulerModal<%= sortie.getId() %>">Annuler</button>
              </td>
            </tr>
            <% } %>
          </tbody>
        </table>
      </div>

      <% for(Sorties sortie : sorties) { %>
        <% if(sortie.isValid()) { %>
          <div class="modal fade" id="validerModal<%= sortie.getId() %>"  aria-labelledby="validerModalLabel<%= sortie.getId() %>" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h1 class="modal-title fs-5" id="validerModalLabel<%= sortie.getId() %>">Valider ce mouvement</h1>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <form action="valider" method="post">
                  <div class="modal-body">
                    <input type="hidden" name="id_mouvement_sortie" value="<%= sortie.getId() %>">
                    <input type="hidden" name="action" value="1">
                    <div class="mb-3">
                      <label for="idatesortie" class="col-form-label">Date de saisie:</label>
                      <input type="date" id="idatesortie" class="form-control" value="<%= sortie.getDateMouvement() %>" disabled />
                    </div>
                    <div class="mb-3">
                      <label for="imagasin" class="col-form-label">Magasin:</label>
                      <input type="text" id="imagasin" class="form-control" value="<%= sortie.getMagasin().getNom() %>" disabled />
                    </div>
                    <div class="mb-3">
                      <label for="iarticle" class="col-form-label">Article:</label>
                      <input type="text" id="iarticle" class="form-control" value="<%= sortie.getArticle().getNom() %>" disabled />
                    </div>
                    <div class="mb-3">
                      <label for="iquantite" class="col-form-label">Quantité:</label>
                      <input type="text" id="iquantite" class="form-control" name="quantite_sortie" value="<%= sortie.getQuantite() %>" required />
                    </div>
                    <div class="mb-3">
                      <label for="idatevalidation" class="col-form-label">Date de validation:</label>
                      <input type="date" id="idatevalidation" class="form-control" name="date_validation" required />
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-transparent" data-bs-dismiss="modal">Fermer</button>
                    <button type="submit" class="btn btn-success">Valider</button>
                  </div>
                </form>

              </div>
            </div>
          </div>
        <% } %>

        <div class="modal fade" id="annulerModal<%= sortie.getId() %>"  aria-labelledby="annulerModalLabel<%= sortie.getId() %>" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="annulerModalLabel<%= sortie.getId() %>">Annuler ce mouvement</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              
              <form action="valider" method="post">
                <div class="modal-body">
                  <input type="hidden" name="id_mouvement_sortie" value="<%= sortie.getId() %>">
                  <input type="hidden" name="action" value="2">
                  <div class="mb-3">
                    <label for="idatesortie" class="col-form-label">Date de saisie:</label>
                    <input type="date" id="idatesortie" class="form-control" value="<%= sortie.getDateMouvement() %>" disabled />
                  </div>
                  <div class="mb-3">
                    <label for="imagasin" class="col-form-label">Magasin:</label>
                    <input type="text" id="imagasin" class="form-control" value="<%= sortie.getMagasin().getNom() %>" disabled />
                  </div>
                  <div class="mb-3">
                    <label for="iarticle" class="col-form-label">Article:</label>
                    <input type="text" id="iarticle" class="form-control" value="<%= sortie.getArticle().getNom() %>" disabled />
                  </div>
                  <div class="mb-3">
                    <label for="iquantite" class="col-form-label">Quantité:</label>
                    <input type="text" id="iquantite" class="form-control" value="<%= sortie.getQuantite() %>" disabled />
                  </div>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-transparent" data-bs-dismiss="modal">Fermer</button>
                  <button type="submit" class="btn btn-danger">Annuler</button>
                </div>
              </form>

            </div>
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
