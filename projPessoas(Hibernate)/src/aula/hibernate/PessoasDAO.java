package aula.hibernate;

import java.util.List;
import org.hibernate.Session;

public class PessoasDAO {

    Session session;
    Hibernate hibernate = new Hibernate();

    public void salvar(Pessoas pessoa) {

        session = hibernate.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(pessoa);

        session.getTransaction().commit();
        session.close();
    }

    //-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ---UPDATE
    public void atualizar(Pessoas p) {
        session = hibernate.getSessionFactory().openSession();
        session.beginTransaction();

        Pessoas pessoa = session.get(Pessoas.class, p.getId());

        session.update(pessoa);
        session.getTransaction().commit();
        session.close();

        pessoa.imprimirPessoa();
    }

    //-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ---DELETE
    public void remover(Pessoas p) {
        session = hibernate.getSessionFactory().openSession();
        session.beginTransaction();

        Pessoas pessoa = session.get(Pessoas.class, p.getId());

        session.delete(pessoa);
        session.getTransaction().commit();
        session.close();
    }

    //-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ---SELECT
    public void listar() {
        session = hibernate.getSessionFactory().openSession();
        session.beginTransaction();

        List<Pessoas> lista = session.createQuery("from Pessoas").list();

        session.getTransaction().commit();
        session.close();

        for (Pessoas p : (List<Pessoas>) lista) {
            p.imprimirPessoa();
        }
    }

    public Pessoas findById(int id) {
        session = hibernate.getSessionFactory().openSession();
        session.beginTransaction();

        Pessoas pessoa = session.get(Pessoas.class, id);

        session.getTransaction().commit();
        session.close();

        return pessoa;
    }
}
