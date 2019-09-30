package signin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import signin.model.User;
import signin.service.LoginAndRegisterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class RegisterController {

    private final LoginAndRegisterService loginAndRegisterService;

    public RegisterController(LoginAndRegisterService loginAndRegisterService) {
        this.loginAndRegisterService = loginAndRegisterService;
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(
            Model model,
            @RequestPart("avatarImg") MultipartFile avatar,
            @ModelAttribute User user, HttpServletRequest request, HttpServletResponse response
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        if (loginAndRegisterService.register(avatar, user, request, response)) {
            model.addAttribute("msg", "用户创建成功！");
            return "login";
        } else {
            model.addAttribute("msg", "用户名已存在！");
            return "register";
        }
    }

}
