package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapping.Magasins;
import spec.EtatStock;

@WebServlet("/etat")
public class EtatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dateDebut = "2023-01-01";
        String dateFin = "2023-12-31";
        int idMagasin = 0;

        if (req.getParameter("date_debut") != null && req.getParameter("date_fin") != null
                && req.getParameter("id_magasin") != null) {
            dateDebut = req.getParameter("date_debut");
            dateFin = req.getParameter("date_fin");
            idMagasin = Integer.parseInt(req.getParameter("id_magasin"));
        }

        EtatStock etatStock = new EtatStock(dateDebut, dateFin, idMagasin);
        req.setAttribute("etat_stock", etatStock);
        req.setAttribute("id_magasin", idMagasin);

        List<Magasins> magasins = Magasins.getAll();
        req.setAttribute("magasins", magasins);
        req.getRequestDispatcher("etat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

}
