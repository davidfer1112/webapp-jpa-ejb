package org.aguzman.apiservlet.webapp.headers.services;

import jakarta.annotation.Resource;
import jakarta.jms.*;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EnviarEmailService {
/*
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.out.println("Error en el número de parámetros de ejecución");
            System.exit(1);
        }

        String nameQueue = args[0];
        int numMensajes = args.length == 2 ? Integer.parseInt(args[1]) : 1;

        try {
            InitialContext context = new InitialContext();
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup("java:/ConnectionFactory");
            Queue queue = (Queue) context.lookup(nameQueue);

            try (QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
                 QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                 QueueSender queueSender = queueSession.createSender(queue)) {

                for (int i = 0; i < numMensajes; i++) {
                    TextMessage msg = queueSession.createTextMessage();
                    msg.setText("Mensaje No." + (i + 1));
                    queueSender.send(msg);
                }
            }
        } catch (NamingException | JMSException e) {
            System.out.println("Error al enviar mensajes a la cola: " + e.getMessage());
            e.printStackTrace();
        }
    }

 */
}
