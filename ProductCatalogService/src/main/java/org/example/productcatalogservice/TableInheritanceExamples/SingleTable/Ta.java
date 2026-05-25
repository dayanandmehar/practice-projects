package org.example.productcatalogservice_feb2026.TableInheritanceExamples.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "st_ta")
@DiscriminatorValue(value = "TA")
public class Ta extends User {
    Long hours;
}
