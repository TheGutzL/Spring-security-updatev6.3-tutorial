package com.security.presentation.controller;

import java.util.List;

import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.persistence.entity.Person;
import com.security.service.PersonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final PersonService personService;
    private final CompromisedPasswordChecker passwordChecker;

    @GetMapping("/admin")
    public String sayHelloAdmin() {
        return "Hello World Admin";
    }

    @GetMapping("/user")
    public String sayHelloUser() {
        return "Hello World User";
    }

    @GetMapping("/invited")
    public String sayHelloInvited() {
        return "Hello World Invited";
    }

    @GetMapping("/find")
    public Person findById() {
        return this.personService.findById().orElseThrow();
    }

    @GetMapping("/findAll")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {

        // Web: https://haveibeenpwned.com/Passwords
        CompromisedPasswordDecision decision = this.passwordChecker.check(password);

        if (decision.isCompromised()) {
            throw new IllegalArgumentException("Compromised password");
        }

        return String.format("User %s registered successfully", username);
    }
}
