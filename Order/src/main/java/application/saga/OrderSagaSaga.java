package application.saga;

import application.config.kafka.KafkaProcessor;
import application.domain.*;
import application.external.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class OrderSagaSaga {

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderPlaced'"
    )
    public void wheneverOrderPlaced_OrderSaga(
        @Payload OrderPlaced orderPlaced,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        OrderPlaced event = orderPlaced;
        System.out.println(
            "\n\n##### listener OrderSaga : " + orderPlaced + "\n\n"
        );

        Delivery delivery = new Delivery();
        delivery.set(event.getId());

        deliveryService.startDelivery(delivery);

        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DeliveryStarted'"
    )
    public void wheneverDeliveryStarted_OrderSaga(
        @Payload DeliveryStarted deliveryStarted,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        DeliveryStarted event = deliveryStarted;
        System.out.println(
            "\n\n##### listener OrderSaga : " + deliveryStarted + "\n\n"
        );

        try {
            DecreaseStockCommand decreaseStockCommand = new DecreaseStockCommand();
            /* Logic */
            decreaseStockCommand.setOrderId(event.getOrderId());

            productService.decreaseStock(
                event.getOrderId(),
                decreaseStockCommand
            );
        } catch (Exception e) {
            CancelDeliveryCommand cancelDeliveryCommand = new CancelDeliveryCommand();
            /* Logic */
            cancelDeliveryCommand.setOrderId(event.getOrderId());

            deliveryService.cancelDelivery(
                event.getOrderId(),
                cancelDeliveryCommad
            );
        }

        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='StockDecreased'"
    )
    public void wheneverStockDecreased_OrderSaga(
        @Payload StockDecreased stockDecreased,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        StockDecreased event = stockDecreased;
        System.out.println(
            "\n\n##### listener OrderSaga : " + stockDecreased + "\n\n"
        );

        UpdateStatusCommand updateStatusCommand = new UpdateStatusCommand();
        /* Logic */
        updateStatusCommand.setOrderId(event.getId());

        orderService.updateStatus(event.getId(), updateStatusCommand);

        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderComplated'"
    )
    public void wheneverOrderComplated_OrderSaga(
        @Payload OrderComplated orderComplated,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        OrderComplated event = orderComplated;
        System.out.println(
            "\n\n##### listener OrderSaga : " + orderComplated + "\n\n"
        );

        /* Logic */

        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }
}
