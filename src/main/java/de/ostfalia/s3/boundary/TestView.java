package de.ostfalia.s3.boundary;

import de.ostfalia.s1.lamp.HueColor;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Named
@SessionScoped
public class TestView implements Serializable {
    private String name;
    private Boolean on = false;
    private List<Integer> slots = new ArrayList();

    List<String> config;

    HueColor hueColor;

    public TestView(){
        for (int i = 1; i < 9; i++){
            slots.add(i);
        }
        config = new ArrayList<>();
        config.add("abc");
        config.add("def");
    }

    public void reset() {
        PrimeFaces.current().resetInputs("form:remote");
    }

    public void test(){
        System.out.println(hueColor.getName());
        System.out.println("läuft");
    }
}
