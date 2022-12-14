package de.ostfalia.se.counter.boundary;

import lombok.Getter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


/**
 * Dieses Beispiel wird am Do. 17.03. in der Vorlesung erklärt.
 * Nicht direkt für die Aufgabenstellung relevant.
 */
@Named
@ApplicationScoped
public class ApplicationScopedOverview {

    @Getter
    int counter;


    protected void incrementCounter() {
        counter++;
    }
}
