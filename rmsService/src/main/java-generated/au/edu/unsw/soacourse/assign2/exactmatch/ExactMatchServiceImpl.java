package au.edu.unsw.soacourse.assign2.exactmatch;

import javax.jws.WebService;


@WebService(endpointInterface = "au.edu.unsw.soacourse.assign2.exactmatch.ExactMatchService")
public class ExactMatchServiceImpl implements ExactMatchService{
	ObjectFactory factory = new ObjectFactory();

	@Override
	public ValidateExactMatchResponse validateExactMatch(
			ValidateExactMatchRequest parameters) {
		
		ValidateExactMatchResponse res  = factory.createValidateExactMatchResponse();		
		
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
