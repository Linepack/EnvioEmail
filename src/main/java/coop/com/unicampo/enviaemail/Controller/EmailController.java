/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coop.com.unicampo.enviaemail.Controller;

import coop.com.unicampo.enviaemail.Main;
import coop.com.unicampo.enviaemail.model.Email;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

/**
 *
 * @author DBS
 */
public class EmailController {   

    public static Email getEmail(Integer sequenciaEmail) {
        Email email = new Email();
        email = Main.em.find(Email.class, sequenciaEmail);

        return email;
    }

    public static void updateEmail(Email email) {

        email.setIsEnviado(1);

        String hostName;
        String ip;
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            hostName = address.getHostName();
            ip = address.getHostAddress();
        } catch (UnknownHostException ex) {
            hostName = "UNKNOWN";
            ip = "UNKNOWN";
        }

        email.setPc(hostName);
        email.setIp(ip);
        Main.em.getTransaction().commit();
    }

}
