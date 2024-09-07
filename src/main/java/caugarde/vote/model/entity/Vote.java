package caugarde.vote.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Vote {
    @Id
    private String votePk;
    private String title;
    private String content;
    private Timestamp startDate;
    private Timestamp submitDate;
    private int limitPeople;

    // Getters and Setters
}