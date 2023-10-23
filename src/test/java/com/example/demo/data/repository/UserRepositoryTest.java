package com.example.demo.data.repository;

import com.example.demo.data.models.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;




@DataMongoTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testThatRepositoryCanSave(){
        User user = new User();
        userRepository.save(user);
        assertThat(user.getId()).isNotNull();
    }
}