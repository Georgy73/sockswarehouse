package com.example.sockwarehouse.controllers;

import com.example.sockwarehouse.entities.Sock;
import com.example.sockwarehouse.services.SockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class SockController {

    @Autowired
    private SockService sockService;

    @PostMapping(value = "/api/socks/income")
    public ResponseEntity<?> income(@RequestBody Sock sock) {
        if(sock.getQuantity() <= 0 || sock.getCottonPart() < 0 || sock.getCottonPart() > 100) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        sockService.addSock(sock);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/api/socks/outcome")
    public ResponseEntity<?> outcome(@RequestBody Sock sock) {
        try {
            sockService.subtract(sock);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/api/socks")
    public ResponseEntity<String> countSocks(@RequestParam(value = "color") String color,
                                             @RequestParam(value = "operation") String operation,
                                             @RequestParam(value = "cottonPart") int cottonPart) {
        if(!(operation.equals("moreThan") || operation.equals("lessThan") || operation.equals("equal"))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String count = sockService.findByParameters(color, operation, cottonPart);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
