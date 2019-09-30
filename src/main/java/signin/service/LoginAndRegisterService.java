package signin.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import signin.model.User;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * describe:
 *
 * @author linkerlau
 * @since 2019/6/27
 */
@Service
public interface LoginAndRegisterService {
    boolean register(MultipartFile avatar, User user, HttpServletRequest request, HttpServletResponse response);
    String login(Model model, User user, HttpServletRequest request);
}
