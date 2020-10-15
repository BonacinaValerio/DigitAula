package it.uppercase.hackathon2020.user;

public class UserUtil {
    public static boolean hasPermission(String role) {
        return role.equals(FetchUserInfoUseCase.ROLE_ADMIN) || role.equals(FetchUserInfoUseCase.ROLE_PROF);
    }
}
