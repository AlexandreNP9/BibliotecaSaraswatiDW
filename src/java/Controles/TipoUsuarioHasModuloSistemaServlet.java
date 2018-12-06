/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import DAOs.DAOTipoUsuario;
import DAOs.DAOTipoUsuarioHasModuloSistema;
import DAOs.DAOModuloSistema;
import Entidades.TipoUsuario;
import Entidades.TipoUsuarioHasModuloSistema;
import Entidades.ModuloSistema;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jaque
 */
@WebServlet(name = "TipoUsuarioHasModuloSistemaServlet", urlPatterns = {"/tipoUsuarioHasModuloSistema"})
public class TipoUsuarioHasModuloSistemaServlet extends HttpServlet {

    Locale ptBr = new Locale("pt", "BR");
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
        int tipoUsuarioId = 0;
        int moduloSistemaId = 0;
        String submitCadastro = "";

        try (PrintWriter out = response.getWriter()) {
            submitCadastro = request.getParameter("ok");

            String resultado = "";
            if (submitCadastro == null) {
                //se nao veio do submit é lista
                //só precisa disso se a lista usa servlet, o primeiro jeito que vimos,
                //se sua lista usa JSTL ou scriplet pode ir direto p/ cadastro, sem esse if
                tipoUsuarioId = Integer.parseInt(request.getParameter("tipoUsuarioId"));
                if (tipoUsuarioId == 0) {
                    resultado = listaTipoUsuarioHasModuloSistemasCadastrados();
                } else {
                    resultado = listaTipoUsuarioHasModuloSistemasCadastrados();
                }
            } else {
                //parametros do form
                //aqui pq se passar do if não serão nulos

                //tudo que vem do formulario é string, por isso aqui alguns precisam de conversão
                tipoUsuarioId = Integer.parseInt(request.getParameter("tipoUsuario"));
                moduloSistemaId = Integer.parseInt(request.getParameter("moduloSistema"));
                
                DAOTipoUsuarioHasModuloSistema daoTipoUsuarioHasModuloSistema = new DAOTipoUsuarioHasModuloSistema();
                DAOTipoUsuario daoTipoUsuario = new DAOTipoUsuario();
                DAOModuloSistema daoModuloSistema = new DAOModuloSistema();
                TipoUsuarioHasModuloSistema tipoUsuarioHasModuloSistema = new TipoUsuarioHasModuloSistema();

                //busca a tipoUsuario do id selecionado no select do form
                //busca com o listById para criar um objeto de entidade completo, 
                //que é o parâmetro que o set de tipoUsuario pede
                TipoUsuario tipoUsuario = daoTipoUsuario.listById(tipoUsuarioId).get(0);
                ModuloSistema moduloSistema = daoModuloSistema.listById(moduloSistemaId).get(0);

                //seta informacoes do tipoUsuarioHasModuloSistema na entidade
                //essa tabela nao tem id automatico no banco, então precisa setar
                //para nao pedir p/ tipoUsuarioHasModuloSistema no formulario e correr o risco de repetição
                //use a função do dao p/ calcular o id
                tipoUsuarioHasModuloSistema.setIdTipoUsuarioHasModuloSistema(daoTipoUsuarioHasModuloSistema.autoIdTipoUsuarioHasModuloSistema());
                tipoUsuarioHasModuloSistema.setTipoUsuarioIdTipoUsuario(tipoUsuario);
                tipoUsuarioHasModuloSistema.setModuloSistemaIdModuloSistema(moduloSistema);
                
                //seta a tipoUsuario do tipoUsuarioHasModuloSistema, que vai gravar apenas o id como fk no tipoUsuarioHasModuloSistema  no banco
                //porém, aqui é orientado a objeto, então o tipoUsuario é um objeto da entidade tipoUsuario
                //insere o tipoUsuarioHasModuloSistema no banco
                daoTipoUsuarioHasModuloSistema.inserir(tipoUsuarioHasModuloSistema);
                //faz a busca p/ direcionar p/ uma lista atualizada
                //só se sua lista usa servlet, se for com JSTL ou scriplet é só redirecionar
                resultado = listaTipoUsuarioHasModuloSistemasCadastrados();
            }
            request.getSession().setAttribute("resultado", resultado);
            response.sendRedirect(request.getContextPath() + "/paginas/tipoUsuarioHasModuloSistemaListaScriptlet.jsp");
        }
    }

    protected String listaTipoUsuarioHasModuloSistemaNome(String nomeTipoUsuarioHasModuloSistema) {
        DAOTipoUsuarioHasModuloSistema tipoUsuarioHasModuloSistema = new DAOTipoUsuarioHasModuloSistema();
        String tabela = "";
        List<TipoUsuarioHasModuloSistema> lista = tipoUsuarioHasModuloSistema.listByNome(nomeTipoUsuarioHasModuloSistema);
        for (TipoUsuarioHasModuloSistema l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getIdTipoUsuarioHasModuloSistema() + "</td>"
                    + "<td>" + l.getTipoUsuarioIdTipoUsuario() + "</td>"
                    + "<td>" + l.getModuloSistemaIdModuloSistema() + "</td>"
                    + "</tr>";
        }

        return tabela;
    }

    protected String listaTipoUsuarioHasModuloSistemasCadastrados() {
        DAOTipoUsuarioHasModuloSistema tipoUsuarioHasModuloSistema = new DAOTipoUsuarioHasModuloSistema();
        String tabela = "";
        List<TipoUsuarioHasModuloSistema> lista = tipoUsuarioHasModuloSistema.listInOrderId();
        for (TipoUsuarioHasModuloSistema l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getTipoUsuarioIdTipoUsuario() + "</td>"
                    + "<td>" + l.getModuloSistemaIdModuloSistema() + "</td>"
                    + "</tr>";
        }

        return tabela;
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
        System.out.println("teste doget");
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
        System.out.println("teste dopost");
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
