package signin.service.imp;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import signin.exceptions.FileCreatedException;
import signin.exceptions.InsertDataInDBException;
import signin.service.LoginAndRegisterService;
import signin.utils.EncodeAndDecodeUrl;
import signin.dao.UserDaoFace;
import signin.model.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * describe:
 *
 * @author linkerlau
 * @since 2019/6/27
 */
@Service
public class LoginAndRegisterImp implements LoginAndRegisterService {

    private final UserDaoFace userDaoFace;

    public LoginAndRegisterImp(UserDaoFace userDaoFace) {
        this.userDaoFace = userDaoFace;
    }

    @Override
    public boolean register(MultipartFile avatar, User user, HttpServletRequest request, HttpServletResponse response) {
        User dbUser = userDaoFace.findUserByUsername(user.getUsername());
        if (dbUser == null) { //the user is not exists
            Cookie cookie = new Cookie("olduser", EncodeAndDecodeUrl.encode(user.getUsername(), "utf-8"));
            cookie.setMaxAge(60*60*24*30*5);//生命周期 五年 -.-
            cookie.setPath("/");
            user.setCookieinfo(user.getUsername());

            //保存用户
            int i = userDaoFace.saveUser(
                    user.getUsername(), user.getPassword(),
                    user.getQq(), user.getSex(), user.getSig(),
                    request.getHeader("User-Agent"),
                    user.getCookieinfo(),
                    avatar.getOriginalFilename()
            );
            if (i == 0) {
                throw new InsertDataInDBException();
            }

            //保存头像
            String path = "C:\\Users\\16961\\IdeaProjects\\signin\\src\\main\\webapp\\resources\\static\\img\\avatar\\";
            File file = new File(path);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    throw new FileCreatedException();
                }
            }
            file = new File(path + avatar.getOriginalFilename());
            try {
                avatar.transferTo(file);
            } catch (IOException e) {
                System.out.println("文件保存失败！");
                e.printStackTrace();
            }

            response.addCookie(cookie);
            return true;
        } else { //the user is exists
            return false;
        }
    }

    @Override
    public String login(Model model, User user, HttpServletRequest request) {
        User dbUser = userDaoFace.findUserByUsername(user.getUsername());
        if (dbUser != null) {
            if (dbUser.getPassword().equals(user.getPassword()) ) { //the password is correct


                //verification
                Cookie[] cookies = request.getCookies();
                boolean flag = false;
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("olduser")) {
                            if (EncodeAndDecodeUrl.decode(cookie.getValue(), "utf-8").equals(user.getUsername())) {
                                flag = true;
                            }
                        }
                    }
                }
                user.setBrowver(request.getHeader("User-Agent"));
                if (flag && user.getBrowver().equals(dbUser.getBrowver())) { //登录成功
                    request.getSession().setAttribute("user", dbUser);
                    return "redirect:/home";
                } else {
                    model.addAttribute("error", "未在正确地点登录！");
                    return "login";
                }


            } else {
                model.addAttribute("error", "密码错误！");
                return "login";
            }
        } else {
            model.addAttribute("error", "用户不存在！");
            return "login";
        }

    }

    @ExceptionHandler(InsertDataInDBException.class)
    public void insertFailed() { }

    @ExceptionHandler(FileCreatedException.class)
    public void fileCreatedFailed() { }
}
