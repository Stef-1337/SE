package de.ostfalia.s3.boundary;

import de.ostfalia.s3.control.CommandProcessor;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
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


    private int positionSelected;
    private List<Integer> freePositions = new ArrayList<>(8);


    public RemoteControlView(){
        for (int i = 1; i  < 9; i++) {
            freePositions.add(i);
        }
    }

    public List<Integer> showFreePositions(){
        return null;
    }
    public void init(){
        List<SelectItem> items = new ArrayList<>();
        SelectOneMenu menu = new SelectOneMenu();
        SelectItemGroup group = new SelectItemGroup();
        for (Integer i: freePositions) {
            items.add(new SelectItem(i));
        }
    }

    public void switchButton(){
    }

    public void colorButton(){

    }

}
