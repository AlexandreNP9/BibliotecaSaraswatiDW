/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import DAOs.DAOAutor;
import DAOs.DAOAutorPublicaObra;
import DAOs.DAOObra;
import Entidades.Autor;
import Entidades.AutorPublicaObra;
import Entidades.Obra;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AutorPublicaObraServlet", urlPatterns = {"/autorPublicaObra"})
public class AutorPublicaObraServlet extends HttpServlet {

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
        String local = "";
        String submitCadastro = "";
        Date data = null;
        try {
            data = sdf.parse("01/01/0001");
        } catch (ParseException ex) {
            Logger.getLogger(AutorPublicaObraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        int obraId = 0;
        int autorId = 0;

        try (PrintWriter out = response.getWriter()) {
            submitCadastro = request.getParameter("ok");

            String resultado = "";
            if (submitCadastro == null) {
                //se nao veio do submit é lista
                //só precisa disso se a lista usa servlet, o primeiro jeito que vimos,
                //se sua lista usa JSTL ou scriplet pode ir direto p/ cadastro, sem esse if
                local = request.getParameter("local");
                if (local == null || local.equals("")) {
                    resultado = listaAutoresPublicamObrasCadastrados();
                } else {
                    resultado = listaAutorPublicaObraNome(local);
                }
            } else {
                //parametros do form
                //aqui pq se passar do if não serão nulos

                //tudo que vem do formulario é string, por isso aqui alguns precisam de conversão
                local = request.getParameter("local");
                try {
                    data = sdf.parse(request.getParameter("data"));
                } catch (ParseException ex) {
                    Logger.getLogger(AutorPublicaObraServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                obraId = Integer.parseInt(request.getParameter("obra"));
                autorId = Integer.parseInt(request.getParameter("autor"));

                DAOAutorPublicaObra daoAutorPublicaObra = new DAOAutorPublicaObra();

                DAOObra daoObra = new DAOObra();
                DAOAutor daoAutor = new DAOAutor();

                AutorPublicaObra autorPublicaObra = new AutorPublicaObra();

//busca a tipoObra do id selecionado no select do form
                //busca com o listById para criar um objeto de entidade completo, 
                //que é o parâmetro que o set de tipoObra pede
                Obra obra = daoObra.listById(obraId).get(0);
                Autor autor = daoAutor.listById(obraId).get(0);

                //seta informacoes do obra na entidade
                //essa tabela nao tem id automatico no banco, então precisa setar
                //para nao pedir p/ obra no formulario e correr o risco de repetição
                //use a função do dao p/ calcular o id
                autorPublicaObra.setIdAutorPublicaObra(daoAutorPublicaObra.autoIdAutorPublicaObra());
                autorPublicaObra.setLocalAutorPublicaObra(local);
                autorPublicaObra.setDataAutorPublicaObra(data);

                //seta a tipoObra do obra, que vai gravar apenas o id como fk no obra  no banco
                //porém, aqui é orientado a objeto, então o tipoObra é um objeto da entidade tipoObra
                autorPublicaObra.setObraIdObra(obra);
                autorPublicaObra.setAutorIdAutor(autor);

                //insere o obra no banco
                daoAutorPublicaObra.inserir(autorPublicaObra);
                //faz a busca p/ direcionar p/ uma lista atualizada
                //só se sua lista usa servlet, se for com JSTL ou scriplet é só redirecionar
                resultado = listaAutoresPublicamObrasCadastrados();
            }
            request.getSession().setAttribute("resultado", resultado);
            response.sendRedirect(request.getContextPath() + "/paginas/autorPubliacObraListaScriptlet.jsp");
        }
    }

    protected String listaAutorPublicaObraNome(String local) {
        DAOAutorPublicaObra autorPublicaObra = new DAOAutorPublicaObra();
        String tabela = "";
        List<AutorPublicaObra> lista = autorPublicaObra.listByNome(local);
        for (AutorPublicaObra l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getLocalAutorPublicaObra() + "</td>"
                    + "<td>" + sdf.format(l.getDataAutorPublicaObra()) + "</td>"
                    + "<td>" + l.getObraIdObra().getNomeObra() + "</td>"
                    + "<td>" + l.getAutorIdAutor().getNomeAutor() + "</td>"
                    + "</tr>";
        }

        return tabela;
    }

    protected String listaAutoresPublicamObrasCadastrados() {
        DAOAutorPublicaObra autorPublicaObra = new DAOAutorPublicaObra();
        String tabela = "";
        List<AutorPublicaObra> lista = autorPublicaObra.listInOrderNome();
        for (AutorPublicaObra l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getLocalAutorPublicaObra() + "</td>"
                    + "<td>" + sdf.format(l.getDataAutorPublicaObra()) + "</td>"
                    + "<td>" + l.getObraIdObra().getNomeObra() + "</td>"
                    + "<td>" + l.getAutorIdAutor().getNomeAutor() + "</td>"
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
