package com.tobeto.spring.b;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/persons")                  // Bu controllerın kontrol edeceği alt routeları tanımlamak

public class PersonsController {
     List<Person> inMemoryList = new ArrayList<>();
     @GetMapping // api/persons
     public List<Person> get(){
          return inMemoryList;
     }
     //@GetMapping("getById")
     // query string, path variable
     // path => https://localhost:8080/api/persons/30/halit/19
     // query string => https://localhost:8080/api/persons/getById?id=1&name=halit&age=19
     @GetMapping("{id}")
     public Person getById(@PathVariable int id){
          // Lambda Expressions
          // Stream API
          Person person = inMemoryList                //Optional<Person> person = inMemoryList   //.orElseThrow
                  .stream()
                  // Lambda Expressions
                  .filter((p) -> p.getId() == id) // 1 eleman [ {} ]
                  .findFirst() // {}
                  .orElseThrow();
          return person;
     }

     @PostMapping
     public Person post(@RequestBody Person person) {
      inMemoryList.add(person);
      return person;
     }

     @PutMapping("{id}")
     public ResponseEntity<String> personUpdate(@PathVariable int id, @RequestBody Person person) {
               Optional<Person> existingPerson = inMemoryList.stream().filter(p -> p.getId() == id).findFirst();

               if (existingPerson.isPresent()) {
                    Person newPerson = existingPerson.get();
                    newPerson.setId(person.getId());
                    newPerson.setName(person.getName());
                    newPerson.setSurname(person.getSurname());
                    newPerson.setAge(person.getAge());
                    return new ResponseEntity<>("Kişi güncellendi", HttpStatus.OK);
               } else {
                    return new ResponseEntity<>("Bu ID'ye sahip kişi bulunamadı", HttpStatus.NOT_FOUND);
               }
          }
     @DeleteMapping("{id}")
     public Person deleteById(@PathVariable int id){
          Person person = inMemoryList
                  .stream()
                  .filter((p) -> p.getId() == id)
                  .findFirst() // {}
                  .orElseThrow();
          return person;
     }

     }