package tech.skullprogrammer.auth.model.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class UserDTO {

    public record In(String firstName, String lastName, String username, @NotBlank String email, @NotBlank String password){}
    public record Out(String firstName, String lastName, String username, String email, Set<String> roles){}

}
