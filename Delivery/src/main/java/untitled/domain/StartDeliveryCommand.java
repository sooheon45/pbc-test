package untitled.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
public class StartDeliveryCommand {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
}
