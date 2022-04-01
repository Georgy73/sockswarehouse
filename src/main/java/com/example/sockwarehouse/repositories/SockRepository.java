package com.example.sockwarehouse.repositories;

import com.example.sockwarehouse.entities.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SockRepository extends CrudRepository<Sock, Integer>, JpaRepository<Sock, Integer> {
    Sock findByColorAndCottonPart(String color, int cottonPart);

    List<Sock> findByColorAndCottonPartLessThan(String color, int cottonPart);
    List<Sock> findByColorAndCottonPartGreaterThan(String color, int cottonPart);
    List<Sock> findByColorAndCottonPartEquals(String color, int cottonPart);


}
