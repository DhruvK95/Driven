package au.edu.unsw.soacourse.driven;

/**
 * Created by Dhruv on 17/10/2016. DrivenRest
 */
public class EmailCode {
    Integer id;
    String code;

    public EmailCode(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
