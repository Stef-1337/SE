package de.ostfalia.s3.boundary;

import lombok.Getter;
import lombok.Setter;

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

    public TestView(){
        for (int i = 1; i < 9; i++){
            slots.add(i);
        }
        config = new ArrayList<>();
        config.add("abc");
        config.add("def");
    }
}
