package com.attention.demo.Services;


import com.attention.demo.Models.User;
import com.attention.demo.Models.UserPrincipal;
import com.attention.demo.Repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserRepo userRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user==null)
            throw new UsernameNotFoundException("User 404");
        return new UserPrincipal(user);
    }
}
