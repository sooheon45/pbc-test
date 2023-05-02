package application.domain;

import application.domain.DeliveryStarted;
import application.DeliveryApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import org.springframework.context.ApplicationContext;

@Entity
@Table(name="Delivery_table")
@Data

public class Delivery  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    private Long id;    
    
    
    
    private String address;

    @PostPersist
    public void onPostPersist(){
       

        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();


    
    }



    public static DeliveryRepository repository(){
        DeliveryRepository deliveryRepository = applicationContext().getBean(DeliveryRepository.class);
        return deliveryRepository;
    }

    public static ApplicationContext applicationContext(){        
        return DeliveryApplication.applicationContext;
    }

    public void (){
        //
    }


    public void cancelDelivery(CancelDeliveryCommand cancelDeliveryCommand){
        // implement the business logics here:

        DeliveryCanceled deliveryCanceled = new DeliveryCanceled(this);
        deliveryCanceled.setOrderId(cancelDeliveryCommand.getOrderId());
        /** Logic **/
        
        deliveryCanceled.publishAfterCommit();

    }



}
