/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import DAOs.DAOTipoUsuario;
import Entidades.TipoUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author a1602896
 */
@WebServlet(name = "TipoUsuarioServlet", urlPatterns = {"/tipoUsuario"})
public class TipoUsuarioServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            if (!request.getParameter("id").equals("null")) {
                //editar
                //se vem o parametro de id é porque escolheu alguem p/ editar
                int id = Integer.parseInt(request.getParameter("id"));
                String nome = request.getParameter("nome");

                DAOTipoUsuario daoTipoUsuario = new DAOTipoUsuario();

                TipoUsuario tipoUsuario = daoTipoUsuario.listById(id).get(0);
                tipoUsuario.setNomeTipoUsuario(nome);//novo nome
                daoTipoUsuario.atualizar(tipoUsuario);
            } else {
                //essa tabela está com o id automatico no banco, então não precisa setar aqui
                String nome = request.getParameter("nome");

                TipoUsuario tipoUsuario = new TipoUsuario();
                DAOTipoUsuario daoTipoUsuario = new DAOTipoUsuario();

                tipoUsuario.setNomeTipoUsuario(nome);
                daoTipoUsuario.inserir(tipoUsuario);
            }
            response.sendRedirect(request.getContextPath() + "/paginas/tipoUsuarioListaScriptlet.jsp");
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
