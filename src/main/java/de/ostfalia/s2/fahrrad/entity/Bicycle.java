package de.ostfalia.s2.fahrrad.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedQuery(name = "bicycle.getAll", query = "select bc from Bicycle bc")
@NamedQuery(name = "bicycle.getByBicycleChannel", query = "select bc from Bicycle bc where channel = :channelBicycle ORDER BY bc.timestamp DESC")
@NamedQuery(name = "bicycle.getByBicycleChannelWithTimeLimits", query = "select bc from Bicycle bc where channel = :channelBicycle AND bc.timestamp >= :from AND bc.timestamp <= :to")
@NamedQuery(name = "bicycle.getByBicycleChannelBeforeTime", query = "select bc from Bicycle bc where channel = :channelBicycle AND bc.timestamp <= :tBefore")
@NamedQuery(name = "bicycle.getByBicycleChannelWithLimit", query = "select bc from Bicycle bc where channel = :channelBicycle ORDER BY bc.timestamp DESC")


@Entity
@Table(name = "bicycle")
@IdClass(BicycleID.class)
@Getter
@Setter
public class Bicycle {

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getRotation_per_second() {
        return rotation_per_second;
    }

    public void setRotation_per_second(int rotation_per_second) {
        this.rotation_per_second = rotation_per_second;
    }

    @Id
    @Column(name = "channel")
    int channel;

    @Id
    @Column(name = "timestamp")
    LocalDateTime timestamp;

    @Id
    @Column(name = "rotation_per_second")
    int rotation_per_second;

}
