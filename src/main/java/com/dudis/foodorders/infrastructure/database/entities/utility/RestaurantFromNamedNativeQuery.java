package com.dudis.foodorders.infrastructure.database.entities.utility;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantFromNamedNativeQuery {

    private Integer restaurantId;
    private String name;
    private String description;
    private String type;
    private Integer menuId;
    private Integer ownerId;

}
