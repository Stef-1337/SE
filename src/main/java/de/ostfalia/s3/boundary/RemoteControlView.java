package de.ostfalia.s3.boundary;

import de.ostfalia.s3.control.CommandProcessor;
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
public class RemoteControlView implements Serializable {

    private CommandProcessor commandProcessor;

    private List<Integer> freePositions = new ArrayList<>(8);

    public void switchButton(){
    }

    public void colorButton(){

    }
    public void initList(){
        for (int i = 1; i  < 9; i++){
            freePositions.add(i);
        }
    }
}
