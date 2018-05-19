package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receptor {

  private final static String QUEUE_NAME = "minha-fila";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    //credenciais
    factory.setUri("amqp://intncmcf:UyFMaUKymdXZhqKOlihaiKyV6lcn9mI9@mosquito.rmq.cloudamqp.com/intncmcf");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    //declarando uma fila. a fila tem o mesmo nome da do emissor.
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Esperando recebimento de mensagens...");

    //consumidor de mensagens
    Consumer consumer = new DefaultConsumer(channel) {
      //método será executado ao chegar msg.
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        //mapear msg para string.
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Mensagem recebida: '" + message + "'");
      }
    };
    //o metodo handleDelivery vai ser chamado toda vez que houver mensagem na fila.
    channel.basicConsume(QUEUE_NAME, true, consumer);
  }
}