package de.ostfalia.se.counter.boundary;

import lombok.Getter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Dieses Beispiel wird am Do. 17.03. in der Vorlesung erklärt.
 * Nicht direkt für die Aufgabenstellung relevant.
 */
@Named
@RequestScoped
public class RequestCounterOverview {
    @Getter
    int counter;

    @Inject
    SessionScopedOverview sso;

    @Inject
    ApplicationScopedOverview aso;


    public void incrementCounter(){
        counter++;
        sso.increaseCounter();
        aso.incrementCounter();



    }
}
