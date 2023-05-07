package com.example.motorcycleshop.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.motorcycleshop.exceptions.BasketNotFoundException;
import com.example.motorcycleshop.exceptions.ClientNotFoundException;
import com.example.motorcycleshop.model.Basket;
import com.example.motorcycleshop.model.Client;
import com.example.motorcycleshop.model.Role;
import com.example.motorcycleshop.repository.BasketRepository;
import com.example.motorcycleshop.repository.ClientRepository;
import com.example.motorcycleshop.repository.MotorcycleRepository;
import com.example.motorcycleshop.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@Transactional
public class ClientServiceImp implements ClientService, UserDetailsService {
    private JavaMailSender mailSender;
    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BasketRepository basketRepository;
    private final MotorcycleRepository motorcycleRepository;

    public ClientServiceImp(ClientRepository clientRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder,
                            BasketRepository basketRepository,
                            MotorcycleRepository motorcycleRepository) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.basketRepository = basketRepository;
        this.motorcycleRepository = motorcycleRepository;
    }
    @Override
    public void registerClient(Client client, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        if (clientRepository.findByClientName(client.getClientName()).isEmpty()) {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            client.getRoles().add(roleRepository.findByName("USER"));
            String basketCustomName = MotorcycleService.generate(20);
            Basket basket = new Basket(basketCustomName);
            basketRepository.save(basket);
            client.setBasket(basketRepository.findByBasketName(basketCustomName).get());
            String randomCode = MotorcycleService.generate(64);
            client.setVerificationCode(randomCode);
            sendVerificationEmail(client, siteUrl);
            clientRepository.save(client);
        } else {
            throw new ClientNotFoundException("Client" + client.getClientName() + " already exist.");
        }
    }
    private void sendVerificationEmail(Client client, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = client.getClientName();
        String fromAddress = "tomaszojava@gmail.com";
        String senderName = "MotorcycleShop";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "MotorShop.pl.";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", client.getClientName());
        String verifyUrl = siteURL + "/api/verify?code=" + client.getVerificationCode();
        content = content.replace("[[URL]]", verifyUrl);
        helper.setText(content, true);
        mailSender.send(mimeMessage);
    }
    @Override
    public void addRoleToClient(String clientName, String name) {
        Client client = clientRepository.findByClientName(clientName).orElseThrow(() ->
                new ClientNotFoundException("Client" + clientName + "was not found"));
        Role role = roleRepository.findByName(name);
        client.getRoles().add(role);
    }
    @Override
    public Client saveAdmin(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.getRoles().add(roleRepository.findByName("ADMIN"));
        String basketCustomName = MotorcycleService.generate(20);
        Basket basket = new Basket(basketCustomName);
        basketRepository.save(basket);
        client.setBasket(basketRepository.findByBasketName(basketCustomName).orElseThrow(() ->
                new BasketNotFoundException("Basket" + basketCustomName + "was not found")));
        client.setVerified(true);
        return clientRepository.save(client);
    }
    @Override
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC512("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String clientName = decodedJWT.getSubject();
                Client client = getClient(clientName);
                String access_token = JWT.create()
                        .withSubject(client.getClientName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("basketName", findBasketName(client.getClientName()))
                        .withClaim("name", findName(client.getClientName()))
                        .withClaim("isVerified", isClientVerified(client.getClientName()))
                        .withClaim("roles", client.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("access_token", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh Token is Missing");
        }
    }
    private boolean isClientVerified(String clientName) {
        Client client = this.clientRepository.findByClientName(clientName).orElseThrow();
        return client.isVerified();
    }
    private String findName(String clientName) {
        Client client = this.clientRepository.findByClientName(clientName).orElseThrow();
        return client.getClientName();
    }
    private String findBasketName(String clientName) {
        Client client = this.clientRepository.findByClientName(clientName).orElseThrow(() -> new RuntimeException("Basket belongind to " + clientName + " not found"));
        return client.getBasket().getBasketName();
    }
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
    @Override
    public UserDetails loadUserByUsername(String clientName) throws UsernameNotFoundException {
        Client client = clientRepository.findByClientName(clientName).orElseThrow(() ->
                new ClientNotFoundException("Client " + clientName + " was not found."));
        System.out.println("Client found");
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        client.getRoles().forEach(role ->
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(client.getClientName(), client.getPassword(), authorities);
    }
    @Override
    public Client getClient(String clientName) {
        return clientRepository.findByClientName(clientName).orElseThrow();
    }
    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }
    @Override
    public boolean verify(String verificationCode) {
        Client client = clientRepository.findByVerificationCode(verificationCode);

            if(client == null || client.isVerified()){
                return false;
            } else{
                client.setVerificationCode(null);
                client.setVerified(true);
                clientRepository.save(client);
                return true;
            }
    }
}
