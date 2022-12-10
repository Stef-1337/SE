package de.ostfalia.s3.boundary;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Getter
@Setter
@Named
@RequestScoped
public class TestView{

    public void viewCommands() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(false)
                .build();
        System.out.println("test");
        PrimeFaces.current().dialog().openDynamic("src/main/webapp/RemoteControl/commandView.xhtml", options, null);
    }
}
