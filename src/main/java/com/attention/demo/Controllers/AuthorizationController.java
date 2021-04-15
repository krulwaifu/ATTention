package com.attention.demo.Controllers;

import com.attention.demo.Models.EmailChecker;
import com.attention.demo.Models.Role;
import com.attention.demo.Models.User;
import com.attention.demo.Models.UsernamePasswordChecker;
import com.attention.demo.Repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
public class AuthorizationController {
    ExecutorService threadExecutor = Executors.newCachedThreadPool();

    @Autowired
    private IUserRepo userRepository;

    @PostMapping("/login")
    public String loginPage(@RequestParam(name = "username") String username){
        User user = userRepository.findByUsername(username);
        return "redirect:/profilePage/{user.getId()}";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam(name = "firstname") String firstname,
                          @RequestParam(name = "lastname") String lastname,
                          @RequestParam(name = "username") String username,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "email") String email,
                          Model model) throws ExecutionException, InterruptedException {
        User userForm = new User(username,new BCryptPasswordEncoder().encode(password),firstname,lastname,email);
        User isExist = userRepository.findByUsername(username);
        Future<Boolean> futureCall1 = threadExecutor.submit(new EmailChecker(email));
        Future<Boolean> futureCall2 = threadExecutor.submit(new UsernamePasswordChecker(username,password));
        boolean result1 =  futureCall1.get();
        boolean result2 = futureCall2.get();
        if(isExist != null){
            model.addAttribute("error","User exists!");
            return "auth";
        }
        if(!result1){
            model.addAttribute("error","Invalid format of email!");
            return "auth";
        }
        if(!result2){
            model.addAttribute("error","Invalid format of username or password!");
            return "auth";
        }
        userForm.setRoles(Collections.singleton(Role.USER));
        model.addAttribute("success","You have been registered!");
        userRepository.save(userForm);
        return "auth";
    }

    @GetMapping("/logout-success")
    public String logoutPage(){
        return "auth";
    }

    @GetMapping("/auth")
    public String auth(){
        return "auth";
    }
}
