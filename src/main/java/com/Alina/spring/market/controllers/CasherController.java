package com.Alina.spring.market.controllers;

import com.Alina.spring.market.entities.Item;
import com.Alina.spring.market.entities.TransactionHistory;
import com.Alina.spring.market.entities.User;
import com.Alina.spring.market.interfacesDAO.ItemDAO;
import com.Alina.spring.market.interfacesDAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@SessionAttributes("chosenItemList")
public class CasherController {
    @Autowired
    private UserDAO userBean;

    @Autowired
    private ItemDAO itemBean;


    @GetMapping(value = "/casherHome")
    public ModelAndView casherHome(@SessionAttribute("user") User user){
        ModelAndView mw = new ModelAndView("casherHomePage","user",user);
        mw.addObject("chosenItemList", new ArrayList<Item>());
        return mw;
    }

    @PostMapping(value = "/addProduct")
    public ModelAndView addProduct(@RequestParam("uniProductCode") String uniProductCode, @SessionAttribute("chosenItemList") ArrayList<Item> chosenItems) {
        Item item = itemBean.getItemByUPC(uniProductCode);
        item.setAmounts(item.getAmounts() - 1);
        itemBean.addItem(item);
        ModelAndView mw = null;
        if ((item == null) || (item.getAmounts() < 0)){
            mw = new ModelAndView("casherHomePage", "error", "No such item or it is not enough");
        } else {
            boolean ok = false;
            for (Item i : chosenItems) {
                if (i.getUpc().equalsIgnoreCase(uniProductCode)) {
                    i.setAmounts(i.getAmounts() + 1);
                    i.setPrice(i.getPrice() + item.getPrice());
                    ok = true;
                }
            }
            if (ok == false) {
                item.setAmounts(1);
                chosenItems.add(item);
            }
            mw = new ModelAndView("casherHomePage", "chosenItemList", chosenItems);
        }
        return mw;
    }


    @PostMapping(value = "/submitSelling")
    public ModelAndView submitSelling(@SessionAttribute("chosenItemList") ArrayList<Item> chosenItems, @SessionAttribute("user") User user) {
        ArrayList<TransactionHistory> transactionHistories = new ArrayList<TransactionHistory>();
        if (chosenItems != null) {
            for (Item item : chosenItems) {
                item.setAmounts(itemBean.getItemByID(item.getId()).getAmounts());
                transactionHistories.add(new TransactionHistory(item, user, item.getAmounts()));
            }
            itemBean.addTransactionHistory(transactionHistories);
        }

        ModelAndView mw = new ModelAndView("redirect:/transactionHistoryCasher", "transList", (ArrayList<TransactionHistory>) itemBean.getAllTransactionHistories(user));
        return mw;
    }

    @GetMapping(value = "/transactionHistoryCasher")
    public ModelAndView transactionHistory( @SessionAttribute("user") User user){
        ModelAndView mw = new ModelAndView("casherTransactionHistory", "transList", (ArrayList<TransactionHistory>)itemBean.getAllTransactionHistories(user));
        return mw;
    }

}
