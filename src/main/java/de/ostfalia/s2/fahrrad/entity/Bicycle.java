package de.ostfalia.s2.fahrrad.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NamedQuery(name = "bicycle.getByBicycleChannelWithTimeLimits", query = "select bc from Bicycle bc where channel = :channelBicycle AND bc.timestamp >= :from AND bc.timestamp <= :to")
@NamedQuery(name = "bicycle.getBicycleIDs", query = "select bc from Bicycle bc group by channel")
@Entity
@Table(name = "bicycle")
@Getter
@Setter
public class Bicycle implements Serializable {

    public int getChannel() {
        return channel;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getRotations_per_second() {
        return rotations_per_second;
    }

    public void setRotations_per_second(int rotations_per_second) {
        this.rotations_per_second = rotations_per_second;
    }

    @Id
    @Column(name = "channel")
    int channel;

    @Id
    @Column(name = "timestamp")
    LocalDateTime timestamp;

    @Id
    @Column(name = "rotations_per_second")
    int rotations_per_second;

    public String channelToString() {
        return String.valueOf(channel);
    }
}
