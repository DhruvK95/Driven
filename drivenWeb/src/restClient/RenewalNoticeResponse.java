package restClient;

/**
 * Created by Dhruv on 4/10/2016. DrivenRest
 */
public class RenewalNoticeResponse {
    RenewalNotice renewalNotice;
    String Link;

    public RenewalNoticeResponse(RenewalNotice renewalNotice, String link) {
        this.renewalNotice = renewalNotice;
        Link = link;
    }

    public RenewalNotice getRenewalNotice() {
        return renewalNotice;
    }

    public void setRenewalNotice(RenewalNotice renewalNotice) {
        this.renewalNotice = renewalNotice;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    @Override
    public String toString() {
        return "RenewalNoticeResponse{" +
                "renewalNotice=" + renewalNotice +
                ", Link='" + Link + '\'' +
                '}';
    }
}
