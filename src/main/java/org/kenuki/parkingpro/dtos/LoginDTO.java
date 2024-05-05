package org.kenuki.parkingpro.dtos;

import lombok.Data;

@Data
public class LoginDTO {
    private String mailOrNick;
    private String password;
}
