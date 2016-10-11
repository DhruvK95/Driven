package au.edu.unsw.soacourse.driven;

import java.util.Date;

/**
 * Created by Dhruv on 2/10/2016. Driven
 */
public class Payment {
    Integer pid;
    Integer nid;
    Integer amount;
    Integer credit_card_number;
    String credit_card_name;
    Integer credit_card_ccv;
    Date paid_date;

    public Payment(Integer pid, Integer nid, Integer amount, Integer credit_card_number, String credit_card_name, Integer credit_card_ccv, Date paid_date) {
        this.pid = pid;
        this.nid = nid;
        this.amount = amount;
        this.credit_card_number = credit_card_number;
        this.credit_card_name = credit_card_name;
        this.credit_card_ccv = credit_card_ccv;
        this.paid_date = paid_date;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "pid=" + pid +
                ", nid=" + nid +
                ", amount=" + amount +
                ", credit_card_number=" + credit_card_number +
                ", credit_card_name='" + credit_card_name + '\'' +
                ", credit_card_ccv=" + credit_card_ccv +
                ", paid_date=" + paid_date +
                '}';
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCredit_card_number() {
        return credit_card_number;
    }

    public void setCredit_card_number(Integer credit_card_number) {
        this.credit_card_number = credit_card_number;
    }

    public String getCredit_card_name() {
        return credit_card_name;
    }

    public void setCredit_card_name(String credit_card_name) {
        this.credit_card_name = credit_card_name;
    }

    public Integer getCredit_card_ccv() {
        return credit_card_ccv;
    }

    public void setCredit_card_ccv(Integer credit_card_ccv) {
        this.credit_card_ccv = credit_card_ccv;
    }

    public Date getPaid_date() {
        return paid_date;
    }

    public void setPaid_date(Date paid_date) {
        this.paid_date = paid_date;
    }
}
