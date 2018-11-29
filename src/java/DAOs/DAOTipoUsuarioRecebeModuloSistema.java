package DAOs;

import Entidades.TipoUsuarioRecebeModuloSistema;
import java.util.ArrayList;
import java.util.List;

public class DAOTipoUsuarioRecebeModuloSistema extends DAOGenerico<TipoUsuarioRecebeModuloSistema> {

    public DAOTipoUsuarioRecebeModuloSistema() {
        super(TipoUsuarioRecebeModuloSistema.class);
    }

    public int autoIdTipoUsuarioRecebeModuloSistema() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idTipoUsuarioRecebeModuloSistema) FROM TipoUsuarioRecebeModuloSistema e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

//    public List<TipoUsuarioRecebeModuloSistema> listByNome(String nome) {
//        return em.createQuery("SELECT e FROM TipoUsuarioRecebeModuloSistema e WHERE e.nomeTipoUsuarioRecebeModuloSistema LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
//    }

    public List<TipoUsuarioRecebeModuloSistema> listById(int id) {
        return em.createQuery("SELECT e FROM TipoUsuarioRecebeModuloSistema e WHERE e.idTipoUsuarioRecebeModuloSistema = :id").setParameter("id", id).getResultList();
    }

//    public List<TipoUsuarioRecebeModuloSistema> listInOrderNome() {
//        return em.createQuery("SELECT e FROM TipoUsuarioRecebeModuloSistema e ORDER BY e.nomeTipoUsuarioRecebeModuloSistema").getResultList();
//    }

    public List<TipoUsuarioRecebeModuloSistema> listInOrderId() {
        return em.createQuery("SELECT e FROM TipoUsuarioRecebeModuloSistema e ORDER BY e.idTipoUsuarioRecebeModuloSistema").getResultList();
    }

//    public List<String> listInOrderNomeStrings(String qualOrdem) {
//        List<TipoUsuarioRecebeModuloSistema> lf;
//        if (qualOrdem.equals("id")) {
//            lf = listInOrderId();
//        } else {
//            lf = listInOrderNome();
//        }
//
//        List<String> ls = new ArrayList<>();
//        for (int i = 0; i < lf.size(); i++) {
//            ls.add(lf.get(i).getIdTipoUsuarioRecebeModuloSistema() + "-" + lf.get(i).getNomeTipoUsuarioRecebeModuloSistema());
//        }
//        return ls;
//    }
}
