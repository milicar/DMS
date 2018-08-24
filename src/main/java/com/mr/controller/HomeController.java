package com.mr.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

@SessionScope
@Controller
public class HomeController implements ErrorController{


    private static final String PATH = "/error";
            
    @RequestMapping("/")    
    public ModelAndView start(){
        return new ModelAndView("index.xhtml");
    }
    
    @RequestMapping(value=PATH)
    public ModelAndView error(){
        return new ModelAndView("index.xhtml");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
    
    
}
