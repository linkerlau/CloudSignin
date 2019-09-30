package signin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import signin.model.RecentMsgs;
import signin.model.User;
import signin.service.UserCenterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserCenterService userCenterService;

    public UserController(UserCenterService userCenterService) {
        this.userCenterService = userCenterService;
    }

    @GetMapping
    public String toUser(
            Model model,
            HttpServletRequest request
    ) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            List<RecentMsgs> recentMessages =  userCenterService.handleRecentMsg(user);
            model.addAttribute("user", user);
            model.addAttribute("recentMsgsList", recentMessages);
            return "user";
        } else {
            model.addAttribute("error", "请登录！");
            return "login";
        }
    }

    @GetMapping("/modify")
    public String toModify(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute(user);
        return "modify";
    }

    @PostMapping("/modify")
    public String modifyUserInfo(
            Model model,
            @ModelAttribute User user,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws InterruptedException {
        User oldUser = (User) request.getSession().getAttribute("user");
        if (user != null) {
            if (userCenterService.modifyUserInfoByUsername(user, oldUser.getUsername(), request, response)) {
                model.addAttribute("msg", "success");
                Thread.sleep(2000);
                return "redirection";
            } else {
                model.addAttribute("msg", "failed");
                return "redirection";
            }
        } else {
            model.addAttribute("msg", "unlogin");
            return "redirection";
        }
    }

    @GetMapping("/signin")
    public String toSignin(Model model, @RequestParam String msg, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (userCenterService.signin(user.getUsername(), msg)) {
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("msg", "签到成功！");
            stringMap.put("qq", userCenterService.getRandomQQNumber());
            stringMap.put("username", user.getUsername());
            model.addAllAttributes(stringMap);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("msg", "签到失败！");
        }
        return "user";
    }

}
