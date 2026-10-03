package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class CustomerControllers {

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/indice")
    public String indice() {
        return "Hola CON SEGURIDAD";
    }

    @GetMapping("/indice2")
    public String indice2() {
        return "Hola SIN seguridad ";
    }

    @GetMapping("/sesion")
    public ResponseEntity<?> getDetallesDeLaSesion() {
        String sesionId = "";
        User userObj = null;

        List<Object> sesionesList = sessionRegistry.getAllPrincipals();
        for(Object sesion : sesionesList) {

            if(sesion instanceof User) {
                userObj = (User) sesion;
            }

            List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(sesion, false);

            for(SessionInformation item : sessionInformationList) {
                sesionId = item.getSessionId();
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("respuesta", "OLitas desde aquí");
        response.put("sesionId", sesionId);
        response.put("sesionUser", userObj);

        return ResponseEntity.ok(response);
    }
}
