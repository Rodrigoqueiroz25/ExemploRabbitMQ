package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Emissor {

  private final static String QUEUE_NAME = "minha-fila";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    //fornece credenciais de acesso
    factory.setUri("amqp://intncmcf:UyFMaUKymdXZhqKOlihaiKyV6lcn9mI9@mosquito.rmq.cloudamqp.com/intncmcf");
    //conexão.
    Connection connection = factory.newConnection();
    //Partilhar a conexão tcp. canais lógicos dentro da conexao tcp, pode criar quantos quiser. a conexão tcp é uma só
    Channel channel = connection.createChannel();

    //dentro do canal que acontece a interação com o rabbitMQ. ver na documentação os parametros.
    //criação da fila.
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    
    String message = "Olá!!!";
    //mensagem vai para a fila.
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    System.out.println(" [x] Mensagem enviada: '" + message + "'");

    channel.close();
    connection.close();
  }
}