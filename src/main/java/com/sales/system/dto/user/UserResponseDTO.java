package com.sales.system.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {

    private final Long id;
    private final String name;
    private final String email;
    private final Long cartId;
}
