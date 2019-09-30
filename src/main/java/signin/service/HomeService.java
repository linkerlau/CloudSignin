package signin.service;

import org.springframework.stereotype.Service;
import signin.model.RecentMsgs;

import java.util.List;

@Service
public interface HomeService {
    List<RecentMsgs> handleRecentMsg();
}
