package au.edu.unsw.soacourse.assign2.driverslicense;

import javax.jws.WebService;


@WebService(endpointInterface = "au.edu.unsw.soacourse.assign2.driverslicense.DriversLicenseService")
public class DriversLicenseServiceImpl implements DriversLicenseService{
	ObjectFactory factory = new ObjectFactory();
	@Override
	public ValidateDriversLicenseResponse validateDriversLicense(
			ValidateDriversLicenseRequest parameters) {

		ValidateDriversLicenseResponse res  = factory.createValidateDriversLicenseResponse();		
		
		System.out.println("DriversLicenseServiceImpl ==" +parameters.address);
		if(parameters.address.equals("home")){
			res.message="\n Exact Address Found \n";
			res.messageStatus="Output Closed"; 
		}else{
			res.message=parameters.address;
			res.messageStatus="no"; 
			
		}
		return res;
	}

}
