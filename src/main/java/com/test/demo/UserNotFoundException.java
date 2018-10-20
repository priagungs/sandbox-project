package com.test.demo;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private Long id;

    public UserNotFoundException(Long id) {
        super("not found");
        this.id = id;
    }

}
