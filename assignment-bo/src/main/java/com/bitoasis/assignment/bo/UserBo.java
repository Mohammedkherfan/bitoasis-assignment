package com.bitoasis.assignment.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBo implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String password;
}
