package persistence;

import jakarta.persistence.*;
import model.*;

import java.time.LocalDate;

@Entity
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private String client;  
    
    @ManyToOne
    @JoinColumn(name = "id")
    private String routePackage; 
    
    private int purchase_day;
    private int purchase_month;
    private int purchase_year;
    
    private float cost;
    
    private int expiration_day;
    private int expiration_month;
    private int expiration_year;

    
}
