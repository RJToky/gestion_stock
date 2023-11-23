package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapping.Articles;
import mapping.Magasins;
import util.Remote;

@WebServlet("/sortir")
public class SortirServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Magasins> magasins = Magasins.getAll();
        req.setAttribute("magasins", magasins);

        List<Articles> articles = Articles.getAll();
        req.setAttribute("articles", articles);

        req.getRequestDispatcher("sortir.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idMagasin = Integer.parseInt(req.getParameter("id_magasin"));
        int idArticle = Integer.parseInt(req.getParameter("id_article"));

        double quantiteSortie = Double.parseDouble(req.getParameter("quantite_sortie"));
        String dateSortie = req.getParameter("date_sortie");

        try {
            Remote.stock().saisirSortie(idMagasin, idArticle, quantiteSortie,
                    dateSortie);
            resp.sendRedirect("sortir");
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.setAttribute("redirect", "sortir");
            req.getRequestDispatcher("message.jsp").forward(req, resp);
        }
    }
}
