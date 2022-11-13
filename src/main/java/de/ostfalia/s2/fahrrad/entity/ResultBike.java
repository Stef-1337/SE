package de.ostfalia.s2.fahrrad.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResultBike {

    private int channel;

    private long step;
    private int numbers;

    private LocalDateTime timestamp;
    private Double rotations, active;

    public ResultBike(int channel, long step, int numbers, LocalDateTime timestamp, Double rotations, Double active){
        this.channel = channel;
        this.step = step;
        this.numbers = numbers;
        this.timestamp = timestamp;
        this.rotations = rotations;
        this.active = active;
    }

}
