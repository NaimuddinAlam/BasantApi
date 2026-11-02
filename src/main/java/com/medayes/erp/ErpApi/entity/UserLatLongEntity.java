package com.medayes.erp.ErpApi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "PAY_USER_LAT_LONG")
public class UserLatLongEntity {

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "LAT")
    private Double latitude;

    @Column(name = "LONG")
    private Double longitude;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    // ROW_ID is the actual unique key per GPS ping — USER_ID repeats
    // across every row for the same user, so it must never be @Id.
    // Using USER_ID as @Id caused Hibernate to collapse all of a
    // user's rows down to a single cached entity in the session's
    // first-level cache, which is why findLocations() effectively
    // returned far fewer distinct points than actually existed in
    // the table (and often < 2, producing a false 0.0 km result).
    @Id
    @Column(name = "ROW_ID")
    private String rowId;

    // getters/setters
}