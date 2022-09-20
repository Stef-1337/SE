package de.ostfalia.se.talsperre.boundary;

import de.ostfalia.se.talsperre.control.TalsperreService;
import de.ostfalia.se.talsperre.control.TalsperrendatenService;
import de.ostfalia.se.talsperre.entity.Talsperre;
import de.ostfalia.se.talsperre.entity.Talsperrendaten;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Backend Bean für die Talsperren Detailansicht.
 */
@Named
@RequestScoped
public class TalsperreDetailview {

    @Inject
    TalsperreService tss;
    @Inject
    TalsperrendatenService tsds;
    @Getter
    @Setter
    int idTalsperre;

    public Talsperre getTalsperre() {
        return tss.findById(idTalsperre);
    }

    public Talsperrendaten getStatus() {
        return tsds.getLast(idTalsperre);
    }

    public List<Talsperrendaten> getAllTalsperrenDatenByTalsperrenID(int idTalsperre) {
        return tsds.getByTalsperrenID(idTalsperre);
    }


}
