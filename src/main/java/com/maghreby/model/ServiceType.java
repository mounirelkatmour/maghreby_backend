package com.maghreby.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ServiceType {
    ACCOMMODATION,
    CAR,
    RESTAURANT,
    ACTIVITY,
    NSP
}