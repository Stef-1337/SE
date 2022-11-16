package de.ostfalia.s3.boundary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private String code, name, category;
    private int quantity;
}
