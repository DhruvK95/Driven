import java.util.Date;

/**
 * Created by Dhruv on 28/09/2016. XML-Parsing
 */
public class Registration {
    private Integer rID;
    private String RegistrationNumber;
    private Date validTill;
    private Driver driver;

    @Override
    public String toString() {
        return "Registration{" +
                "rID=" + rID +
                ", RegistrationNumber='" + RegistrationNumber + '\'' +
                ", validTill=" + validTill +
                ", driver=" + driver +
                '}';
    }

    public Registration(Integer rID, String registrationNumber, Date validTill, Driver driver) {
        this.rID = rID;
        RegistrationNumber = registrationNumber;
        this.validTill = validTill;
        this.driver = driver;
    }

    public Integer getrID() {
        return rID;
    }

    public void setrID(Integer rID) {
        this.rID = rID;
    }

    public String getRegistrationNumber() {
        return RegistrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
