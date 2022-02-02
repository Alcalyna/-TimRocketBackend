package com.example.timrocket_backend.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "SESSION")
public class Session {
    @Id
    @GeneratedValue
    @Column(name = "SESSION_ID")
    private UUID sessionId;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "TIME")
    private LocalTime time;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "F2F_LOCATION")
    private String f2fLocation;

    @Column(name = "REMARKS")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "COACHEE_ID", referencedColumnName = "USER_ID")
    private User coachee;

    @ManyToOne
    @JoinColumn(name = "COACH_ID", referencedColumnName = "USER_ID")
    private User coach;

    public Session() {
    }

    public Session(String subject, LocalDate date, LocalTime time, String location, String f2fLocation, String remarks, User coachee, User coach) {
        this.subject = subject;
        this.date = date;
        this.time = time;
        this.location = location;
        this.f2fLocation = f2fLocation;
        this.remarks = remarks;
        this.coachee = coachee;
        this.coach = coach;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public String getSubject() {
        return subject;
    }
}
