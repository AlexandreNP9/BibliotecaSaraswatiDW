/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import DAOs.DAOUsuario;
import DAOs.DAOEmprestimo;
import DAOs.DAOObra;
import Entidades.Usuario;
import Entidades.Emprestimo;
import Entidades.Obra;
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
@WebServlet(name = "EmprestimoServlet", urlPatterns = {"/emprestimo"})
public class EmprestimoServlet extends HttpServlet {

    Locale ptBr = new Locale("pt", "BR");
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
        int usuarioId = 0;
        int obraId = 0;
        Date dataEmprestimo = null;
        try {
            dataEmprestimo = sdf.parse("01/01/0001");
        } catch (ParseException ex) {
            Logger.getLogger(EmprestimoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String submitCadastro = "";

        try (PrintWriter out = response.getWriter()) {
            submitCadastro = request.getParameter("ok");

            String resultado = "";
            if (submitCadastro == null) {
                //se nao veio do submit é lista
                //só precisa disso se a lista usa servlet, o primeiro jeito que vimos,
                //se sua lista usa JSTL ou scriplet pode ir direto p/ cadastro, sem esse if
                usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
                if (usuarioId == 0) {
                    resultado = listaEmprestimosCadastrados();
                } else {
                    resultado = listaEmprestimosCadastrados();
                }
            } else {
                //parametros do form
                //aqui pq se passar do if não serão nulos

                //tudo que vem do formulario é string, por isso aqui alguns precisam de conversão
                usuarioId = Integer.parseInt(request.getParameter("usuario"));
                obraId = Integer.parseInt(request.getParameter("obra"));
                try {
                    dataEmprestimo = sdf.parse(request.getParameter("data"));
                } catch (ParseException ex) {
                    Logger.getLogger(EmprestimoServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                DAOEmprestimo daoEmprestimo = new DAOEmprestimo();
                DAOUsuario daoUsuario = new DAOUsuario();
                DAOObra daoObra = new DAOObra();
                Emprestimo emprestimo = new Emprestimo();

                //busca a usuario do id selecionado no select do form
                //busca com o listById para criar um objeto de entidade completo, 
                //que é o parâmetro que o set de usuario pede
                Usuario usuario = daoUsuario.listById(usuarioId).get(0);
                Obra obra = daoObra.listById(obraId).get(0);

                //seta informacoes do emprestimo na entidade
                //essa tabela nao tem id automatico no banco, então precisa setar
                //para nao pedir p/ emprestimo no formulario e correr o risco de repetição
                //use a função do dao p/ calcular o id
                emprestimo.setIdEmprestimo(daoEmprestimo.autoIdEmprestimo());
                emprestimo.setUsuarioIdUsuario(usuario);
                emprestimo.setObraIdObra(obra);
                emprestimo.setData(dataEmprestimo);

                //seta a usuario do emprestimo, que vai gravar apenas o id como fk no emprestimo  no banco
                //porém, aqui é orientado a objeto, então o usuario é um objeto da entidade usuario
                //insere o emprestimo no banco
                daoEmprestimo.inserir(emprestimo);
                //faz a busca p/ direcionar p/ uma lista atualizada
                //só se sua lista usa servlet, se for com JSTL ou scriplet é só redirecionar
                resultado = listaEmprestimosCadastrados();
            }
            request.getSession().setAttribute("resultado", resultado);
            response.sendRedirect(request.getContextPath() + "/paginas/emprestimo.jsp");
        }
    }

    protected String listaEmprestimoNome(String nomeEmprestimo) {
        DAOEmprestimo emprestimo = new DAOEmprestimo();
        String tabela = "";
        List<Emprestimo> lista = emprestimo.listByNome(nomeEmprestimo);
        for (Emprestimo l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getIdEmprestimo() + "</td>"
                    + "<td>" + l.getUsuarioIdUsuario() + "</td>"
                    + "<td>" + l.getObraIdObra() + "</td>"
                    + "<td>" + sdf.format(l.getData()) + "</td>"
                    + "</tr>";
        }

        return tabela;
    }

    protected String listaEmprestimosCadastrados() {
        DAOEmprestimo emprestimo = new DAOEmprestimo();
        String tabela = "";
        List<Emprestimo> lista = emprestimo.listInOrderId();
        for (Emprestimo l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getUsuarioIdUsuario() + "</td>"
                    + "<td>" + l.getObraIdObra() + "</td>"
                    + "<td>" + sdf.format(l.getData()) + "</td>"
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
