package signin.service.imp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import signin.dao.MessageDaoFace;
import signin.dao.UserDaoFace;
import signin.model.Message;
import signin.model.RecentMsgs;
import signin.model.Signin;
import signin.model.User;
import signin.service.UserCenterService;
import signin.utils.EncodeAndDecodeUrl;
import signin.utils.MyDateUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserCenterImp implements UserCenterService {
    private UserDaoFace userDaoFace;
    private MessageDaoFace messageDaoFace;
    private Gson gson;

    public UserCenterImp(UserDaoFace userDaoFace, MessageDaoFace messageDaoFace) {
        this.userDaoFace = userDaoFace;
        this.messageDaoFace = messageDaoFace;
        this.gson = new Gson();
    }

    @Override
    public boolean modifyUserInfoByUsername(User user, String oldName, HttpServletRequest request, HttpServletResponse response) {
        user.setCookieinfo(user.getUsername());
        int res = userDaoFace.updateUserByUsername(
                user.getUsername(),
                user.getPassword(),
                user.getQq(),
                user.getSex(),
                user.getSig(),
                user.getCookieinfo(),
                oldName
        );
        if (res != 0) {
            request.getSession().removeAttribute("user");
            request.getSession().setAttribute("user", user);

            Cookie cookie = new Cookie("olduser", EncodeAndDecodeUrl.encode(user.getUsername(), "utf-8"));
            cookie.setMaxAge(60*60*24*30*5);
            cookie.setPath("/");
            response.addCookie(cookie);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean signin(String username, String msg) {


        User user = userDaoFace.findUserByUsername(username);
        List<Signin>  signins = new ArrayList<>();

        if (user.getSituation() != null) {//非新用户
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(user.getSituation()).getAsJsonArray();
            for (JsonElement s : jsonArray) {
                Signin signin = gson.fromJson(s, Signin.class);
                signins.add(signin);
            }
        }

        Signin signin;
        if (signins.size() != 0) { //排除新用户
            //当天对象存在 上午已签
            if (signins.get(signins.size() - 1) != null && MyDateUtils.getNowDayOfYear(signins.get(signins.size() - 1).getDate()) == MyDateUtils.getNowDayOfYear(new Date())) {
                signin = signins.get(signins.size() - 1);
                List<String> temp = signin.getSituation();
                temp.remove(temp.size() - 1);
                temp.add("1");
                signin.setSituation(temp);
            } else { //对象不存在 上午或上午未签
                List<String> temp = new ArrayList<>();
                signin = new Signin();
                signin.setDate(new Date());
                if (MyDateUtils.isMorning()) {
                    temp.add("1");
                } else {    //早上没有签到
                    temp.add("0");
                    temp.add("1");
                }
                signin.setSituation(temp);
                signins.add(signin);
            }
        } else { //新用户
            signin = new Signin();
            List<String> temp = new ArrayList<>();
            signin.setDate(new Date());
            if (MyDateUtils.isMorning()) {
                temp.add("1");
            } else {    //早上没有签到
                temp.add("0");
                temp.add("1");
            }
            signin.setSituation(temp);
            signins.add(signin);
        }

        String json = gson.toJson(signins);
        Long uid = userDaoFace.findUserByUsername(username).getId();
        int status = userDaoFace.updateSituationByUsername(json, username);
        int status1 = messageDaoFace.saveMsgByUid(new Date(), msg, uid);
        return status == 1 && status1 == 1;
    }

    @Override
    public String getRandomQQNumber() {
        int uid = new Random(47).nextInt(userDaoFace.countAll());
        return (String) userDaoFace.findQQByUid((int) Math.ceil(uid));
    }

    @Override
    public List<RecentMsgs> handleRecentMsg(User user) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(0, 5, sort);

        Page<Message> messages = messageDaoFace.findAllByUser(user, pageable);
        List<RecentMsgs> recentMsgsList = new ArrayList<>();
        for (Message message : messages) {
            RecentMsgs recentMsgs = new RecentMsgs();
            recentMsgs.setUsername(user.getUsername());
            recentMsgs.setDate(message.getDate());
            recentMsgs.setMessage(message.getMsg());
            recentMsgsList.add(recentMsgs);
        }
        return recentMsgsList;
    }
}