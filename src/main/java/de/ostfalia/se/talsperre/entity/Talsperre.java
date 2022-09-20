package de.ostfalia.se.talsperre.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity-Klasse der Talsperren.
 * Diese Entity enthält alle Attribute zur Beschreibung einer Talsperre.
 * Die gesammelten Talsperrendaten der Füllstände und Co werden in der Entity
 * @see de.ostfalia.se.talsperre.entity.Talsperrendaten abgebildet.
 */

/**
 * NamesQuery enthalten spezielle Select Aufrufe welche vom
 * @see de.ostfalia.se.talsperre.control.TalsperreService an die Datenbank
 * gesendet werden können.
 */
@NamedQuery(name = "talsperre.getAll", query = "select ts from Talsperre ts")

@Entity
@Table(name = "Talsperren")
@Getter
@Setter
public class Talsperre {

    @Id
    @Column(name = "ID")
    int id;

    @Column(name = "Name")
    String name;

    @Column(name = "InfoURL")
    String infoURL;

    @Column(name = "ImageID")
    String imageID;

}
