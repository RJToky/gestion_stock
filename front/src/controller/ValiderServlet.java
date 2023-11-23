package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapping.Sorties;
import util.Remote;

@WebServlet("/valider")
public class ValiderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Sorties> sorties = Sorties.getAllOfEtat(10);
        req.setAttribute("sorties", sorties);

        req.getRequestDispatcher("valider.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int action = Integer.parseInt(req.getParameter("action"));
        int idMouvementSortie = Integer.parseInt(req.getParameter("id_mouvement_sortie"));

        try {
            if (action == 1) {
                double quantiteSortie = Double.parseDouble(req.getParameter("quantite_sortie"));
                String dateValidation = req.getParameter("date_validation");
                Remote.stock().validerSortie(idMouvementSortie, dateValidation, quantiteSortie);

            } else if (action == 2) {
                Remote.stock().annulerSortie(idMouvementSortie);
            }
            resp.sendRedirect("valider");
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.setAttribute("redirect", "valider");
            req.getRequestDispatcher("message.jsp").forward(req, resp);
        }
    }
}
