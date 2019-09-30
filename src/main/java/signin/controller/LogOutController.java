package signin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import signin.model.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogOutController {

    @GetMapping("/logout")
    public String loginout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/login";
    }

}
