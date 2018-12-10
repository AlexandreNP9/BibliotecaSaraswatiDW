/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import DAOs.DAOTipoObra;
import DAOs.DAOObra;
import DAOs.DAOStatus;
import Entidades.TipoObra;
import Entidades.Obra;
import Entidades.Status;
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
@WebServlet(name = "ObraServlet", urlPatterns = {"/obra"})
public class ObraServlet extends HttpServlet {

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        
        try (PrintWriter out = response.getWriter()) {
            if (!request.getParameter("id").equals("null")) {
                //editar
                int id = Integer.parseInt(request.getParameter("id"));
                String nome = request.getParameter("nome");
                Date ano = null;
                try {
                    ano = sdf.parse(request.getParameter("ano"));
                } catch (ParseException ex) {
                    Logger.getLogger(ObraServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                String observacoes = request.getParameter("observacoes");
                int tipoObraId = Integer.parseInt(request.getParameter("tipoObra"));
                int statusId = Integer.parseInt(request.getParameter("status"));
                
                DAOObra daoObra = new DAOObra();

                Obra obra = daoObra.listById(id).get(0);
                
                obra.setNomeObra(nome);
                obra.setAnoObra(ano);
                obra.setQuantidadeObra(quantidade);
                obra.setObservacoesObra(observacoes);
                
                DAOTipoObra daoTipoObra = new DAOTipoObra();
                TipoObra tipoObra = daoTipoObra.listById(tipoObraId).get(0);
                
                obra.setTipoobraidtipoObra(tipoObra);
                
                DAOStatus daoStatus = new DAOStatus();
                Status status = daoStatus.listById(statusId).get(0);
                
                obra.setStatusIdStatus(status);
                
                daoObra.atualizar(obra);
            } else {
                String nome = request.getParameter("nome");
                Date ano = null;
                try {
                    ano = sdf.parse(request.getParameter("ano"));
                } catch (ParseException ex) {
                    Logger.getLogger(ObraServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                String observacoes = request.getParameter("observacoes");
                int tipoObraId = Integer.parseInt(request.getParameter("tipoObra"));
                int statusId = Integer.parseInt(request.getParameter("status"));
                
                Obra obra = new Obra();
                DAOObra daoObra = new DAOObra();

                obra.setNomeObra(nome);
                obra.setAnoObra(ano);
                obra.setQuantidadeObra(quantidade);
                obra.setObservacoesObra(observacoes);
                
                DAOTipoObra daoTipoObra = new DAOTipoObra();
                TipoObra tipoObra = daoTipoObra.listById(tipoObraId).get(0);
                
                obra.setTipoobraidtipoObra(tipoObra);
                
                DAOStatus daoStatus = new DAOStatus();
                Status status = daoStatus.listById(statusId).get(0);
                
                obra.setStatusIdStatus(status);
                
                daoObra.inserir(obra);
            }
            response.sendRedirect(request.getContextPath() + "/paginas/obraListaScriptlet.jsp");
            
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
