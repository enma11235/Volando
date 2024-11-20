package model;

import java.util.ArrayList;
import datatype.*;
import java.util.List;

public class Airline extends User {
	
	//links
	private List<FlightRoute> flight_routes;

	//atributes
	private String description;
	private String web_site;

	public Airline(String nickname, String name, String email, String password, String description, String webSite, List<FlightRoute> fRoutes, String image) {
		super(nickname, name, email, password, image);
		this.description = description;
		this.web_site = webSite;
		this.flight_routes = fRoutes;
	}
	
	//----------------OPERACIONES---------------------

	@Override
	public Boolean isAirline() {
		return true;
	}

	@Override
	public Boolean isClient() {
		return false;
	}

	@Override
	public DTUsuario getDTO() {
		return new DTAerolinea(super.getNickname(), super.getNombre(), super.getEmail(), super.getPassword(), this.description, this.web_site, super.getImageUrl());
	}
	
	public void addFlightRoute(FlightRoute route) {
		this.flight_routes.add(route);
	}

	
	public void removeFlightRoute(FlightRoute route) {
		this.flight_routes.remove(route);
	}
	
	public List<String> listFlightRoutes() {
		List<String> nombresrvs = new ArrayList<String>();
		for (int i = 0; i < flight_routes.size(); i++) {
			nombresrvs.add(flight_routes.get(i).getNombre());
		}
		return nombresrvs;
	}
	
	public List<FlightRoute> getAllFlightRoutes(){
		return flight_routes;
	}
	
	public FlightRoute getFlightRoute(String routeName) {
	    if (flight_routes == null || flight_routes.isEmpty()) {
	        return null; 
	    } 
	    for (FlightRoute ruta : flight_routes) {
	        if (ruta.getNombre().equals(routeName)) {
	            return ruta; 
	        }
	    }
	    return null;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebSite() {
		return web_site;
	}

	public void setWebSite(String webSite) {
		this.web_site = webSite;
	}
	
	public List<String> listPendingAprovalFlightRoutes(){
		List<String> routeNames = new ArrayList<String>();
		for (int i = 0; i < flight_routes.size(); i++) {
			if (flight_routes.get(i).getEstado() == RouteState.Ingresada) routeNames.add(flight_routes.get(i).getNombre());			
		}
		return routeNames;
	}
	public List<DTRutaDeVuelo> listAcceptedFlightRoutesDTO(){
		List<DTRutaDeVuelo> dtrvs = new ArrayList<DTRutaDeVuelo>();
		for (int i = 0; i < flight_routes.size(); i++) {
			if (flight_routes.get(i).getEstado() == RouteState.Confirmada) dtrvs.add(flight_routes.get(i).getData());			
		}
		return dtrvs;
	}
}
