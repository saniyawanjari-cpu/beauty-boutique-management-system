
package com.saniya.beautyboutique.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.saniya.beautyboutique.entity.Customer;
import com.saniya.beautyboutique.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    CustomerRepository repo;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<Customer> customers = repo.findAll();

        double totalRevenue = 0;

        for(Customer c : customers) {

            totalRevenue += Double.parseDouble(c.getAmount());

        }

        model.addAttribute(
                "customers",
                customers
        );

        model.addAttribute(
                "totalBookings",
                customers.size()
        );

        model.addAttribute(
                "totalRevenue",
                totalRevenue
        );

        return "dashboard";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @PostMapping("/login")
    public String login(

            @RequestParam String username,
            @RequestParam String password,
            Model model

    ) {

        if(username.equals("admin")
                &&
                password.equals("Glow@2026")) {

            return "redirect:/dashboard";
        }

        model.addAttribute(
                "error",
                "Invalid Username or Password"
        );

        return "login";
    }

    @GetMapping("/")
    public String home(Model model) {

        long totalBookings = repo.count();

        double totalRevenue = 0;

        for(Customer c : repo.findAll()) {

            totalRevenue += Double.parseDouble(c.getAmount());
        }

        model.addAttribute("totalBookings", totalBookings);

        model.addAttribute("totalRevenue", totalRevenue);

        return "index";
    }

    @PostMapping("/save")
    public String saveCustomer(Customer customer, Model model) {

        repo.save(customer);

        model.addAttribute(
                "message",
                "Appointment Saved Successfully"
        );

        return "redirect:/";
    }

    @GetMapping("/bookings")
    public String viewBookings(Model model) {

        model.addAttribute(
                "customers",
                repo.findAll()
        );

        return "bookings";
    }

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {

        repo.deleteById(id);

        return "redirect:/bookings";
    }

    @GetMapping("/edit/{id}")
    public String editBooking(
            @PathVariable Long id,
            Model model
    ) {

        Customer customer =
                repo.findById(id).orElse(null);

        model.addAttribute(
                "customer",
                customer
        );

        return "edit";
    }

    @PostMapping("/update")
    public String updateBooking(Customer customer) {

        repo.save(customer);

        return "redirect:/bookings";
    }
}