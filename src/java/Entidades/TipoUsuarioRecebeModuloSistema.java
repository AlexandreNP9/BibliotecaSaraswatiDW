/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author alexa
 */
@Entity
@Table(name = "tipo_usuario_recebe_modulo_sistema")
@NamedQueries({
    @NamedQuery(name = "TipoUsuarioRecebeModuloSistema.findAll", query = "SELECT t FROM TipoUsuarioRecebeModuloSistema t")})
public class TipoUsuarioRecebeModuloSistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_usuario_recebe_modulo_sistema")
    private Integer idTipoUsuarioRecebeModuloSistema;
    @JoinColumn(name = "modulo_sistema_id_modulo_sistema", referencedColumnName = "id_modulo_sistema")
    @ManyToOne(optional = false)
    private ModuloSistema moduloSistemaIdModuloSistema;
    @JoinColumn(name = "tipo_usuario_id_tipo_usuario", referencedColumnName = "id_tipo_usuario")
    @ManyToOne(optional = false)
    private TipoUsuario tipoUsuarioIdTipoUsuario;

    public TipoUsuarioRecebeModuloSistema() {
    }

    public TipoUsuarioRecebeModuloSistema(Integer idTipoUsuarioRecebeModuloSistema) {
        this.idTipoUsuarioRecebeModuloSistema = idTipoUsuarioRecebeModuloSistema;
    }

    public Integer getIdTipoUsuarioRecebeModuloSistema() {
        return idTipoUsuarioRecebeModuloSistema;
    }

    public void setIdTipoUsuarioRecebeModuloSistema(Integer idTipoUsuarioRecebeModuloSistema) {
        this.idTipoUsuarioRecebeModuloSistema = idTipoUsuarioRecebeModuloSistema;
    }

    public ModuloSistema getModuloSistemaIdModuloSistema() {
        return moduloSistemaIdModuloSistema;
    }

    public void setModuloSistemaIdModuloSistema(ModuloSistema moduloSistemaIdModuloSistema) {
        this.moduloSistemaIdModuloSistema = moduloSistemaIdModuloSistema;
    }

    public TipoUsuario getTipoUsuarioIdTipoUsuario() {
        return tipoUsuarioIdTipoUsuario;
    }

    public void setTipoUsuarioIdTipoUsuario(TipoUsuario tipoUsuarioIdTipoUsuario) {
        this.tipoUsuarioIdTipoUsuario = tipoUsuarioIdTipoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoUsuarioRecebeModuloSistema != null ? idTipoUsuarioRecebeModuloSistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuarioRecebeModuloSistema)) {
            return false;
        }
        TipoUsuarioRecebeModuloSistema other = (TipoUsuarioRecebeModuloSistema) object;
        if ((this.idTipoUsuarioRecebeModuloSistema == null && other.idTipoUsuarioRecebeModuloSistema != null) || (this.idTipoUsuarioRecebeModuloSistema != null && !this.idTipoUsuarioRecebeModuloSistema.equals(other.idTipoUsuarioRecebeModuloSistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoUsuarioRecebeModuloSistema[ idTipoUsuarioRecebeModuloSistema=" + idTipoUsuarioRecebeModuloSistema + " ]";
    }
    
}
