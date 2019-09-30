package signin.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import signin.model.Message;
import signin.model.User;

import java.util.Date;

@Repository
@Transactional
public interface MessageDaoFace extends PagingAndSortingRepository<Message, Long> {

    @Modifying
    @Query(value = "insert into message(date, msg, uid) values (?1,?2,?3)", nativeQuery = true)
    int saveMsgByUid(Date date, String msg, Long uid);

    Page<Message> findAll(Pageable pageable);

    Page<Message> findAllByUser(User user, Pageable pageable);
}
