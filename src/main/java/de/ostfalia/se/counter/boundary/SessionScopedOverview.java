package de.ostfalia.se.counter.boundary;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Dieses Beispiel wird am Do. 17.03. in der Vorlesung erklärt.
 * Nicht direkt für die Aufgabenstellung relevant.
 */
@Named
@SessionScoped
public class SessionScopedOverview implements Serializable {

    @Getter
    @Setter
    int counter;

    protected void increaseCounter(){
        counter++;
    }


}
