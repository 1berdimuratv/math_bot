package uz.pdp.model;

import lombok.Data;


@Data
public class OrderProduct {
    private final Integer id;
    private static int sequence = 0;
    private Integer productId;
    private Integer amount;
    private Integer orderId;

    private Boolean submitted = Boolean.FALSE;
    {
        id = sequence++;
    }

    public OrderProduct(Integer productId, Integer amount, Integer orderId) {
        this.productId = productId;
        this.amount = amount;
        this.orderId = orderId;
    }
}
