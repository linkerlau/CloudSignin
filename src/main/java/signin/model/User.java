package signin.model;

import javax.persistence.*;
import java.util.List;

/**
 * describe:
 *
 * @author linkerlau
 * @since 2019/6/27
 */

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "sig", nullable = false)
    private String sig;

    @Column(name = "qq", nullable = false)
    private String qq;

    @Column(name = "avatar", nullable = false)
    private String avatar;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "browver")
    private String browver;

    @Column(name = "cookieinfo")
    private String cookieinfo;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "situation")
    private String situation;

    @OneToMany(mappedBy = "msg", fetch = FetchType.LAZY)
    private List<Message> msgs;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sig='" + sig + '\'' +
                ", qq='" + qq + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex='" + sex + '\'' +
                ", browver='" + browver + '\'' +
                ", cookieinfo='" + cookieinfo + '\'' +
                ", situation='" + situation + '\'' +
                ", msgs=" + msgs +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrowver() {
        return browver;
    }

    public void setBrowver(String browver) {
        this.browver = browver;
    }

    public String getCookieinfo() {
        return cookieinfo;
    }

    public void setCookieinfo(String cookieinfo) {
        this.cookieinfo = cookieinfo;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public List<Message> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }
}
