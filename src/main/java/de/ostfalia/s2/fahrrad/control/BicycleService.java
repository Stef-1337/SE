package de.ostfalia.s2.fahrrad.control;

import de.ostfalia.s2.fahrrad.entity.Bicycle;

import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@SessionScoped
public class BicycleService implements Serializable {

    @PersistenceContext(unitName = "Fahrraddaten")
    EntityManager em;

    /**
     *
     * @param channel Bicycle channel
     * @param from Min timestamp
     * @param to Max timestamp
     * @param step Step between each entry in millis
     * @return Matching bicycles
     */
    public List<Bicycle> getFahrradDaten(int channel, LocalDateTime from, LocalDateTime to, long step){
        TypedQuery<Bicycle> query = em.createNamedQuery("bicycle.getByBicycleChannelWithTimeLimits", Bicycle.class);

        query.setParameter("channelBicycle", channel);
        query.setParameter("from", from == null ? LocalDateTime.MIN : from);
        query.setParameter("to", to == null ? LocalDateTime.now() : to);

        return query.getResultList();
    }

    public List<Bicycle> getAll() {
        return em.createNamedQuery("bicycle.getAll", Bicycle.class).getResultList();
    }

}
