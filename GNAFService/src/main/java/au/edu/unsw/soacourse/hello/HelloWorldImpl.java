
package au.edu.unsw.soacourse.hello;

import javax.jws.WebService;

@WebService(endpointInterface = "au.edu.unsw.soacourse.hello.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

