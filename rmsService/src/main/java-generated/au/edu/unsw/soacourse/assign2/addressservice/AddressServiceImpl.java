package au.edu.unsw.soacourse.assign2.addressservice;


public class AddressServiceImpl implements AddressService {
	ObjectFactory factory = new ObjectFactory();

	@Override
	public ValidateAddressResponse validateAddress(
			ValidateAddressRequest parameters) {
		ValidateAddressResponse res  = factory.createValidateAddressResponse();
		System.out.print("LEEEEEEOONN" + parameters.address);
		if(parameters.address.equals("leon")){
			res.setIsValid(true);
		}else{
			res.setIsValid(false);

		}
		return res;
	}

}


