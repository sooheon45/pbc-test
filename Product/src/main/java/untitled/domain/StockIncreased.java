package untitled.domain;

import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

@Data
@ToString
public class StockIncreased extends AbstractEvent {

    private Long id;
    private String name;
    private Integer stock;
    private Long orderId;

    public StockIncreased(Product aggregate) {
        super(aggregate);
    }

    public StockIncreased() {
        super();
    }
}
