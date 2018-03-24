package com.Alina.spring.market.controllers;


import com.Alina.spring.market.entities.Item;
import com.Alina.spring.market.entities.Role;
import com.Alina.spring.market.entities.User;
import com.Alina.spring.market.interfacesDAO.ItemDAO;
import com.Alina.spring.market.interfacesDAO.UserDAO;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("user")
public class HomeController {

    @Autowired
    private UserDAO userBean;

    @Autowired
    private ItemDAO itemBean;

    @RequestMapping(value = {"/", "index",""}, method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }


    @RequestMapping(value = {"/home","/login"}, method = RequestMethod.GET)
    public ModelAndView home(@SessionAttribute("user") User user){
        ModelAndView mw = null;
        if(user == null)
            return new ModelAndView("index");
        else {
            if(user.getRole().getName().equalsIgnoreCase("ADMIN")){
                mw = new ModelAndView("adminHomePage","user",user);
                mw.addObject("itemsList",itemBean.getAllItems());
                mw.addObject("casherList",userBean.getAllUsers());
                mw.addObject("transList",itemBean.getAllTransactionHistories());
            } else {
                mw = new ModelAndView("casherHomePage","user",user);
                mw.addObject("chosenItemList", new ArrayList<Item>());
                mw.addObject("transList",itemBean.getAllTransactionHistories(user));
            }
            return mw;
        }
        }


    @PostMapping(value = "/login")
    public ModelAndView login(@RequestParam("login")String login, @RequestParam("password")String password) {
        User user = userBean.getUserByLoginAndPassword(login,password);
        ModelAndView mw = null;
        if(user == null){
            mw = new ModelAndView("redirect:/index", "error","Wrong password or login");
        } else {
            mw = new ModelAndView("redirect:/home","user",user);
        }
        return mw;
    }



    @GetMapping(value = "/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/index";
    }


    @GetMapping(value = "/editProfile")
    public ModelAndView editProfile(@SessionAttribute("user") User user){
        ModelAndView mw = new ModelAndView("profile", "user",user);
        mw.addObject("roleList",userBean.getAllRoles());
        return mw; }

    @PostMapping(value = "/editProfile")
    public ModelAndView editProfileP(@ModelAttribute("user") User user){
        userBean.addUser(user);
        ModelAndView mw = new ModelAndView("redirect:/home");
        return mw;
    }
}
