
package com.saniya.beautyboutique.controller;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import com.saniya.beautyboutique.entity.Customer;
import com.saniya.beautyboutique.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    CustomerRepository repo;

    @GetMapping("/")

    public String home(Model model) {

        long totalBookings = repo.count();

        double totalRevenue = 0;

        for(Customer c : repo.findAll()) {

            totalRevenue += Double.parseDouble(
                    c.getAmount()
            );
        }

        model.addAttribute(
                "totalBookings",
                totalBookings
        );

        model.addAttribute(
                "totalRevenue",
                totalRevenue
        );

        return "index";
    }

    @PostMapping("/save")

    public String saveCustomer(Customer customer,
                               Model model) {

        repo.save(customer);

        model.addAttribute(
                "message",
                "Appointment Saved Successfully"
        );

        return "index";
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

    public String deleteBooking(
            @PathVariable Long id
    ) {

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

    public String updateBooking(
            Customer customer
    ) {

        repo.save(customer);

        return "redirect:/bookings";
    }

}
//isse bookings dikhegi