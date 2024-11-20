package persistence;

import jakarta.persistence.*;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import datatype.DocumentType;

@Entity
public class ClientEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
    private String last_name;
    private int birthday_day;
    private int birthday_month;
    private int birthday_year;
    private String nationality;
    
    @Enumerated(EnumType.STRING)
    private DocumentType document_type;
    
    private String document_number;
    
    @OneToMany(mappedBy = "client")
	private List<BookingEntity> bookings;
    
    @OneToMany(mappedBy = "client")
	private List<PurchaseEntity> purchases;
    
}
