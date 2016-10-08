package au.edu.unsw.soacourse.assign2.similarmatchservice;
import javax.jws.WebService;

@WebService(endpointInterface = "au.edu.unsw.soacourse.assign2.similarmatchservice.SimilarMatchService")
public class SimilarMatchServiceImpl implements SimilarMatchService {
	ObjectFactory factory = new ObjectFactory();

	@Override
	public ValidateSimilarMatchResponse validateSimilarMatch(
			ValidateSimilarMatchRequest parameters) {
		
		ValidateSimilarMatchResponse res  = factory.createValidateSimilarMatchResponse();
		System.out.println("ValidateAddressResponse--> " + parameters.address);
		res.message="\n Exact Address Not Found "
				+ "\n Similair Address Found";
		res.messageStatus="Output Closed";
		
		
		
		
		return res;
	}

}


