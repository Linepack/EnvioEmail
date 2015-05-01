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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author DBS
 */
public class EmailController {

    public static Email getEmailPerID(Integer sequenciaEmail) {
                
        Email email = new Email();
        email = Main.em.find(Email.class, sequenciaEmail);

        return email;
    }

    public static List<Email> getEmailsNotSend() {

        List<Email> emails = new ArrayList<>();
        Query query = Main.em.createQuery("select e"
                + " from GEEMAIL e"
                + " where e.isEnviado = 0"
                + " and e.numeroDeTentativas < 3"
                + " and e.mensagemDeErro is null");
        for (Object emailObject : query.getResultList()){
            emails.add((Email) emailObject);
        }

        return emails;
    }

    public static void updateEmail(Email email, Integer enviado, String mensagemErro) {
                        
        email.setIsEnviado(enviado);

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
        if (mensagemErro != null){
            email.setMensagemDeErro(mensagemErro);
        }
        email.setNumeroDeTentativas(email.getNumeroDeTentativas()+1);    
        
        if (enviado == 1){
           email.setPassword("0");
        }else{
            if (email.getNumeroDeTentativas() > 3){
                email.setPassword("0");
            }else{
                if (email.getMensagemDeErro() != null){
                    email.setPassword("0");
                }
            }
                
        }
        
        if (!Main.em.getTransaction().isActive()){
            Main.em.getTransaction().begin();
        }
        Main.em.getTransaction().commit();        
        
    }
}
