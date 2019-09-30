package signin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import signin.model.RecentMsgs;
import signin.service.HomeService;

import java.util.List;

@Controller
public class HomeController {

    private HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/home")
    public String getRecentSigninMsg(Model model) {
        List<RecentMsgs> recentMessages =  homeService.handleRecentMsg();
        model.addAttribute("recentMsgsList", recentMessages);
        return "home";
    }

}
