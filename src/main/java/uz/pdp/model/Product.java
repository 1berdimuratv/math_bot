package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    private Integer id; // u
    private String categoryId;
    private String name;
    private String url;
    private String desc;
    private Long price;
    private Boolean isActive = Boolean.TRUE;

}
