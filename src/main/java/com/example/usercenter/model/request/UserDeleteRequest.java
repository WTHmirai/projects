package com.example.usercenter.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDeleteRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -4826565831548129208L;

    private long id;
}
