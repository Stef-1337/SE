package de.ostfalia.s3.boundary;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DialogFrameworkOptions;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("dfView")
@RequestScoped
public class DFView {

    public void viewProducts() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(false)
                .build();

        PrimeFaces.current().dialog().openDynamic("viewProducts", options, null);
    }

    public void viewProductsCustomized() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .modal(true)
                .width("640")
                .height("340")
                .contentHeight("100%")
                .contentWidth("100%")
                .headerElement("customheader")
                .build();

        PrimeFaces.current().dialog().openDynamic("viewProducts", options, null);
    }

    public void viewResponsive() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .modal(true)
                .fitViewport(true)
                .responsive(true)
                .width("900px")
                .contentWidth("100%")
                .resizeObserver(true)
                .resizeObserverCenter(true)
                .resizable(false)
                .styleClass("max-w-screen")
                .iframeStyleClass("max-w-screen")
                .build();

        PrimeFaces.current().dialog().openDynamic("viewResponsive", options, null);
    }

    public void viewProductsLargerThanViewport() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .modal(true)
                .fitViewport(true)
                .build();

        PrimeFaces.current().dialog().openDynamic("viewProductsLargerThanViewport", options, null);
    }

    public void chooseProduct() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(false)
                .draggable(false)
                .modal(false)
                .build();

        PrimeFaces.current().dialog().openDynamic("selectProduct", options, null);
    }

    public void onProductChosen(SelectEvent event) {
        Product product = (Product) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Product Selected", "Name:" + product.getName());

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", " Always Bet on Prime!");

        PrimeFaces.current().dialog().showMessageDynamic(message);
    }
}