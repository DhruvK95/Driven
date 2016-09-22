package au.edu.unsw.soacourse.hello;

import javax.jws.WebService;

@WebService
public interface HelloWorld {
    String sayHi(String text);
}

