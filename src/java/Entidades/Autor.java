/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author alexa
 */
@Entity
@Table(name = "autor")
@NamedQueries({
    @NamedQuery(name = "Autor.findAll", query = "SELECT a FROM Autor a")})
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_autor")
    private Integer idAutor;
    @Basic(optional = false)
    @Column(name = "sobrenome_autor")
    private String sobrenomeAutor;
    @Basic(optional = false)
    @Column(name = "nome_autor")
    private String nomeAutor;
    @Basic(optional = false)
    @Column(name = "nascimento_autor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nascimentoAutor;
    @Column(name = "falecimento_autor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date falecimentoAutor;
    @Column(name = "imagem_autor")
    private String imagemAutor;

    public Autor() {
    }

    public Autor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public Autor(Integer idAutor, String sobrenomeAutor, String nomeAutor, Date nascimentoAutor) {
        this.idAutor = idAutor;
        this.sobrenomeAutor = sobrenomeAutor;
        this.nomeAutor = nomeAutor;
        this.nascimentoAutor = nascimentoAutor;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getSobrenomeAutor() {
        return sobrenomeAutor;
    }

    public void setSobrenomeAutor(String sobrenomeAutor) {
        this.sobrenomeAutor = sobrenomeAutor;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Date getNascimentoAutor() {
        return nascimentoAutor;
    }

    public void setNascimentoAutor(Date nascimentoAutor) {
        this.nascimentoAutor = nascimentoAutor;
    }

    public Date getFalecimentoAutor() {
        return falecimentoAutor;
    }

    public void setFalecimentoAutor(Date falecimentoAutor) {
        this.falecimentoAutor = falecimentoAutor;
    }

    public String getImagemAutor() {
        return imagemAutor;
    }

    public void setImagemAutor(String imagemAutor) {
        this.imagemAutor = imagemAutor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAutor != null ? idAutor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autor)) {
            return false;
        }
        Autor other = (Autor) object;
        if ((this.idAutor == null && other.idAutor != null) || (this.idAutor != null && !this.idAutor.equals(other.idAutor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Autor[ idAutor=" + idAutor + " ]";
    }
    
}
