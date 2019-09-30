package signin.dao;

import org.springframework.stereotype.Repository;
import signin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * describe: Spring data jpa
 *    get read find count
 * @author linkerlau
 * @since 2019/6/27
 */
@Repository
@Transactional
public interface UserDaoFace extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    @Override
    List<User> findAll();

    @Modifying
    @Query(value = "insert into user(username, password, qq, sex, sig, browver, cookieinfo, avatar) values (?1,?2,?3,?4,?5,?6,?7,?8)", nativeQuery = true)
    int saveUser(String name, String password, String qq, String sex, String sig, String browver, String cookieinfo, String avatar);

    @Modifying
    @Query(value = "update user set username=?1,password=?2,qq=?3,sex=?4,sig=?5,cookieinfo=?6 where username=?7", nativeQuery = true)
    int updateUserByUsername(String newUsername,String password, String qq, String sex, String sig, String cookieInfo, String username);

    @Modifying
    @Query(value = "update user set situation=?1 where username=?2", nativeQuery = true)
    int updateSituationByUsername(String situation, String username);

    @Query(value = "select count(username) from user", nativeQuery = true)
    int countAll();

    @Query(value = "select qq from user where uid=?1", nativeQuery = true)
    Object findQQByUid(int uid);

    Object findUserById(Long id);



}