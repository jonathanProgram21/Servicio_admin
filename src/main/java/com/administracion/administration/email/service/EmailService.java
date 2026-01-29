package com.administracion.administration.email.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(final JavaMailSender javaMailSender ){
        this.emailSender = javaMailSender;
    }

    public void sendEmail(String para, String asunto, String contenido){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(para);
        mensaje.setSubject(asunto);
        mensaje.setText(contenido);
        mensaje.setFrom("teatroccran@gmail.com");
        emailSender.send(mensaje);
    }


    public void enviarCorreoConAdjunto(
            String para,
            String asunto,
            String contenido,
            byte[] pdf,
            String nombreArchivo
    ) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");


            helper.setTo(para);
            helper.setSubject(asunto);
            helper.setText(contenido);
            helper.setFrom("teatroccran@gmail.com");
            // helper.addAttachment(nombreArchivo,
            //         () -> pdf);
            helper.addAttachment(nombreArchivo, new ByteArrayResource(pdf));

            emailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }
}