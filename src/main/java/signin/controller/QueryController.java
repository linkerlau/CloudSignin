package signin.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import signin.model.ChartData;
import signin.model.User;
import signin.service.QueryService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/query", method = RequestMethod.GET)
public class QueryController {

    private QueryService queryService;
    private Gson gson = new Gson();

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @RequestMapping("/home/fixed-percentage")
    @ResponseBody
    public List<ChartData> getHomeFixedPercentage(HttpServletRequest request) {
        List<ChartData> res = new ArrayList<>();
        String date = request.getParameter("date");
        switch (date) {
            case "now" : {
                res = queryService.getNowHomeFixedPercentage();
                break;
            }
            case "yesterday" : {
                res = queryService.getYesterdayHomeFixedPercentage();
                break;
            }
            case "week" : {
                res = queryService.getWeekHomeFixedPercentage();
                break;
            }
        }
        return res;
    }

    @GetMapping("/home/fixed-situation")
    @ResponseBody
    public List<ChartData> getHomeFixedSituation(HttpServletRequest request) {
        List<ChartData> res = null;
        String date = request.getParameter("date");
        switch (date) {
            case "now" : {
                res = queryService.getNowHomeFixedSituation();
                break;
            }
            case "yesterday" : {
                res = queryService.getYesterdayHomeFixedSituation();
                break;
            }
        }
        return res;
    }

    @PostMapping("/home/optional-situation")
    public String getHomeOptionalSituation(Model model, HttpServletRequest request) {
        String firstDate = request.getParameter("firstDate");
        String secondDate = request.getParameter("secondDate");
        List<ChartData> res = queryService.getHomeOptionalSituation(firstDate, secondDate);
        model.addAttribute("res", gson.toJson(res));
        return "result";
    }

    @GetMapping("/user/fixed-percentage")
    @ResponseBody
    public List<ChartData> getUserFixedPercentage(HttpServletRequest request) {
        List<ChartData> res = new ArrayList<>();
        String date = request.getParameter("date");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        switch (date) {
            case "now" : {
                res = queryService.getNowUserFixedPercentage(username);
                break;
            }
            case "yesterday" : {
                res = queryService.getYesterdayUserFixedPercentage(username);
                break;
            }
            case "week" : {
                res = queryService.getWeekUserFixedPercentage(username);
                break;
            }
        }
        return res;
    }

    @GetMapping("/user/fixed-situation")
    @ResponseBody
    public List<ChartData> getUserFixedSituation(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();

        return queryService.getWeekUserFixedSituation(username);
    }

    @PostMapping("/user/optional-situation")
    public String getUserOptionalSituation(Model model, HttpServletRequest request) {
        String firstDate = request.getParameter("firstDate");
        String secondDate = request.getParameter("secondDate");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        List<ChartData> res = queryService.getUserOptionalSituation(username, firstDate, secondDate);
        model.addAttribute("res", gson.toJson(res));
        return "result";
    }



}
