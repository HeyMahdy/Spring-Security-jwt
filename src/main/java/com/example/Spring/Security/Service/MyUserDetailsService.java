package com.example.Spring.Security.Service;
import com.example.Spring.Security.Entity.User;
import com.example.Spring.Security.Repostry.UserRepostry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private  UserRepostry userRepostry;


    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username: {}", username);
        User user1 = userRepostry.findByUsername(username);
        if (user1 == null) {
            logger.warn("User not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }
        logger.info("User found: {}", user1.getUsername());
        User user = userRepostry.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        else {
            return new UserDetailServiceImpl(user);
        }
    }
}




