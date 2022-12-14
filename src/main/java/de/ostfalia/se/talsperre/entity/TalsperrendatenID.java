package de.ostfalia.se.talsperre.entity;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Diese Klasse stellt die ID der Talsperrendaten dar.
 * Da es hierfür zwei Attribute gibt müssen diese in einer solchen Klasse zusammengeführt werden.
 *
 * Für eine ID-Klasse sind folgende Punkte technisch nötig:
 * - All Args Constructor
 * - Equals & HashCode
 * - implements Serializable
 *
 *
 * Der Konstruktor wird hier durch Lombok mittels @AllArgsConstructor erzeugt.
 * Alternativ könnten Sie den Konstruktor auch selber anlegen.
 * Selbiges gilt für Equals und HashCode.
 *
 * Der Default bzw. NoArgsContructor ist optional.
 *
 */


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TalsperrendatenID implements Serializable {

    int fkTalsperre;
    LocalDateTime tstamp;

}
