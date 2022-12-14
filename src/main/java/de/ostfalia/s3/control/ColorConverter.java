package de.ostfalia.s3.control;

import de.ostfalia.s1.lamp.ColorSelector;
import de.ostfalia.s1.lamp.HueColor;

import java.net.URI;
import java.net.URISyntaxException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("ColorConverter")
public class ColorConverter implements Converter {

    ColorSelector colorSelector = new ColorSelector();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        HueColor hueColor = colorSelector.getColors().get(s);
        return hueColor;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }
}
