package org.example.productcatalogservice_feb2026.TableInheritanceExamples.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "st_instructor")
@DiscriminatorValue(value = "INSTRUCTOR")
public class Instructor extends User {
    String company;
}
