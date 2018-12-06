package DAOs;

import Entidades.TipoUsuarioHasModuloSistema;
import java.util.ArrayList;
import java.util.List;

public class DAOTipoUsuarioHasModuloSistema extends DAOGenerico<TipoUsuarioHasModuloSistema> {

    public DAOTipoUsuarioHasModuloSistema() {
        super(TipoUsuarioHasModuloSistema.class);
    }

    public int autoIdTipoUsuarioHasModuloSistema() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idTipoUsuarioHasModuloSistema) FROM TipoUsuarioHasModuloSistema e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<TipoUsuarioHasModuloSistema> listByNome(String nome) {
        return em.createQuery("SELECT e FROM TipoUsuarioHasModuloSistema e WHERE e.nomeTipoUsuarioHasModuloSistema LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<TipoUsuarioHasModuloSistema> listById(int id) {
        return em.createQuery("SELECT e FROM TipoUsuarioHasModuloSistema e WHERE e.idTipoUsuarioHasModuloSistema = :id").setParameter("id", id).getResultList();
    }

    public List<TipoUsuarioHasModuloSistema> listInOrderNome() {
        return em.createQuery("SELECT e FROM TipoUsuarioHasModuloSistema e ORDER BY e.idTipoUsuarioHasModuloSistema").getResultList();
    }

    public List<TipoUsuarioHasModuloSistema> listInOrderId() {
        return em.createQuery("SELECT e FROM TipoUsuarioHasModuloSistema e ORDER BY e.idTipoUsuarioHasModuloSistema").getResultList();
    }

//    public List<String> listInOrderNomeStrings(String qualOrdem) {
//        List<TipoUsuarioHasModuloSistema> lf;
//        if (qualOrdem.equals("id")) {
//            lf = listInOrderId();
//        } else {
//            lf = listInOrderNome();
//        }
//
//        List<String> ls = new ArrayList<>();
//        for (int i = 0; i < lf.size(); i++) {
//            ls.add(lf.get(i).getIdTipoUsuarioHasModuloSistema() + "-" + lf.get(i).getNomeTipoUsuarioHasModuloSistema());
//        }
//        return ls;
//    }
}
