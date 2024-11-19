package com.example.btl_oop.model.request.room;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomFilterDataRequest {
    private String price, area,address, roomType;

    public boolean isNull() {
        return (price == null || price.equals("null")) && (area == null || area.equals("null")) && (address == null || address.equals("null")) && (roomType == null || roomType.equals("null"));
    }
}
