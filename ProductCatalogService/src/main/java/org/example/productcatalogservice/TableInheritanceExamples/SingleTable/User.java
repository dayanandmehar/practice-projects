package org.example.productcatalogservice_feb2026.TableInheritanceExamples.SingleTable;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "st_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    UUID id;
    String name;
}
