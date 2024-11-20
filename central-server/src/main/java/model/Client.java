package model;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import datatype.*;
import jakarta.persistence.*;

public class Client extends User {
	
	//links
	private List<Booking> bookings = new ArrayList<>();
	private List<Compra> purchases = new ArrayList<>();
	
	//atributes
	private String last_name;
	private LocalDate birthday;
	private String nationality;
	private DocumentType document_type;
	private String document_number;
	
	public Client(String nickname, String name, String email, String password, String lastName, LocalDate birthday,
			String nationality, DocumentType documentType, String documentNumber, String imageUrl) {
		super(nickname, name, email, password, imageUrl);
		this.last_name = lastName;
		this.birthday = birthday;
		this.nationality = nationality;
		this.document_type = documentType;
		this.document_number = documentNumber;
	}
	
	@Override
	public Boolean isAirline() {
		return false;
	}

	@Override
	public Boolean isClient() {
		return true;
	}

	@Override
	public DTUsuario getDTO() {
		return new DTCliente(super.getNickname(), super.getNombre(), super.getEmail(), super.getPassword(), this.last_name, this.birthday, this.nationality, this.document_type, this.document_number, super.getImageUrl());
	}
	
	//public void asociarPaqueteACliente(Paquete p) {}
	
	public void addBooking(Booking b) {
		this.bookings.add(b);
	}
	
	public void removeBooking(Booking b) {
		this.bookings.remove(b);
	}
	
	public List<Booking> getAllBookings() {
		return this.bookings;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String lastName) {
		this.last_name = lastName;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public DocumentType getDocumentType() {
		return document_type;
	}

	public void setDocumentType(DocumentType documentType) {
		this.document_type = documentType;
	}

	public String getDocumentNumber() {
		return document_number;
	}

	public void setDocumentNumber(String documentNumber) {
		this.document_number = documentNumber;
	}

	public void addPurchase(Compra p) {
		// TODO Auto-generated method stub
		purchases.add(p);

	}

	public List<Compra> getAllPurchases() {
		return purchases;
	}

}
