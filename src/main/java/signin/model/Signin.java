package signin.model;

import java.util.Date;
import java.util.List;

public class Signin {

    private Date date;
    private List<String> situation;

    public Signin(Date date, List<String> situation) {
        this.date = date;
        this.situation = situation;
    }

    public Signin() {}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getSituation() {
        return situation;
    }

    public void setSituation(List<String> situation) {
        this.situation = situation;
    }
}
