package signin.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import signin.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import signin.service.LoginAndRegisterService;

import javax.servlet.http.HttpServletRequest;

/**
 * describe:
 *
 * @author linkerlau
 * @since 2019/6/27
 */
@Controller
public class LoginValidateController {
    private final LoginAndRegisterService loginAndRegisterService;

    public LoginValidateController(LoginAndRegisterService loginAndRegisterService) {
        this.loginAndRegisterService = loginAndRegisterService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //登录验证
    @PostMapping("/login")
    public String loginValidate(
            Model model,
            @ModelAttribute User user, HttpServletRequest request
    ) {
        return loginAndRegisterService.login(model, user, request);
    }

}
