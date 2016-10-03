package au.edu.unsw.soacourse.driven;

/**
 * Created by Dhruv on 2/10/2016. Driven
 */
public class RenewalNotice {
    Integer nid;
    Integer rid;
    String status;

    public RenewalNotice(Integer nid, Integer rid, String status) {
        this.nid = nid;
        this.rid = rid;
        this.status = status;
    }

    @Override
    public String toString() {
        return "RenewalNotice{" +
                "nid=" + nid +
                ", rid=" + rid +
                ", status='" + status + '\'' +
                '}';
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
