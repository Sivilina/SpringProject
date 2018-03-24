package com.Alina.spring.market.services;

import com.Alina.spring.market.entities.Role;
import com.Alina.spring.market.entities.TransactionHistory;
import com.Alina.spring.market.entities.User;
import com.Alina.spring.market.interfacesDAO.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAllUsers() {
        List<User> users = null;
            Session session = sessionFactory.getCurrentSession();
            Transaction tr = session.beginTransaction();
            Role r = (Role)session.get(Role.class,new Long(2));
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> usersRoot = criteria.from(User.class);
            Predicate predicate = builder.equal(usersRoot.get("role"), r);
            criteria.select(usersRoot);
            criteria.where(predicate);
            users = session.createQuery(criteria).getResultList();
            tr.commit();
        return users;
    }

    public List<Role> getAllRoles() {
        List<Role> roles = null;
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
        Root<Role> rolesRoot = criteria.from(Role.class);
        criteria.select(rolesRoot);
        roles = session.createQuery(criteria).getResultList();
        tr.commit();
        return roles;
    }

    public void addUser(User u){

        try {
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            session.saveOrUpdate(u);
            tr.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public User getUserByLoginAndPassword(String login, String password) {

        List<User> users = null;

        try{

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> usersRoot = criteria.from(User.class);
            Predicate predicate = builder.and(builder.equal(usersRoot.get("login"), login), builder.equal(usersRoot.get("password"), password));
            criteria.select(usersRoot);
            criteria.where(predicate);
            users = session.createQuery(criteria).getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        if(!users.isEmpty()){
            return users.get(0);
        }else{
            return null;
        }

    }

    public User getUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.beginTransaction();
        User user = session.get(User.class,id);
        tr.commit();
        return user;
    }

    public void deleteUser(User u) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.beginTransaction();
        session.delete(u);
        tr.commit();
    }


}

