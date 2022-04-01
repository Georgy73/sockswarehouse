package com.example.sockwarehouse.services;

import com.example.sockwarehouse.entities.Sock;
import com.example.sockwarehouse.repositories.SockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SockService {

    @Autowired
    private SockRepository sockRepository;

    @Transactional
    public void addSock(Sock sock) {
        Sock sockFromDB = sockRepository.findByColorAndCottonPart(sock.getColor(), sock.getCottonPart());
        if(sockFromDB != null) {
            sockFromDB.setQuantity(sockFromDB.getQuantity() + sock.getQuantity());
            sockRepository.save(sockFromDB);
        } else {
            sockRepository.save(sock);
        }
    }

    @Transactional
    public void subtract(Sock sock) {
        Sock sockFromDB = sockRepository.findByColorAndCottonPart(sock.getColor(), sock.getCottonPart());
        if(sockFromDB != null) {
            sockFromDB.setQuantity(sockFromDB.getQuantity() - sock.getQuantity());
            sockRepository.save(sockFromDB);
        } else {
            throw new NullPointerException();
        }
    }

    @Transactional
    public String findByParameters(String color, String operation, int cottonPart) {
        List<Sock> socks;
        switch(operation) {
            case "moreThan":
                socks = sockRepository.findByColorAndCottonPartGreaterThan(color, cottonPart);
                break;
            case "lessThan":
                socks = sockRepository.findByColorAndCottonPartLessThan(color, cottonPart);
                break;
            case "equal":
                socks = sockRepository.findByColorAndCottonPartEquals(color, cottonPart);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
        }
        int sum = socks.stream().mapToInt(Sock::getQuantity).sum();
        return Integer.toString(sum);
    }
}
