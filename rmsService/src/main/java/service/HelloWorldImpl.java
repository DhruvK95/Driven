
package service;

import javax.jws.WebService;

@WebService(endpointInterface = "service.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

