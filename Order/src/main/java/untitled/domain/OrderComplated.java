package untitled.domain;

import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

@Data
@ToString
public class OrderComplated extends AbstractEvent {

    private Long id;
    private String userId;
    private Long productId;

    public OrderComplated(Order aggregate) {
        super(aggregate);
    }

    public OrderComplated() {
        super();
    }
}
