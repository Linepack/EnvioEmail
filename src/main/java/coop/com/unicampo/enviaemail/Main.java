/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coop.com.unicampo.enviaemail;

import coop.com.unicampo.enviaemail.Controller.ConfiguracoesController;
import coop.com.unicampo.enviaemail.model.Configuracoes;
import coop.com.unicampo.enviaemail.model.EntityManagerDAO;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;

/**
 *
 * @author DBS
 */
public class Main {

    public static EntityManager em;
    private static String host;
    private static Integer porta;

    public static void main(String[] args) throws UnsupportedEncodingException {
        
        em = EntityManagerDAO.getEntityManager();
        Configuracoes config =  ConfiguracoesController.getConfiguracaoAtiva();
        host = config.getHost();
        porta = config.getPorta();
        
        Map<String, String> toUsers = new HashMap<>();
        toUsers.put("leandro@dbsti.com.br", "Leandro Franciscato");
        toUsers.put("leandro_franciscato@hotmail.com", "Leandro Franciscato Hot");

        final String from = "beneficios@unicampo.coop.br";                

        List<String> pathAnexos = new ArrayList<>();
        pathAnexos.add("C:\\Users\\DBS\\Desktop\\2661.pdf");
        pathAnexos.add("C:\\Users\\DBS\\Desktop\\2661 - Cópia.pdf");

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", porta);
        properties.put("mail.smtp.ssl.enable", false);
        properties.put("mail.smtp.starttls.enable", false);
        properties.put("mail.smtp.auth", false);
        properties.put("mail.smtp.ssl.trust", "*");

        Session session = null;
        Authenticator authenticator = null;
        if ((boolean) properties.get("mail.smtp.auth") == true) {
            authenticator = new Authenticator() {
                private PasswordAuthentication pa = new PasswordAuthentication(from, "inativos25");

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
            session = Session.getDefaultInstance(properties, authenticator);
        } else {
            session = Session.getDefaultInstance(properties);
        }

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.setSubject("This is the Subject Line!");

            //To
            Boolean first = true;
            for (Map.Entry<String, String> map : toUsers.entrySet()) {
                if (first) {
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(map.getKey(), map.getValue())
                    );

                    first = false;
                } else {
                    message.addRecipient(Message.RecipientType.CC,
                            new InternetAddress(map.getKey(), map.getValue())
                    );
                }
            }

            //Anexo
            Multipart mp = new MimeMultipart();
            for (int index = 0; index < pathAnexos.size(); index++) {
                MimeBodyPart mbp = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(pathAnexos.get(index));
                mbp.setDataHandler(new DataHandler(fds));
                mbp.setFileName(fds.getName());

                mp.addBodyPart(mbp, index);
            }

            // Corpo
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent("<h1>This is actual message</h1>", "text/html");
            mp.addBodyPart(bodyPart);
            message.setContent(mp);

            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}
