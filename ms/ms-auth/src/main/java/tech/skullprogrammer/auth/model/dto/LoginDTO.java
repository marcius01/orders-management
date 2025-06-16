package tech.skullprogrammer.auth.model.dto;

public record LoginDTO() {

    public record In(String email, String password) {}
    public record Out(String token) {}
}
