package io.mountblue.blogapplication.security;

import io.mountblue.blogapplication.entities.Users;
import io.mountblue.blogapplication.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOError;
import java.io.IOException;

@Component
public class CustomAuthenticaionSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        HttpSession session = request.getSession();
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        Users u  = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );

        System.out.println(u.getEmail() + " " + u.getPassword() + " " + u.getRole());

        session.setAttribute("user", userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ));



        super.onAuthenticationSuccess(request, response, authentication);
    }

}
