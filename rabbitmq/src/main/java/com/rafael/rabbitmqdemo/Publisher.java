package com.rafael.rabbitmqdemo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class Publisher {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        String NOME_FILA = "FILA_PDIST_HELLO";

        try (
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()
        ) {
            String mensagem = "Mensagem enviado pelo Publiser para fila:" + NOME_FILA;

            channel.queueDeclare(NOME_FILA, false, false, false, null);
            channel.basicPublish("", NOME_FILA, null, mensagem.getBytes());
            System.out.println("Enviada: " + mensagem);
        } catch (IOException | TimeoutException error) {
            System.out.println(error);
        }
    }
}
