package com.example.motorcycleshop.controller;

import com.example.motorcycleshop.model.Client;
import com.example.motorcycleshop.model.Role;
import com.example.motorcycleshop.model.RoleToUser;
import com.example.motorcycleshop.service.ClientService;
import org.json.JSONObject;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

private final ClientService clientService;

    public ClientController(ClientService clientService) {

        this.clientService = clientService;
    }

    @PostMapping("/client")
    @ResponseBody()
    public ResponseEntity<?> registerClient(@RequestBody Client client, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/client/save").toUriString());
        clientService.registerClient(client, getSiteURL(request));
        return ResponseEntity.created(uri).body(JSONObject.quote("Client saved"));
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyClient(@Param("code") String code){
        if(clientService.verify(code)){
            return ResponseEntity
                    .ok()
                    .body("verify_ok");
        }else{
            return ResponseEntity.badRequest().body("verify_fail");
        }
    }
    @GetMapping("/clients")
    public ResponseEntity<List<Client>>getClients(){

        return ResponseEntity.ok().body(clientService.getClients());
    }
    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri =URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/role/save")
                .toUriString());
        return ResponseEntity.created(uri).body(clientService.saveRole(role));
    }
    @PostMapping("/role/toClient")
    public ResponseEntity<?>addRoleToClient(@RequestBody RoleToUser role){
        clientService.addRoleToClient(role.getRoleName(),role.getUserName());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.clientService.refresh(request, response);
    }
}
