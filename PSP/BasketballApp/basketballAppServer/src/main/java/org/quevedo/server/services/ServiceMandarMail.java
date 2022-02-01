package org.quevedo.server.services;

import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.quevedo.server.config.Configuration;
import org.quevedo.server.ee.utils.EEConst;
import org.quevedo.server.services.utils.ServicesConsts;

import java.util.Properties;

@Log4j2
public class ServiceMandarMail {

    Properties mailServerProperties;
    Configuration generalConfig;
    Session getMailSession;
    MimeMessage generateMailMessage;

    @Inject
    public ServiceMandarMail(Configuration generalConfig) {
        this.generalConfig = generalConfig;
    }

    private void generateAndSendEmail(String to, String msg, String subject) throws MessagingException {
        // Step1

        mailServerProperties = System.getProperties();
        mailServerProperties.put(ServicesConsts.MAIL_SMTP_PORT, Integer.parseInt(generalConfig.getMailSmtpPort()));
        mailServerProperties.put(ServicesConsts.MAIL_SMTP_AUTH, generalConfig.getMailSmtpAuth());
        mailServerProperties.put(ServicesConsts.MAIL_SMTP_SSL_TRUST, generalConfig.getMailHost());
        mailServerProperties.put(ServicesConsts.MAIL_SMTP_STARTTLS_ENABLE, generalConfig.getMailSmtpStarttlsEnable());

        // Step2

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        generateMailMessage.setContent(msg, generalConfig.getMailContentType());


        // Step3

        Transport transport = getMailSession.getTransport(generalConfig.getMailProtocol());

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect(generalConfig.getMailHost(),
                generalConfig.getMailUser(),
                generalConfig.getMailPass());
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    public void sendConfirmationMail(String correo, String codigoActivacion) {
        try {
            String mensaje = ServicesConsts.CONFIRMACION_MENSAJE + EEConst.PATH_ACTIVATE + ServicesConsts.QUESTION_MARK + EEConst.COD_ACTIVACION + ServicesConsts.EQUALS + codigoActivacion + ServicesConsts.CONFIRMACION_MENSAJE_2;
            generateAndSendEmail(correo, mensaje, ServicesConsts.MAIL_CONFIRMATION_SUBJECT);
        } catch (MessagingException messagingException) {
            log.error(messagingException.getMessage(), messagingException);
        }
    }

    public void sendChangePasswordMail(String changePasswordEmail, String codCambio) {
        try {
            String mensaje =
                    ServicesConsts.CORREO_PARTE1 +
                            EEConst.CHANGE_PASS_JSP + ServicesConsts.QUESTION_MARK + EEConst.COD_PASS + ServicesConsts.EQUALS + codCambio + ServicesConsts.CORREO_PARTE2;
            generateAndSendEmail(changePasswordEmail, mensaje, ServicesConsts.SUBJECT_PASSWORD_CHANGE);
        } catch (MessagingException messagingException) {
            log.error(messagingException.getMessage(), messagingException);
        }
    }
}