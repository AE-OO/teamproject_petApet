package com.teamproject.petapet.domain.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CounterRepositoryTest {

    @Autowired
    CounterRepository counterRepository;

    @Test
    public void insertCounterDummies(){
        LongStream.rangeClosed(1, 30).forEach(i -> {
            Product product = Product.builder()
                    .productId(i).build();

            Counter counter = Counter.builder()
                    .product(product)
                    .build();

            counterRepository.save(counter);
        });
    }
}