package com.example.motorcycleshop.service;

import com.example.motorcycleshop.model.Client;
import com.example.motorcycleshop.model.Role;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ClientService {

    void registerClient(Client client, String siteUrl) throws MessagingException, UnsupportedEncodingException;

    Role saveRole(Role role);

    void addRoleToClient(String clientName, String roleName);

    Client saveAdmin(Client client);

    Client getClient(String clientName);

    List<Client> getClients();

    boolean verify(String verificationCode);

    void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException;


}
