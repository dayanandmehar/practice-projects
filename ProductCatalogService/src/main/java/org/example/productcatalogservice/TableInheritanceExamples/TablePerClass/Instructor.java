package org.example.productcatalogservice_feb2026.TableInheritanceExamples.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_instructor")
public class Instructor extends User {
    String company;
}
