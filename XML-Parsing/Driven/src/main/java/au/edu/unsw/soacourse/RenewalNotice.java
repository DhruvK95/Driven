package au.edu.unsw.soacourse;

/**
 * Created by Dhruv on 2/10/2016. Driven
 */
public class RenewalNotice {
    public enum Status {
        CREATED, CANCELLED, REQUESTED, UNDER_REVIEW, ACCEPTED, ARCHIVED
    }
    Integer nid;
    Integer rid;
    Status status;

    public RenewalNotice(Integer nid, Integer rid, Status status) {
        this.nid = nid;
        this.rid = rid;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RenewalNotice{" +
                "nid=" + nid +
                ", rid=" + rid +
                ", status=" + status +
                '}';
    }
}
