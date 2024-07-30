package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Order {
    private static int n = 0;
    private final Integer id;
    private Long userId;
    private Long deliveryPrice;
    private Long actualPrice;
    {
        this.id = n++;
    }

    public Order(Long userId, Long actualPrice) {
        this.userId = userId;
        this.deliveryPrice = null;
        this.actualPrice = actualPrice;
    }
}
