package ru.sapteh.DAO.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.UsersForAut;

import java.util.List;

public class AutentService implements DAO<UsersForAut, Integer> {
    private final SessionFactory factory;

    public AutentService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(UsersForAut usersForAut) {

    }

    @Override
    public UsersForAut read(Integer integer) {
        return null;
    }

    @Override
    public List<UsersForAut> readAll() {
        try(Session session = factory.openSession()) {
            String hql = "from UsersForAut";
            Query<UsersForAut> query = session.createQuery(hql);
            return query.getResultList();
        }
    }

    @Override
    public void update(UsersForAut usersForAut) {

    }

    @Override
    public void delete(UsersForAut usersForAut) {

    }
}
