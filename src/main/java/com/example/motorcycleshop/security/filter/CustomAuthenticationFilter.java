package com.example.motorcycleshop.security.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.motorcycleshop.model.Client;
import com.example.motorcycleshop.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final ClientRepository clientRepository;
    private final AuthenticationManager authenticationManager;


    public CustomAuthenticationFilter(ClientRepository clientRepository, AuthenticationManager authenticationManager) {
        this.clientRepository = clientRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.printf("user: %s have password: %s ", username, password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("basketName", findBasketName(user.getUsername()))
                .withClaim("name", findName(user.getUsername()))
                .withClaim("isVerified", isUserVerified(user.getUsername()))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    private Boolean isUserVerified(String clientName) {
        Client client = this.clientRepository.findByClientName(clientName).orElseThrow();
        return client.isVerified();
    }

    String findBasketName(String clientName) {
        Client client = this.clientRepository.findByClientName(clientName).orElseThrow();
        return client.getBasket().getBasketName();
    }

    String findName(String clientName) {
        Client client = this.clientRepository.findByClientName
                (clientName).orElseThrow();
        return client.getClientName();
    }

    public static String get_admin_access_token(String name) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");

        return JWT.create()
                .withSubject(name)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withClaim("isVerified", true)
                .withClaim("roles", roles)
                .sign(algorithm);
    }
}
