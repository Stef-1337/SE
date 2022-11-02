package de.ostfalia.s2.fahrrad.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BicycleID implements Serializable {
    int channel;
    LocalDateTime timestamp;
}
