/**
 * Created by Dhruv on 28/09/2016.
 */

public class Driver {
    private String LastName;
    private String FirstName;
    private String LicenseNumber;
    private String Address;
    private String Email;

    @Override
    public String toString() {
        return "Driver{" +
                "LastName='" + LastName + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LicenseNumber='" + LicenseNumber + '\'' +
                ", Address='" + Address + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }

    public Driver(String lastName, String firstName, String licenseNumber, String address, String email) {
        LastName = lastName;
        FirstName = firstName;
        LicenseNumber = licenseNumber;
        Address = address;
        Email = email;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        LicenseNumber = licenseNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
