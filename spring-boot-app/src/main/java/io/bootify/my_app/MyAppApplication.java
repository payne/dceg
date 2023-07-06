package io.bootify.my_app;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import java.util.*;

import lombok.*;

import io.bootify.my_app.todo.*;


@SpringBootApplication
public class MyAppApplication implements CommandLineRunner {

    @Autowired
    private TodoService todoService;

    public static void main(final String[] args) {
        SpringApplication.run(MyAppApplication.class, args);
    }

    public void run(String... args) {
        final List<String> items = Arrays.asList("Code Night", "Friday Chores", "Saturday Toastmasters");
        for (String itemName : items) {
            TodoDTO todo = TodoDTO.builder().item(itemName).completed(false).build();
            todoService.create(todo);
        }
    }

}
