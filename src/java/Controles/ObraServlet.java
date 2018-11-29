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
@WebServlet(name = "ObraServlet", urlPatterns = {"/obra"})
public class ObraServlet extends HttpServlet {

    Locale ptBr = new Locale("pt", "BR");
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
        String nomeObra = "";
        Date anoObra = null;
        try {
            anoObra = sdf.parse("0001");
        } catch (ParseException ex) {
            Logger.getLogger(ObraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        int quantidadeObra = 0;
        String observacoesObra = "";
        String submitCadastro = "";
        int tipoObraId = 0;
        int statusId = 0;

        try (PrintWriter out = response.getWriter()) {
            submitCadastro = request.getParameter("ok");

            String resultado = "";
            if (submitCadastro == null) {
                //se nao veio do submit é lista
                //só precisa disso se a lista usa servlet, o primeiro jeito que vimos,
                //se sua lista usa JSTL ou scriplet pode ir direto p/ cadastro, sem esse if
                nomeObra = request.getParameter("nomeObra");
                if (nomeObra == null || nomeObra.equals("")) {
                    resultado = listaObrasCadastrados();
                } else {
                    resultado = listaObraNome(nomeObra);
                }
            } else {
                //parametros do form
                //aqui pq se passar do if não serão nulos

                //tudo que vem do formulario é string, por isso aqui alguns precisam de conversão
                tipoObraId = Integer.parseInt(request.getParameter("tipoObra"));
                statusId = Integer.parseInt(request.getParameter("status"));
                nomeObra = request.getParameter("nome");
                try {
                    anoObra = sdf.parse(request.getParameter("ano"));
                } catch (ParseException ex) {
                    Logger.getLogger(ObraServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                quantidadeObra = Integer.valueOf(request.getParameter("quantidade"));
                observacoesObra = request.getParameter("observacoes");

                DAOObra daoObra = new DAOObra();
                DAOTipoObra daoTipoObra = new DAOTipoObra();
                DAOStatus daoStatus = new DAOStatus();
                Obra obra = new Obra();

                //busca a tipoObra do id selecionado no select do form
                //busca com o listById para criar um objeto de entidade completo, 
                //que é o parâmetro que o set de tipoObra pede
                TipoObra tipoObra = daoTipoObra.listById(tipoObraId).get(0);
                Status status = daoStatus.listById(statusId).get(0);

                //seta informacoes do obra na entidade
                //essa tabela nao tem id automatico no banco, então precisa setar
                //para nao pedir p/ obra no formulario e correr o risco de repetição
                //use a função do dao p/ calcular o id
                obra.setIdObra(daoObra.autoIdObra());
                obra.setNomeObra(nomeObra);
                obra.setAnoObra(anoObra);

                obra.setQuantidadeObra(quantidadeObra);
                obra.setObservacoesObra(observacoesObra);
                //seta a tipoObra do obra, que vai gravar apenas o id como fk no obra  no banco
                //porém, aqui é orientado a objeto, então o tipoObra é um objeto da entidade tipoObra
                obra.setTipoobraidtipoObra(tipoObra);
                obra.setStatusIdStatus(status);

                //insere o obra no banco
                daoObra.inserir(obra);
                //faz a busca p/ direcionar p/ uma lista atualizada
                //só se sua lista usa servlet, se for com JSTL ou scriplet é só redirecionar
                resultado = listaObrasCadastrados();
            }
            request.getSession().setAttribute("resultado", resultado);
            response.sendRedirect(request.getContextPath() + "/paginas/obraListaScriptlet.jsp");
        }
    }

    protected String listaObraNome(String nomeObra) {
        DAOObra obra = new DAOObra();
        String tabela = "";
        List<Obra> lista = obra.listByNome(nomeObra);
        for (Obra l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getNomeObra() + "</td>"
                    + "<td>" + sdf.format(l.getAnoObra()) + "</td>"
                    + "<td>" + l.getQuantidadeObra() + "</td>"
                    + "<td>" + l.getObservacoesObra() + "</td>"
                    + "<td>" + l.getTipoobraidtipoObra().getNometipoObra() + "</td>"
                    + "<td>" + l.getStatusIdStatus().getNomeStatus() + "</td>"
                    + "</tr>";
        }

        return tabela;
    }

    protected String listaObrasCadastrados() {
        DAOObra obra = new DAOObra();
        String tabela = "";
        List<Obra> lista = obra.listInOrderNome();
        for (Obra l : lista) {
            tabela += "<tr>"
                    + "<td>" + l.getNomeObra() + "</td>"
                    + "<td>" + sdf.format(l.getAnoObra()) + "</td>"
                    + "<td>" + l.getQuantidadeObra() + "</td>"
                    + "<td>" + l.getObservacoesObra() + "</td>"
                    + "<td>" + l.getTipoobraidtipoObra().getNometipoObra() + "</td>"
                    + "<td>" + l.getStatusIdStatus().getNomeStatus() + "</td>"
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
