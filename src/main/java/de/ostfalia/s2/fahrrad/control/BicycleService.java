package de.ostfalia.s2.fahrrad.control;

import de.ostfalia.s2.fahrrad.entity.Bicycle;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
public class BicycleService implements Serializable {

    @PersistenceContext(unitName = "Fahrraddaten")
    EntityManager em;

    private final static int STEP = 86400;

    private List<Integer> channels;

    /**
     * @param channel Bicycle channel
     * @param from    Min timestamp
     * @param to      Max timestamp
     * @param step    Step between each entry in millis
     * @return Matching bicycles
     */
    public List<Bicycle> getFahrradDaten(int channel, LocalDateTime from, LocalDateTime to, long step) {
        if (channels == null) {
            channels = getBicycleIDs();
        }

        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannelWithTimeLimits", Bicycle.class);

        query.setParameter("channelBicycle", channel);
        query.setParameter("from", from == null ? LocalDateTime.MIN : from);
        query.setParameter("to", to == null ? LocalDateTime.now() : to);

        List<Bicycle> list = new ArrayList<>();

        int first = 0;

        query.setMaxResults(STEP);

        for (int i = 0; i < 100; i++) {
            query.setFirstResult(first);

            List<Bicycle> temp = query.getResultList();

            for (Bicycle bike : query.getResultList()) {
                list.add(bike);
            }

            first += STEP;

            if (temp.size() < STEP) {
                break;
            }
        }
        return list;
    }

    public List<Integer> getBicycleIDs() {
        List<Integer> list = new ArrayList<>();

        List<Bicycle> channelBicycle = em.createNamedQuery("bicycle.getBicycleIDs", Bicycle.class).getResultList();

        channelBicycle.forEach(bicycle -> list.add(bicycle.getChannel()));

        return list;
    }

    public List<Integer> getChannels(boolean isExtended){
        if(channels == null) channels = getBicycleIDs();
        if (isExtended) {
            List<Integer> list = new ArrayList<>(channels);
            list.add(0,-1);
            return list;
        }
        return channels;
    }

}


