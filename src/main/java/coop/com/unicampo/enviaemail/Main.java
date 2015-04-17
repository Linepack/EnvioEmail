/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coop.com.unicampo.enviaemail;

import coop.com.unicampo.enviaemail.Controller.ConfiguracoesController;
import coop.com.unicampo.enviaemail.Controller.EmailController;
import coop.com.unicampo.enviaemail.Converter.EmailConverter;
import coop.com.unicampo.enviaemail.model.Configuracoes;
import coop.com.unicampo.enviaemail.model.Email;
import coop.com.unicampo.enviaemail.model.EntityManagerDAO;
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
import org.apache.log4j.Logger;

/**
 *
 * @author DBS
 */
public class Main {

    private static Logger log = Logger.getLogger(Main.class);

    public static EntityManager em;
    private static String host;
    private static Integer porta;
    private static Map<String, String> to;
    private static Map<String, String> cc;
    private static Map<String, String> cco;
    private static List<String> anexo;
    private static String from;
    private static String password;
    private static String subject;
    private static String body;
    private static Integer configuracaoID;

    public static void main(String[] args) {

        try {
            configuracaoID = Integer.parseInt(args[1]);
        } catch (Exception e) {
            configuracaoID = 0;
        }

        try {
            log.info("Buscando Configurações.");
            em = EntityManagerDAO.getEntityManager();

            Configuracoes config;

            if (configuracaoID == 0) {
                config = ConfiguracoesController.getConfiguracaoAtiva();
            } else {
                config = ConfiguracoesController.getConfiguracaoPerID(configuracaoID);
            }

            host = config.getHost();
            porta = config.getPorta();

            log.info("Buscando Email.");
            Email email = EmailController.getEmail(Integer.parseInt(args[0]));
            to = EmailConverter.stringToMap(email.getTo(), ";");
            if (email.getCc() != null) {
                cc = EmailConverter.stringToMap(email.getCc(), ";");
            }
            if (email.getCco() != null) {
                cco = EmailConverter.stringToMap(email.getCco(), ";");
            }
            if (email.getAnexo() != null) {
                anexo = EmailConverter.stringToList(email.getAnexo(), ";");
            }
            from = email.getFrom();
            password = email.getPassword();
            subject = email.getSubject();
            body = email.getBody();

            log.info("Configurando SMTP");
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", porta);
            properties.put("mail.smtp.ssl.enable", ("SSL".equals(config.getDescricaoDaConexao())));
            properties.put("mail.smtp.starttls.enable", ("TLS".equals(config.getDescricaoDaConexao())));
            properties.put("mail.smtp.auth", (!"NENHUM".equals(config.getDescricaoDaConexao())));

            properties.put("mail.smtp.ssl.trust", "*");

            Session session = null;
            Authenticator authenticator = null;
            if ((boolean) properties.get("mail.smtp.auth") == true) {
                authenticator = new Authenticator() {
                    private PasswordAuthentication pa = new PasswordAuthentication(from, password);

                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return pa;
                    }
                };
                session = Session.getDefaultInstance(properties, authenticator);
            } else {
                session = Session.getDefaultInstance(properties);
            }

            log.info("Montando Mensagem: " + email.getId());
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);

            //To            
            for (Map.Entry<String, String> map : to.entrySet()) {
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(map.getKey(), map.getValue())
                );
            }

            //CC
            if (cc != null) {
                for (Map.Entry<String, String> map : cc.entrySet()) {
                    message.addRecipient(Message.RecipientType.CC,
                            new InternetAddress(map.getKey(), map.getValue())
                    );
                }
            }

            //CCO                        
            if (cco != null) {
                for (Map.Entry<String, String> map : cco.entrySet()) {
                    message.addRecipient(Message.RecipientType.BCC,
                            new InternetAddress(map.getKey(), map.getValue())
                    );
                }
            }

            //Anexo
            Multipart mp = new MimeMultipart();
            if (anexo != null) {
                for (int index = 0; index < anexo.size(); index++) {
                    MimeBodyPart mbp = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(anexo.get(index));
                    mbp.setDataHandler(new DataHandler(fds));
                    mbp.setFileName(fds.getName());

                    mp.addBodyPart(mbp, index);
                }
            }

            // Corpo
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(body, "text/html");
            mp.addBodyPart(bodyPart);
            message.setContent(mp);

            log.info("Enviando: " + email.getId());
            Transport.send(message);

            EmailController.updateEmail(email);

            log.info("Mensagem Enviada: " + email.getId());

        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            log.error("HELP: call instructions:"
                    + "java -jar [ProjectHome]/EnviaEmail.jar [Email.id[Integer] Configuracoes.id[Integer]]");
            ex.printStackTrace();
        } catch (MessagingException mex) {
            log.error(mex);
            mex.printStackTrace();
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
        }

    }
}
