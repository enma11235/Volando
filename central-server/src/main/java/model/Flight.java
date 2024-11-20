package model;
import datatype.*;
import java.time.Duration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Flight {
	
	//links
	private List<Booking> bookings; 
	private FlightRoute Route;

	//atributes
	private String name;
	private LocalDate date;
	private Duration duration;
	private int tourist_seats;
	private int available_tourist_seats; 
	private int executive_seats;
	private int available_executive_seats; 
	private LocalDate registration_date; 
	private String image;
	private int seat_number = 1;
    
   //constructor	   
   public Flight(String name, LocalDate date, Duration duration, int tseats, int eseats, LocalDate rdate, String image) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.tourist_seats = tseats; 
        this.available_tourist_seats = tseats; 
        this.executive_seats = eseats; 
        this.available_executive_seats = eseats; 
        this.registration_date = rdate; 
        this.image = image;
    }
   
   public Flight(String name, LocalDate date, Duration duration, int TSeats, int ESeats, int ATSeats, int AESeats, LocalDate RDate, String image) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.tourist_seats = TSeats; 
        this.available_tourist_seats = ATSeats; 
        this.executive_seats = ESeats; 
        this.available_executive_seats = AESeats; 
        this.registration_date = RDate; 
        this.image = image;
    }
   
   
//---------------OPERATIONS-----------------------
   
public String getName() {
	return name;
}

public LocalDate getDate() {
	return date;
}

public Duration getDuration() {
	return duration;
}

public int getTouristSeats() {
	return this.tourist_seats;
}
public int getAvailableRouristSeats() {
	return available_tourist_seats;
}


public int getExecutiveSeats() {
	return executive_seats;
}
public int getAvailableExecutiveSeats() {
	return available_executive_seats;
}


public LocalDate getRegistrationDate() {
	return registration_date;
}

public void setRegistrationDate(LocalDate n) {
	this.registration_date = n;
}

public DTVuelo getDTO() {
	List<DTReserva> dtres = null;
	if (bookings!=null) {
		dtres = new ArrayList<DTReserva>();
		for (Booking r : bookings) {
			dtres.add(r.getData());
		}
	}
	DTVuelo dtv = new DTVuelo(this.name, this.date, this.duration, this.tourist_seats, this.executive_seats, this.available_tourist_seats, this.available_executive_seats, this.registration_date, dtres, this.image);
	return dtv;
}
   
public void addBooking(Booking b) {
	if (bookings == null) {
        bookings = new ArrayList<Booking>(); 
    }
			if (b.getTipoAsiento() == SeatType.TOURIST)
				this.available_tourist_seats -= b.getCantPasajeros();
			else
				this.available_executive_seats -= b.getCantPasajeros();					
				
    bookings.add(b); 
}

public FlightRoute getRoute() {
	return this.Route;
}

public void setRoute(FlightRoute n) {
	this.Route = n;
}

public List<Booking> getBookings() {
	return this.bookings;
}

public String getImage() {
	return this.image;
}

public void setImage(String n) {
	this.image = n;
}

public int getSeatNumber() {
	return seat_number;
}

public void setSeatNumber(int n) {
	this.seat_number = n;
}

}
