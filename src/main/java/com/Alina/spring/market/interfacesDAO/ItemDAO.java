package com.Alina.spring.market.interfacesDAO;

import com.Alina.spring.market.entities.Item;
import com.Alina.spring.market.entities.TransactionHistory;
import com.Alina.spring.market.entities.User;

import java.util.List;

public interface ItemDAO {

    public List<Item> getAllItems();
    public List<TransactionHistory> getAllTransactionHistories();
    public List<TransactionHistory> getAllTransactionHistories(User u);
    public void addItem(Item item);
    public void addTransactionHistory(List<TransactionHistory> transactionHistory);
    public void deleteItem(Item item);
    public Item getItemByID(long id);

    public Item getItemByUPC(String upc);
}
