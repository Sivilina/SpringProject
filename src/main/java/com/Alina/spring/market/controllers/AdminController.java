package com.Alina.spring.market.controllers;

import com.Alina.spring.market.entities.Item;
import com.Alina.spring.market.entities.User;
import com.Alina.spring.market.interfacesDAO.ItemDAO;
import com.Alina.spring.market.interfacesDAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("user")
public class AdminController {

    @Autowired
    private UserDAO userBean;

    @Autowired
    private ItemDAO itemBean;

    @GetMapping(value = "/addItem")
    public ModelAndView addItem(){
        return new ModelAndView("addItem","item",new Item());
    }

    @PostMapping(value = "/addItem")
    public ModelAndView addItem(@ModelAttribute("item")Item item){
        ModelAndView mw = null;
        if((item.getPrice() < 0) || (item.getAmounts() < 0)){
            mw = new ModelAndView("addItem", "item",item);
            mw.addObject("error","No negative numbers!");
            return mw;
        }
        itemBean.addItem(item);
        mw = new ModelAndView("redirect:/home");
        return mw;
    }

    @GetMapping(value = "/editItem")
    public ModelAndView editItem(@RequestParam("id") Long id){
        Item item = itemBean.getItemByID(id);
        return new ModelAndView("addItem","item",item);
    }

    @GetMapping(value = "/deleteItem")
    public ModelAndView deleteItem(@RequestParam("id") Long id){
        itemBean.deleteItem(itemBean.getItemByID(id));
        return new ModelAndView("redirect:/home");
    }

    @GetMapping(value = "/transactionHistory")
    public ModelAndView transactionHistory(){
        ModelAndView mw = new ModelAndView("adminTransactionHistory","transList",itemBean.getAllTransactionHistories());
        mw.addObject("itemsList",itemBean.getAllItems());
        mw.addObject("casherList",userBean.getAllUsers());
        return mw;
    }

    @GetMapping(value = "/casherList")
    public ModelAndView cashers(){
        ModelAndView mw = new ModelAndView("adminCasherList","casherList",userBean.getAllUsers());
        mw.addObject("itemsList",itemBean.getAllItems());
        mw.addObject("transList",itemBean.getAllTransactionHistories());
        return mw;
    }

    @GetMapping(value = "/addCasher")
    public ModelAndView register() {
        ModelAndView mw = new ModelAndView("register", "person", new User());
        mw.addObject("roleList",userBean.getAllRoles());
        return mw;
    }

    @PostMapping(value = "/addCasher")
    public ModelAndView addCasher(@ModelAttribute("person")User person){
        userBean.addUser(person);
        ModelAndView mw = new ModelAndView("redirect:/home");
        return mw;
    }

    @GetMapping(value = "/editCasher")
    public ModelAndView editCasher(@RequestParam("id") Long id){
        User user = userBean.getUserById(id);
        ModelAndView mw = new ModelAndView("register","person",user);
        mw.addObject("roleList",userBean.getAllRoles());
        return mw;
    }

    @GetMapping(value = "/deleteCasher")
    public ModelAndView deleteCasher(@RequestParam("id") Long id){
        userBean.deleteUser(userBean.getUserById(id));
        return new ModelAndView("redirect:/casherList");
    }
}