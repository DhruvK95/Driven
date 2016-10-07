package au.edu.unsw.soacourse.assign2.addressservice;

import javax.jws.WebService;

@WebService(endpointInterface = "au.edu.unsw.soacourse.assign2.addressservice.AddressService")
public class AddressServiceImpl implements AddressService {
	ObjectFactory factory = new ObjectFactory();

	@Override
	public ValidateAddressResponse validateAddress(
			ValidateAddressRequest parameters) {
		ValidateAddressResponse res  = factory.createValidateAddressResponse();
		System.out.println("ValidateAddressResponse--> " + parameters.address + parameters.fullName);
		res.message="LOL";
		res.isValid=false;
		return res;
	}

}


