package com.Alina.spring.market.services;

import com.Alina.spring.market.entities.Item;
import com.Alina.spring.market.entities.TransactionHistory;
import com.Alina.spring.market.entities.User;
import com.Alina.spring.market.interfacesDAO.ItemDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<Item> getAllItems() {
        List<Item> items = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Transaction tr = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
            Root<Item> itemsRoot = criteria.from(Item.class);
            criteria.select(itemsRoot);
            items = session.createQuery(criteria).getResultList();
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<TransactionHistory> getAllTransactionHistories() {
        List<TransactionHistory> transactionHistories = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Transaction t = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TransactionHistory> criteria = builder.createQuery(TransactionHistory.class);
            Root<TransactionHistory> transactionHistoryRoot = criteria.from(TransactionHistory.class);
            criteria.select(transactionHistoryRoot);
            transactionHistories = session.createQuery(criteria).getResultList();
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionHistories;
    }

    public List<TransactionHistory> getAllTransactionHistories(User u) {
        List<TransactionHistory> transactionHistories = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Transaction t = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TransactionHistory> criteria = builder.createQuery(TransactionHistory.class);
            Root<TransactionHistory> transactionHistoryRoot = criteria.from(TransactionHistory.class);
            Predicate predicate = builder.equal(transactionHistoryRoot.get("user"), u);
            criteria.select(transactionHistoryRoot);
            criteria.where(predicate);
            transactionHistories = session.createQuery(criteria).getResultList();
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionHistories;
    }

    public void addItem(Item item) {
            Session session = sessionFactory.getCurrentSession();
            Transaction tr = session.beginTransaction();
            session.saveOrUpdate(item);
            tr.commit();
    }

    public void addTransactionHistory(List<TransactionHistory> transactionHistory) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Transaction tr = session.beginTransaction();
            for (TransactionHistory t : transactionHistory)
                session.save(t);
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(Item item) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.beginTransaction();
        session.delete(item);
        tr.commit();
    }

    public Item getItemByID(long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.beginTransaction();
        Item item = session.get(Item.class,id);
        tr.commit();
        return item;
    }

    public Item getItemByUPC(String upc) {
        List<Item> items = null;
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
        Root<Item> itemsRoot = criteria.from(Item.class);
        Predicate predicate = builder.equal(itemsRoot.get("upc"),upc);
        criteria.select(itemsRoot);
        criteria.where(predicate);

        items = session.createQuery(criteria).getResultList();
        if(items!=null)
            return items.get(0);
        else
            return null;
    }
}

