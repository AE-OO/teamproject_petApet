package com.teamproject.petapet.domain.product;

import com.teamproject.petapet.domain.product.repository.CounterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.LongStream;

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