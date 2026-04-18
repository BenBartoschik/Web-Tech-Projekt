package htw.webtech.projektname.controller;

import htw.webtech.projektname.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public List<User> getUsers() {
        return List.of(
                new User(1L, "Mia", "Musterfrau", 2000.0, 800.0),
                new User(2L, "Max", "Mustermann", 2500.0, 1000.0)
        );
    }
}