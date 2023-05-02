package untitled.domain;

import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

@Data
@ToString
public class DeliveryCanceled extends AbstractEvent {

    private Long id;
    private String address;
    private Long orderId;

    public DeliveryCanceled(Delivery aggregate) {
        super(aggregate);
    }

    public DeliveryCanceled() {
        super();
    }
}
