package org.example.expert.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequest {

    @NotBlank(message = "기존 비밀번호는 필수 값입니다.")
    private String oldPassword;
    @NotBlank(message = "새 비밀번호는 필수 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\\\d).*$", message = "비밀번호는 대문자와 숫자를 최소 1개 이상 포함해야 합니다.")
    private String newPassword;
}
