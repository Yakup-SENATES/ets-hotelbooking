package com.etstur.hotelbooking.controller;


import com.etstur.hotelbooking.entity.Reservation;
import com.etstur.hotelbooking.entity.User;
import com.etstur.hotelbooking.services.ReservationService;
import com.etstur.hotelbooking.services.UserService;
import com.etstur.hotelbooking.temp.CurrentReservation;
import com.etstur.hotelbooking.temp.CurrentUser;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/")
public class HotelReservationController {

    private final UserService userService;
    private final ReservationService reservationService;

    public HotelReservationController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }
    
    @PostConstruct
    public void init() {
        CurrentUser currentUser = new CurrentUser();
        currentUser.setUserName("John");
        currentUser.setEmail("q@q.q");
        currentUser.setPassword("123");
        CurrentReservation reservation = new CurrentReservation();
        userService.saveUser(currentUser);
    }


    //birden fazla form doğrulama yapılacaksa bu methodu kullanabiliriz.
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class,stringTrimmerEditor);
    }


    @GetMapping
    public String homePage(){
        return "home-page";
    }

    @GetMapping("/login-form-page")
    public String loginFormPage(Model model) {
        // if user not logged in, redirect to login page
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        //new user attribute for sign up form
        model.addAttribute("newUser", new CurrentUser());

        return "login";
    }

    //registration process page
    @PostMapping("/processRegistration")
    public String processRegistration(@Valid @ModelAttribute("newUser") CurrentUser currentUser,
                                      BindingResult bindingResult,Model model) {
        //check if user already exists
        User user = userService.findUserByEmail(currentUser.getEmail());
        if (user != null) {
            model.addAttribute("registrationError", "User already exists");
            model.addAttribute("newUser", new CurrentUser());
            return "login";
        }
        //create new user
        userService.saveUser(currentUser);
        model.addAttribute("registrationSuccess", "Registration successful");
        return "redirect:/login-form-page";
    }


    //Booking page
    @GetMapping("/new-reservation")
    public String newReservation(Model model) {
        model.addAttribute("newRes", new CurrentUser());
        model.addAttribute("hotel", new CurrentReservation());
        return "reservation-page";
    }

    //save new reservation
    @PostMapping("/proceed-reservation")
    public String proceedReservation(@Valid @ModelAttribute("newRes") CurrentReservation currentReservation,
                                  BindingResult bindingResult,Model model) {
        // send reservation to service to save it
        reservationService.saveOrUpdateReservation(currentReservation);
        return "redirect:/your-reservations";
    }

    //reservation of user
    @GetMapping("/your-reservations")
    //list of reservations for logged user
    public String reservationList(Model model) {
        model.addAttribute("resList", reservationService.getReservationsForLoggedUser());
        return "your-reservations";
    }

    //update Reservation
    @PostMapping("/reservation-update")
    public String updateReservation(@RequestParam("resId") int resId, Model model) {

        //new update reservation attribute send to services to store it
        model.addAttribute("newRes", reservationService.reservationToCurrentReservation((long)resId));
        return "reservation-update";
    }
    //delete Reservation
    @PostMapping("/reservation-delete")
    public String deleteReservation(@RequestParam("resId") int resId) {

        //delete reservation sent to services to delete it from database
        reservationService.deleteReservation((long)resId);
        return "redirect:/your-reservations";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        // handle logout for logged user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login-form-page?logout";
    }


}
