package DAOs;

import Entidades.UsuarioExecutaObra;
import java.util.ArrayList;
import java.util.List;
import Entidades.Usuario;
import Entidades.Obra;

public class DAOUsuarioExecutaObra extends DAOGenerico<UsuarioExecutaObra> {

    public DAOUsuarioExecutaObra() {
        super(UsuarioExecutaObra.class);
    }

    public int autoIdUsuarioExecutaObra() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idUsuarioExecutaObra) FROM UsuarioExecutaObra e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<UsuarioExecutaObra> listByNome(String nome) {
        return em.createQuery("SELECT e FROM UsuarioExecutaObra e WHERE e.nomeUsuarioExecutaObra LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<UsuarioExecutaObra> listById(int id) {
        return em.createQuery("SELECT e FROM UsuarioExecutaObra e WHERE e.idUsuarioExecutaObra = :id").setParameter("id", id).getResultList();
    }

    public List<UsuarioExecutaObra> listInOrderNome() {
        return em.createQuery("SELECT e FROM UsuarioExecutaObra e ORDER BY e.nomeUsuarioExecutaObra").getResultList();
    }

    public List<UsuarioExecutaObra> listInOrderId() {
        return em.createQuery("SELECT e FROM UsuarioExecutaObra e ORDER BY e.idUsuarioExecutaObra").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<UsuarioExecutaObra> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getUsuario()+ "-" + lf.get(i).getObra()+"-"+lf.get(i).getDiaExecucao());
        }
        return ls;
    }
}
