/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import DAOs.DAOAutor;
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
@WebServlet(name = "AutorServlet", urlPatterns = {"/autor"})
public class AutorServlet extends HttpServlet {
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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
                String sobrenome = request.getParameter("sobrenome");
                String nome = request.getParameter("nome");
                Date nascimento = null;
                try {
                    nascimento = sdf.parse(request.getParameter("nascimento"));
                } catch (ParseException ex) {
                    Logger.getLogger(AutorServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                Date falecimento = null;
                try {
                    falecimento = sdf.parse(request.getParameter("falecimento"));
                } catch (ParseException ex) {
                    Logger.getLogger(AutorServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                String imagem = request.getParameter("imagem");

                DAOAutor daoAutor = new DAOAutor();

                Autor autor = daoAutor.listById(id).get(0);
                autor.setSobrenomeAutor(sobrenome);//novo nome
                autor.setNomeAutor(nome);//novo nome
                autor.setNascimentoAutor(nascimento);//novo nome
                autor.setFalecimentoAutor(falecimento);//novo nome
                autor.setImagemAutor(imagem);//novo nome
                daoAutor.atualizar(autor);
            } else {
                //essa tabela está com o id automatico no banco, então não precisa setar aqui
                String nome = request.getParameter("nome");

                Autor autor = new Autor();
                DAOAutor daoAutor = new DAOAutor();

                autor.setNomeAutor(nome);
                daoAutor.inserir(autor);
            }
            response.sendRedirect(request.getContextPath());
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