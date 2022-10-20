package de.ostfalia.s1.lamp.boundary;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

public class nameChangeListener implements ValueChangeListener {

    @Override
    public void processValueChange(ValueChangeEvent event)
            throws AbortProcessingException {

        LampControllerView lampControllerView = (LampControllerView) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("lampControllerView");
        lampControllerView.setName(event.getNewValue().toString());
    }
}