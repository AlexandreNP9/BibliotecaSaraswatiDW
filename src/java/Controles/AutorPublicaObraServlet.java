/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import DAOs.DAOObra;
import DAOs.DAOAutorPublicaObra;
import DAOs.DAOAutor;
import Entidades.Obra;
import Entidades.AutorPublicaObra;
import Entidades.Autor;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author a1602896
 */
@WebServlet(name = "AutorPublicaObraServlet", urlPatterns = {"/autorPublicaObra"})
public class AutorPublicaObraServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        try (PrintWriter out = response.getWriter()) {
            if (!request.getParameter("id").equals("null")) {
                //editar
                int id = Integer.parseInt(request.getParameter("id"));
                String local = request.getParameter("local");
                Date data = null;
                try {
                    data = sdf.parse(request.getParameter("data"));
                } catch (ParseException ex) {
                    Logger.getLogger(AutorPublicaObraServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                int obraId = Integer.parseInt(request.getParameter("obra"));
                int autorId = Integer.parseInt(request.getParameter("autor"));
                
                DAOAutorPublicaObra daoAutorPublicaObra = new DAOAutorPublicaObra();

                AutorPublicaObra autorPublicaAutorPublicaObra = daoAutorPublicaObra.listById(id).get(0);
                
                autorPublicaAutorPublicaObra.setLocalAutorPublicaObra(local);
                autorPublicaAutorPublicaObra.setDataAutorPublicaObra(data);
                
                DAOObra daoObra = new DAOObra();
                Obra obra = daoObra.listById(obraId).get(id);
                
                autorPublicaAutorPublicaObra.setObraIdObra(obra);
                
                DAOAutor daoAutor = new DAOAutor();
                Autor autor = daoAutor.listById(autorId).get(0);
                
                autorPublicaAutorPublicaObra.setAutorIdAutor(autor);
                
                daoAutorPublicaObra.atualizar(autorPublicaAutorPublicaObra);
            } else {
                String local = request.getParameter("local");
                Date data = null;
                try {
                    data = sdf.parse(request.getParameter("data"));
                } catch (ParseException ex) {
                    Logger.getLogger(AutorPublicaObraServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                int obraId = Integer.parseInt(request.getParameter("obra"));
                int autorId = Integer.parseInt(request.getParameter("autor"));
                
                AutorPublicaObra autorPublicaAutorPublicaObra = new AutorPublicaObra();
                DAOAutorPublicaObra daoAutorPublicaObra = new DAOAutorPublicaObra();

                autorPublicaAutorPublicaObra.setLocalAutorPublicaObra(local);
                autorPublicaAutorPublicaObra.setDataAutorPublicaObra(data);
                
                DAOObra daoObra = new DAOObra();
                Obra obra = daoObra.listById(obraId).get(0);
                
                autorPublicaAutorPublicaObra.setObraIdObra(obra);
                
                DAOAutor daoAutor = new DAOAutor();
                Autor autor = daoAutor.listById(autorId).get(0);
                
                autorPublicaAutorPublicaObra.setAutorIdAutor(autor);
                
                daoAutorPublicaObra.inserir(autorPublicaAutorPublicaObra);
            }
            response.sendRedirect(request.getContextPath() + "/paginas/autorPublicaObraListaScriptlet.jsp");
            
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
