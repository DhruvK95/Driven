package au.edu.unsw.soacourse.assign2.exactmatch;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.0.4
 * 2016-10-10T13:35:46.525+11:00
 * Generated source version: 3.0.4
 * 
 */
@WebService(targetNamespace = "http://exactmatch.assign2.soacourse.unsw.edu.au", name = "ExactMatchService")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ExactMatchService {

    @WebResult(name = "validateExactMatchResponse", targetNamespace = "http://exactmatch.assign2.soacourse.unsw.edu.au", partName = "parameters")
    @WebMethod(action = "http://exactmatch.assign2.soacourse.unsw.edu.au/validateExactMatch")
    public ValidateExactMatchResponse validateExactMatch(
        @WebParam(partName = "parameters", name = "validateExactMatchRequest", targetNamespace = "http://exactmatch.assign2.soacourse.unsw.edu.au")
        ValidateExactMatchRequest parameters
    ) throws NullEntryFaultMsg;
}
