package signin.service.imp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import signin.dao.MessageDaoFace;
import signin.dao.UserDaoFace;
import signin.model.Message;
import signin.model.RecentMsgs;
import signin.model.User;
import signin.service.HomeService;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeImp implements HomeService {

    private MessageDaoFace messageDaoFace;
    private UserDaoFace userDaoFace;

    public HomeImp(MessageDaoFace messageDaoFace, UserDaoFace userDaoFace) {
        this.messageDaoFace = messageDaoFace;
        this.userDaoFace = userDaoFace;
    }

    @Override
    public List<RecentMsgs> handleRecentMsg() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(0, 10, sort);

        Page<Message> messages = messageDaoFace.findAll(pageable);
        List<RecentMsgs> recentMsgsList = new ArrayList<>();
        for (Message message : messages.getContent()) {
            RecentMsgs recentMsgs = new RecentMsgs();
            recentMsgs.setUsername(((User)userDaoFace.findUserById(message.getUser().getId())).getUsername());
            recentMsgs.setDate(message.getDate());
            recentMsgs.setMessage(message.getMsg());
            recentMsgsList.add(recentMsgs);
        }

        return recentMsgsList;
    }
}
