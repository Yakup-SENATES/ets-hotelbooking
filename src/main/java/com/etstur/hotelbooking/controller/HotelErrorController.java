package com.etstur.hotelbooking.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HotelErrorController implements ErrorController {

    //handle any http that didn't match any other controller and redirect to home

    @RequestMapping(name = "/error")
    public String handleError() {
        return "redirect:/";
    }

    //set error path
    public String getErrorPath() {
        return "/error";
    }

}
