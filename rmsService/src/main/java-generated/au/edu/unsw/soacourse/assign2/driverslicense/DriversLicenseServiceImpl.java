package au.edu.unsw.soacourse.assign2.driverslicense;

import javax.jws.WebService;


@WebService(endpointInterface = "au.edu.unsw.soacourse.assign2.driverslicense.DriversLicenseService")
public class DriversLicenseServiceImpl implements DriversLicenseService{
	ObjectFactory factory = new ObjectFactory();
	@Override
	public ValidateDriversLicenseResponse validateDriversLicense(
			ValidateDriversLicenseRequest parameters) {

		ValidateDriversLicenseResponse res  = factory.createValidateDriversLicenseResponse();
		res.isValid=true;
		res.message="mekjssage";

		System.out.println("DriversLicenseServiceImpl ==" +parameters.address+ 
				" : " + parameters.fullName + " : " + parameters.licenseNumber);
		return res;
	}

}
