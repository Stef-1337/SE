package de.ostfalia.s2.fahrrad.boundary;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;



public enum Kennzahl {
    ROTATIONS, SPEED, DISTANCE, TIME;
}
