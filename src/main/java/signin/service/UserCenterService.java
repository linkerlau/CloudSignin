package signin.service;

import org.springframework.stereotype.Service;
import signin.model.RecentMsgs;
import signin.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * describe:
 *
 * @author linkerlau
 * @since 2019/6/27
 */
@Service
public interface UserCenterService {
    boolean modifyUserInfoByUsername(User user, String username, HttpServletRequest request, HttpServletResponse response);
    boolean signin(String username, String msg);
    String getRandomQQNumber();
    List<RecentMsgs> handleRecentMsg(User user);
}
