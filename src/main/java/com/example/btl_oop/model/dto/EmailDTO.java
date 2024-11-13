package com.example.btl_oop.model.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class EmailDTO {
    private String to;
    private String subject;
    private String body;

}
