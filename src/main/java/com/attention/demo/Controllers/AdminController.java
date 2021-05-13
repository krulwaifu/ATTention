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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
public class AdminController {
    ExecutorService threadExecutor = Executors.newCachedThreadPool();

    @Autowired
    IUserRepo userRepository;

    @PostMapping("/admin/editUser")
    public String editUser(@RequestParam(name = "user_id") String id,
                          @RequestParam(name = "firstname") String firstname,
                          @RequestParam(name = "lastname") String lastname,
                          @RequestParam(name = "username") String username,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "email") String email,
                          Model model) throws ExecutionException, InterruptedException {
        User userForm = new User(username,new BCryptPasswordEncoder().encode(password),firstname,lastname,email);
        User isExist = userRepository.findById(Long.parseLong(id));
        Future<Boolean> futureCall1 = threadExecutor.submit(new EmailChecker(email));
        Future<Boolean> futureCall2 = threadExecutor.submit(new UsernamePasswordChecker(username,password));
        boolean result1 =  futureCall1.get();
        boolean result2 = futureCall2.get();
        if(isExist != null){
            userForm.setRoles(Collections.singleton(Role.USER));
            model.addAttribute("success","User was edited!");
            userRepository.save(userForm);
            return "admin";
        }
        if(!result1){
            model.addAttribute("error","Invalid format of email!");
            return "admin";
        }
        if(!result2){
            model.addAttribute("error","Invalid format of username or password!");
            return "admin";
        }
        model.addAttribute("error","No such user");
        return "admin";
    }

    @PostMapping("/admin/deleteUser")
    public String deletePoll(@RequestParam(name = "user_id") String id,Model model) throws ExecutionException, InterruptedException {
        User user = userRepository.findById(Long.parseLong(id));
        user.setRoles(null);
        userRepository.delete(user);
        model.addAttribute("success","User was deleted!");

        return "admin";
    }

    @GetMapping("/admin/allUsers")
    public String showUsers(Model model){
        List<User> user = userRepository.findAll();
        model.addAttribute("user",user);
        return "admin";
    }
}
