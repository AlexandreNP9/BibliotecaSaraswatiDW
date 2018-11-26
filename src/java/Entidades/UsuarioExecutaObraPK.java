/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author alexa
 */
@Embeddable
public class UsuarioExecutaObraPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "usuario_id_usuario")
    private int usuarioIdUsuario;
    @Basic(optional = false)
    @Column(name = "obra_id_obra")
    private int obraIdObra;

    public UsuarioExecutaObraPK() {
    }

    public UsuarioExecutaObraPK(int usuarioIdUsuario, int obraIdObra) {
        this.usuarioIdUsuario = usuarioIdUsuario;
        this.obraIdObra = obraIdObra;
    }

    public int getUsuarioIdUsuario() {
        return usuarioIdUsuario;
    }

    public void setUsuarioIdUsuario(int usuarioIdUsuario) {
        this.usuarioIdUsuario = usuarioIdUsuario;
    }

    public int getObraIdObra() {
        return obraIdObra;
    }

    public void setObraIdObra(int obraIdObra) {
        this.obraIdObra = obraIdObra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuarioIdUsuario;
        hash += (int) obraIdObra;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioExecutaObraPK)) {
            return false;
        }
        UsuarioExecutaObraPK other = (UsuarioExecutaObraPK) object;
        if (this.usuarioIdUsuario != other.usuarioIdUsuario) {
            return false;
        }
        if (this.obraIdObra != other.obraIdObra) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.UsuarioExecutaObraPK[ usuarioIdUsuario=" + usuarioIdUsuario + ", obraIdObra=" + obraIdObra + " ]";
    }
    
}
