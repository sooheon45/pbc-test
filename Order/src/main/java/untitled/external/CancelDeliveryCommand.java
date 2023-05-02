package untitled.external;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
public class CancelDeliveryCommand {

    @Id
    private Long id;

    private String address;
    private Long orderId;
}
