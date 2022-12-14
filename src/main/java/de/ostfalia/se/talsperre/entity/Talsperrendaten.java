package de.ostfalia.se.talsperre.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity-Klasse der Talsperrendaten.
 * Diese Entity bildet jeweils einzelne Messdaten der Talsperren ab.
 * Diese Daten werden mit der dazugehörigen Entity
 * @see de.ostfalia.se.talsperre.entity.Talsperre und dem Zeitpunkt verknüft.
 *
 * Da es hier zwei ID-Attribute gibt, wird eine separate ID-Klasse benötigt.
 * @see de.ostfalia.se.talsperre.entity.TalsperrendatenID
 */

/**
 * NamesQuery enthalten spezielle Select Aufrufe welche vom
 * @see de.ostfalia.se.talsperre.control.TalsperrendatenService an die Datenbank
 * gesendet werden können.
 *
 * In dieser Entity gibt es als Beispiel mehrere NamendQuerys. Die Namen können belibig sein,
 * müssen aber über das gesamte Projekt eindeutig bleiben.
 * Daher hat es sich bewährt diese mit der Notation "klassenname.zweck" zu notieren.
 */
@NamedQuery(name = "talsperrendaten.getAll", query = "select tsd from Talsperrendaten tsd")
@NamedQuery(name = "talsperrendaten.getByTalsperrenID", query = "select tsd from Talsperrendaten tsd where fkTalsperre = :idTalsperre ORDER BY tsd.tstamp DESC")
@NamedQuery(name = "talsperrendaten.getByTalsperrenIDWithTimeLimits", query = "select tsd from Talsperrendaten tsd where fkTalsperre = :idTalsperre AND tsd.tstamp >= :from AND tsd.tstamp <= :to")
@NamedQuery(name = "talsperrendaten.getByTalsperrenIDBeforeTime", query = "select tsd from Talsperrendaten tsd where fkTalsperre = :idTalsperre AND tsd.tstamp <= :tBefore")
@NamedQuery(name = "talsperrendaten.getByTalsperrenIDWithLimit", query = "select tsd from Talsperrendaten tsd where fkTalsperre = :idTalsperre ORDER BY tsd.tstamp DESC")

@Entity
@Table(name = "Talsperrendaten")
@IdClass(TalsperrendatenID.class)
@Getter
@Setter
public class Talsperrendaten {


    @Id
    @Column(name = "fkTalsperre")
    int fkTalsperre;

    @Id
    @Column(name = "Zeitpunkt")
    LocalDateTime tstamp;

    @Column(name = "Stauinhalt")
    double stauinhalt;

    @Column(name = "Zufluss")
    double zufluss;

    @Column(name = "Abgabe")
    double abgabe;


}
