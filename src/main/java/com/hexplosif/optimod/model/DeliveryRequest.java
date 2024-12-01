package com.hexplosif.optimod.model;

import lombok.Data;

@Data
public class DeliveryRequest {

        private Long id;

        private Long idPickup;

        private Long idDelivery;

        private Long idWarehouse;
}
