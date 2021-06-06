package br.com.zup.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try(KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>()) {
            String userId = UUID.randomUUID().toString();
            String orderId = UUID.randomUUID().toString();
            Order order = new Order(userId, orderId, new BigDecimal("300"));
            orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);
        }

        try(KafkaDispatcher<Email> emailDispatcher = new KafkaDispatcher<>()) {
            String userId = UUID.randomUUID().toString();
            String corpo = "Processando o pedido";
            Email email = new Email(userId, corpo);
            emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);
        }

    }
}
