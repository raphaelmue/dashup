package de.dashup.test;

import de.dashup.application.DashupApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes ={DashupApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class SpringBootBase {
    @LocalServerPort
    protected int port;

    public int getPort() {
        return port;
    }
}
