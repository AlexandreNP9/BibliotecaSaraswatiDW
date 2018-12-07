/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import DAOs.DAOUsuario;
import DAOs.DAOTipoUsuario;
import Entidades.TipoUsuario;
import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author a1602896
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuario"})
public class UsuarioServlet extends HttpServlet {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

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
                DAOTipoUsuario daoTipoUsuario = new DAOTipoUsuario();

                int id = Integer.parseInt(request.getParameter("id"));
                String login = request.getParameter("login");
                String nome = request.getParameter("nome");
                String senha = request.getParameter("senha");
                int tipoUsuario = Integer.parseInt(request.getParameter("tipoUsuario"));
                List<TipoUsuario> tipoUsuarioId = daoTipoUsuario.listById(tipoUsuario);

                DAOUsuario daoUsuario = new DAOUsuario();

                Usuario usuario = daoUsuario.listById(id).get(0);
                TipoUsuario tipoUsuarioIdAux = daoTipoUsuario.listById(id).get(0);
                usuario.setLoginUsuario(login);//novo nome
                usuario.setNomeUsuario(nome);//novo nome
                usuario.setSenhaUsuario(senha);
                usuario.setTipoUsuarioIdTipoUsuario(tipoUsuarioIdAux);
                daoUsuario.atualizar(usuario);
            } else {
                //essa tabela está com o id automatico no banco, então não precisa setar aqui
                DAOTipoUsuario daoTipoUsuario = new DAOTipoUsuario();

                String login = request.getParameter("login");
                String nome = request.getParameter("nome");
                String senha = request.getParameter("senha");
                int tipoUsuario = Integer.parseInt(request.getParameter("tipoUsuario"));
                List<TipoUsuario> tipoUsuarioId = daoTipoUsuario.listById(tipoUsuario);

                Usuario usuario = new Usuario();
                DAOUsuario daoUsuario = new DAOUsuario();
                
                TipoUsuario tipoUsuario1 = new TipoUsuario();
                DAOTipoUsuario daoTipoUsuario1 = new DAOTipoUsuario();

                usuario.setLoginUsuario(login);//novo nome
                usuario.setNomeUsuario(nome);//novo nome
                usuario.setSenhaUsuario(senha);;
                usuario.setTipoUsuarioIdTipoUsuario(daoTipoUsuario.listInOrderId());
                daoUsuario.inserir(usuario);
            }
            response.sendRedirect(request.getContextPath() + "/paginas/usuarioListaScriptlet.jsp");

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
