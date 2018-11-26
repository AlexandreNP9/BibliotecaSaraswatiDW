package DAOs;

import Entidades.Status;
import java.util.ArrayList;
import java.util.List;

public class DAOStatus extends DAOGenerico<Status> {

    public DAOStatus() {
        super(Status.class);
    }

    public int autoIdStatus() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idProduto) FROM Produto e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Status> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Status e WHERE e.nomeStatus LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Status> listById(int id) {
        return em.createQuery("SELECT e FROM Status e WHERE e.idStatus = :id").setParameter("id", id).getResultList();
    }

    public List<Status> listInOrderNome() {
        return em.createQuery("SELECT e FROM Status e ORDER BY e.nomeStatus").getResultList();
    }

    public List<Status> listInOrderId() {
        return em.createQuery("SELECT e FROM Status e ORDER BY e.idStatus").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Status> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdStatus() + "-" + lf.get(i).getNomeStatus());
        }
        return ls;
    }
}
