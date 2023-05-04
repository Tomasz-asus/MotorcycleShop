package com.example.motorcycleshop.service;

import com.example.motorcycleshop.model.Client;
import com.example.motorcycleshop.model.Role;
import com.example.motorcycleshop.repository.BasketRepository;
import com.example.motorcycleshop.repository.ClientRepository;
import com.example.motorcycleshop.repository.RoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
@Service
@Transactional
public class ClientServiceImp implements ClientService, UserDetailsService {


    private final ClientRepository clientRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final BasketRepository basketRepository;

    public ClientServiceImp(ClientRepository clientRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder,
                            BasketRepository basketRepository) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.basketRepository = basketRepository;
    }

    @Override
    public void registerClient(Client client, String siteUrl) throws MessagingException, UnsupportedEncodingException {

    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public void addRoleToClient(String clientName, String roleName) {

    }

    @Override
    public Client saveAdmin(Client client) {
        return null;
    }

    @Override
    public Client getClient(String clientName) {
        return null;
    }

    @Override
    public List<Client> getClients() {
        return null;
    }

    @Override
    public boolean verify(String verificationCode) {
        return false;
    }

    @Override
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}//TODO
