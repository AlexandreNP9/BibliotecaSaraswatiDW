/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import DAOs.DAOTipoUsuario;
import DAOs.DAOUsuario;
import Entidades.TipoUsuario;
import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jaque
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuario"})
public class UsuarioServlet extends HttpServlet {

    Locale ptBr = new Locale("pt", "BR");
    NumberFormat formatoDinheiro = NumberFormat.getCurrencyInstance(ptBr);

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
        String nomeUsuario = "";
        String submitCadastro = "";
        int tipoUsuarioId = 0;

        try (PrintWriter out = response.getWriter()) {
            submitCadastro = request.getParameter("ok");

            String resultado = "";
            if (submitCadastro == null) {
                //se nao veio do submit é lista
                //só precisa disso se a lista usa servlet, o primeiro jeito que vimos,
                //se sua lista usa JSTL ou scriplet pode ir direto p/ cadastro, sem esse if
                nomeUsuario = request.getParameter("nomeUsuario");
                if (nomeUsuario == null || nomeUsuario.equals("")) {
                    resultado = listaUsuariosCadastrados();
                } else {
                    resultado = listaUsuarioNome(nomeUsuario);
                }
            } else {
                //parametros do form
                //aqui pq se passar do if não serão nulos

                //tudo que vem do formulario é string, por isso aqui alguns precisam de conversão
                tipoUsuarioId = Integer.parseInt(request.getParameter("tipoUsuario"));
                nomeUsuario = request.getParameter("nome");
                String loginUsuario = request.getParameter("login");
                String senhaUsuario = request.getParameter("senha");

                DAOUsuario daoUsuario = new DAOUsuario();
                DAOTipoUsuario daoTipoUsuario = new DAOTipoUsuario();
                Usuario usuario = new Usuario();

                //busca a tipoUsuario do id selecionado no select do form
                //busca com o listById para criar um objeto de entidade completo, 
                //que é o parâmetro que o set de tipoUsuario pede
                TipoUsuario tipoUsuario = daoTipoUsuario.listById(tipoUsuarioId).get(0);

                //seta informacoes do usuario na entidade
                //essa tabela nao tem id automatico no banco, então precisa setar
                //para nao pedir p/ usuario no formulario e correr o risco de repetição
                //use a função do dao p/ calcular o id
                usuario.setIdUsuario(daoUsuario.autoIdUsuario());
                usuario.setNomeUsuario(nomeUsuario);
                usuario.setLoginUsuario(loginUsuario);
                usuario.setSenhaUsuario(senhaUsuario);
                //seta a tipoUsuario do usuario, que vai gravar apenas o id como fk no usuario  no banco
                //porém, aqui é orientado a objeto, então o tipoUsuario é um objeto da entidade tipoUsuario
                usuario.setTipoUsuarioIdTipoUsuario(tipoUsuario);

                //insere o usuario no banco
                daoUsuario.inserir(usuario);
                //faz a busca p/ direcionar p/ uma lista atualizada
                //só se sua lista usa servlet, se for com JSTL ou scriplet é só redirecionar
                resultado = listaUsuariosCadastrados();
            }
            request.getSession().setAttribute("resultado", resultado);
            response.sendRedirect(request.getContextPath() + "/paginas/usuario.jsp");
        }
    }

    protected String listaUsuarioNome(String nomeUsuario) {
        DAOUsuario usuario = new DAOUsuario();
        String tabela = "";
        List<Usuario> lista = usuario.listByNome(nomeUsuario);
        for (Usuario l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getNomeUsuario() + "</td>"
                    + "<td>" + l.getLoginUsuario() + "</td>"
                    + "<td>" + l.getSenhaUsuario() + "</td>"
                    + "<td>" + l.getTipoUsuarioIdTipoUsuario().getNomeTipoUsuario() + "</td>"
                    + "</tr>";
        }

        return tabela;
    }

    protected String listaUsuariosCadastrados() {
        DAOUsuario usuario = new DAOUsuario();
        String tabela = "";
        List<Usuario> lista = usuario.listInOrderNome();
        for (Usuario l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getNomeUsuario() + "</td>"
                    + "<td>" + l.getLoginUsuario() + "</td>"
                    + "<td>" + l.getSenhaUsuario() + "</td>"
                    + "<td>" + l.getTipoUsuarioIdTipoUsuario().getNomeTipoUsuario() + "</td>"
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
