package kr.ac.hansung.controller;

import kr.ac.hansung.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest : 전체 Application Context 로드
// @MockitoBean    : 실제 UserService 대신 Mock 객체 사용
// @WithMockUser   : 로그인한 사용자 상태를 테스트에서 직접 만듦
@SpringBootTest
@DisplayName("UserController 테스트")
class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @MockitoBean
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(wac)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @Test
    @WithMockUser(username = "user@hansung.ac.kr", roles = "USER")
    @DisplayName("인증된 사용자 - 비밀번호 변경 폼 조회 성공")
    void passwordForm_authenticated_returns200() throws Exception {
        mockMvc.perform(get("/user/password"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/password"))
            .andExpect(model().attributeExists("passwordChangeDto"));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("비인증 사용자 - 비밀번호 변경 폼 접근 시 로그인 페이지로 이동")
    void passwordForm_anonymous_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/user/password"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = "user@hansung.ac.kr", roles = "USER")
    @DisplayName("비밀번호 변경 성공 - 홈으로 리다이렉트")
    void changePassword_success_redirectsToHome() throws Exception {
        mockMvc.perform(post("/user/password")
                .with(csrf())
                .param("currentPassword", "oldpass123")
                .param("newPassword", "newpass123")
                .param("confirmPassword", "newpass123"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/home"))
            .andExpect(flash().attribute("successMessage", "비밀번호가 변경되었습니다."));

        then(userService).should().changePassword(
            eq("user@hansung.ac.kr"), eq("oldpass123"), eq("newpass123")
        );
    }

    @Test
    @WithMockUser(username = "user@hansung.ac.kr", roles = "USER")
    @DisplayName("새 비밀번호 불일치 - 변경 폼으로 복귀")
    void changePassword_mismatch_returnsForm() throws Exception {
        mockMvc.perform(post("/user/password")
                .with(csrf())
                .param("currentPassword", "oldpass123")
                .param("newPassword", "newpass123")
                .param("confirmPassword", "otherpass123"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/password"))
            .andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser(username = "user@hansung.ac.kr", roles = "USER")
    @DisplayName("현재 비밀번호 오류 - 변경 폼으로 복귀")
    void changePassword_wrongCurrentPassword_returnsForm() throws Exception {
        willThrow(new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다"))
            .given(userService)
            .changePassword(eq("user@hansung.ac.kr"), eq("wrongpass123"), eq("newpass123"));

        mockMvc.perform(post("/user/password")
                .with(csrf())
                .param("currentPassword", "wrongpass123")
                .param("newPassword", "newpass123")
                .param("confirmPassword", "newpass123"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/password"))
            .andExpect(model().hasErrors());
    }
}
