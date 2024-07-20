package com.codingshuttle.app.zomatoApp.entities;

import com.codingshuttle.app.zomatoApp.entities.enums.AvailableStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
public class DeliveryExecutive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point currentLocation;

    private Double rating;

    @Enumerated(EnumType.STRING)
    private AvailableStatus availableStatus;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;
}

