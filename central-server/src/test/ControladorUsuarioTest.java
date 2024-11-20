package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import excepciones.CategoriaRepetidaException;
import excepciones.CiudadRepetidaException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteYaCompradoException;
import excepciones.PaqueteYaExisteException;
import excepciones.ReservaNoExisteException;
import excepciones.RutaDeVueloRepetidaException;
import excepciones.UsuarioNoEsAerolineaExcepcion;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRepetidoException;
import factory.Fabrica;
import model.*;
import datatype.*;
import service.*;

public class ControladorUsuarioTest {
	private static IControladorRutaDeVuelo controladorRutaDeVuelo;
	private static IControladorUsuario controladorUsuario;
	private static IControladorCiudadCategoria controladorCiudadCategoria;
	private static IControladorPaquete controladorPaquete;
	
	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance();
		controladorRutaDeVuelo = fabrica.getIControladorRutaDeVuelo();
		controladorUsuario = fabrica.getIControladorUsuario();
		controladorCiudadCategoria = fabrica.getIControladorCiudadCategoria();
		controladorPaquete = fabrica.getIControladorPaquete();
		String nickName = "c1";
        String nombre = "Cliente";
        String apellido = "Prueba";
        String email = "prueba@paraeditar.com";
        LocalDate nacimiento = LocalDate.of(1990, 1, 1);
        String nacionalidad = "Uruguaya";
        DocumentType tipoDoc = DocumentType.CedulaIdentidad;
        String numDoc = "18888888";
        try {
        	controladorUsuario.altaCliente(nickName, nombre, email, "", apellido, nacimiento, nacionalidad, tipoDoc, numDoc, "");
        }catch(Exception e){}
        
        nickName = "ae";
        nombre = "S";
        email = "sfew@aa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        try {
        	controladorUsuario.altaAereolinea(nickName, nombre, email, "", descripcion, sitioWeb, "");
        }catch(Exception e){}
        
	}

	@Test
    void testAltaCliente() {
        String nickName = "anuel";
        String nombre = "Anuel";
        String apellido = "AA";
        String email = "anuel.aa@rhlm.com";
        LocalDate nacimiento = LocalDate.of(1990, 1, 1);
        String nacionalidad = "Uruguaya";
        DocumentType tipoDoc = DocumentType.CedulaIdentidad;
        String numDoc = "88888888";

        try {
            controladorUsuario.altaCliente(nickName, nombre, email, "", apellido, nacimiento, nacionalidad, tipoDoc, numDoc, "");
            assertNotNull(controladorUsuario.obtenerInfoUsuario(nickName));
        } catch (UsuarioRepetidoException e) {
            fail("Usuario ya registrado");
        } catch (UsuarioNoExisteException e) {
            fail("Usuario no registrado correctamente");
        }
    }
	
	@Test
    void testAltaAereolinea() {
        String nickName = "AA";
        String nombre = "Aerolínea AA";
        String email = "aerolinea@aa.com";
        String descripcion = "AA";
        String sitioWeb = "https://www.aerolineaaa.com";

        try {
            controladorUsuario.altaAereolinea(nickName, nombre, email, "", descripcion, sitioWeb, "");
            assertNotNull(controladorUsuario.obtenerInfoUsuario(nickName));
        } catch (UsuarioRepetidoException e) {
            fail("Usuario ya registrado");
        } catch (UsuarioNoExisteException e) {
            fail("Usuario no registrado correctamente");
        }
    }
	
	@Test
    void testObtenerInfoUsuarioCliente() {
    	String nickcLiente = "c1";
    	try {
            DTUsuario user = controladorUsuario.obtenerInfoUsuario(nickcLiente);
            DTCliente cliente = (DTCliente) user; 

            assertEquals("c1", cliente.getNickname());
            assertEquals("Cliente", cliente.getNombre());
            assertEquals("Prueba", cliente.getApellido());
            assertEquals("prueba@paraeditar.com", cliente.getEmail());
            assertEquals(LocalDate.of(1990, 1, 1), cliente.getNacimiento());
            assertEquals("Uruguaya", cliente.getNacionalidad());
            assertEquals(DocumentType.CedulaIdentidad, cliente.getTipoDocumento());
            assertEquals("18888888", cliente.getNumDocumento());
  
    	} catch (UsuarioNoExisteException e) {
            fail("Informacion del usuario incorrecta");
        }
    }
    @Test
    void testObtenerInfoUsuarioAerolinea() {
    	String nickAerolinea = "ae";
    	try {
       
            DTUsuario user = controladorUsuario.obtenerInfoUsuario(nickAerolinea);
            DTAerolinea aerolinea = (DTAerolinea) user; 

            assertEquals("ae", aerolinea.getNickname()); 
            assertEquals("S", aerolinea.getNombre());
            assertEquals("sfew@aa.com", aerolinea.getEmail());
            assertEquals("Viajes", aerolinea.getDescripcion());
            assertEquals("https://www.s.com", aerolinea.getSitioWeb());
            
    	} catch (UsuarioNoExisteException e) {
            fail("Informacion del usuario incorrecta");
        }
    }
	
	@Test
    void testEditarDatosCliente() {
        String nickName = "c1";
        String nuevoNombre = "Lucas";
        String nuevoApellido = "Sugo";
        LocalDate nuevaFecha = LocalDate.of(1990, 2, 2);
        String nuevaNacionalidad = "Uruguaya";
        DocumentType nuevoTipoDoc = DocumentType.Pasaporte;
        String nuevoNumDoc = "87654321";

        try {
            controladorUsuario.editarDatosCliente(nickName, nuevoNombre, nuevoApellido, nuevoNumDoc, nuevoNumDoc, nuevaFecha, nuevaNacionalidad, nuevoTipoDoc, nuevoNumDoc);
            DTCliente usuario = (DTCliente) controladorUsuario.obtenerInfoUsuario(nickName);
            assertEquals(nuevaNacionalidad, usuario.getNacionalidad());
            assertEquals(nuevoApellido, usuario.getApellido());
            assertEquals(nuevoNombre, usuario.getNombre());
        } catch (UsuarioNoExisteException e) {
            fail("Usuario no existe para editar");
        }
    }

    @Test
    void testEditarDatosAereolinea() {
        String nickName = "ae";
        String nuevoNombre = "Editada";
        String nuevaDescripcion = "Desc";
        String nuevoSitioWeb = "https://nueva.com";

        try {
            controladorUsuario.editarDatosAereolinea(nickName, nuevoNombre, nuevoSitioWeb, nuevoSitioWeb, nuevaDescripcion, nuevoSitioWeb);
            DTAerolinea aerolinea = (DTAerolinea) controladorUsuario.obtenerInfoUsuario(nickName);
            assertEquals(nuevoNombre, aerolinea.getNombre());
            assertEquals(nuevaDescripcion, aerolinea.getDescripcion());
        } catch (UsuarioNoExisteException e) {
            fail("Usuario no existe para editar");
        }
    }

    @Test
    void testListarClientes() {
        try {
            List<String> clientes = controladorUsuario.listarClientes();
            assertNotNull(clientes);
            assertTrue(clientes.size() > 0);
        } catch (UsuarioNoExisteException e) {
            fail("No se encontraron clientes");
        }
    }
    
    @Test
    void testListarClientesWeb() {
        try {
            String[] clientes = controladorUsuario.listarClientesWeb();
            assertNotNull(clientes);
            assertTrue(clientes.length > 0);
        } catch (UsuarioNoExisteException e) {
            fail("No se encontraron clientes");
        }
    }
    
    

    @Test
    void testListarAerolineas() {
        try {
            List<String> aerolineas = controladorUsuario.listarAerolineas();
            assertNotNull(aerolineas);
            assertTrue(aerolineas.size() > 0);
        } catch (UsuarioNoExisteException e) {
            fail("No se encontraron aerolíneas");
        }
    }
    
     @Test
    void testListarAerolineasDataWeb() {
        try {
            DTAerolinea[] aerolineas = controladorUsuario.listarAerolineasDataWeb();
            assertNotNull(aerolineas);
            assertTrue(aerolineas.length > 0);
        } catch (UsuarioNoExisteException e) {
            fail("No se encontraron aerolíneas");
        }
    }


    @Test
    void testListarUsuarios() {
        try {
            List<DTUsuario> usuarios = controladorUsuario.listarUsuarios();
            assertNotNull(usuarios);
            assertTrue(usuarios.size() > 0);
        } catch (UsuarioNoExisteException e) {
            fail("No se encontraron Usuarios");
        }
    }
    
    @Test
    void testListarUsuariosWeb() {
        try {
            DTUsuario[] usuarios = controladorUsuario.listarUsuariosWeb();
            assertNotNull(usuarios);
            assertTrue(usuarios.length > 0);
        } catch (UsuarioNoExisteException e) {
            fail("No se encontraron Usuarios");
        }
    }
    
    @Test
    void testlistarUsuariosNick() {
        try {
            List<String> usuarios = controladorUsuario.listarUsuariosNick();
            assertNotNull(usuarios);
            assertTrue(usuarios.size() > 0);
        } catch (UsuarioNoExisteException e) {
            fail("No se encontraron Usuarios");
        }
    }
    
    @Test
    void testlistarUsuariosNickWeb() {
        try {
            String[] usuarios = controladorUsuario.listarUsuariosNickWeb();
            assertNotNull(usuarios);
            assertTrue(usuarios.length > 0);
        } catch (UsuarioNoExisteException e) {
            fail("No se encontraron Usuarios");
        }
    }
    
    
    @Test
	void testListarPaquetesCompradosCliente() {
		
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nomRuta = "URY-MX";
		String nomAero = "aero5";
		Float costo = (float) 1;
		String pais = "BR";
		String ciudad = "Londres";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "cat7";
		cat[1]="cat5";
		String nombre1 = "Paquete5";
		String nombre2 = "Paquete8";
		String nomCliente = "Ung1d";
		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
			controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, nombre1, nomRuta, nomAero, fA);
			controladorUsuario.altaAereolinea(nomAero, "", "fsdfdsf", "", "", "", "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nomAero, nomRuta, "", "", LocalTime.NOON, costo, costo, costo, claveCiudad, claveCiudad, fA, cat, "","",0);
			controladorPaquete.crearPaqueteRutasDeVuelo(nombre1, "Vuelos de otono",d , 10, fA, "");
			controladorPaquete.crearPaqueteRutasDeVuelo(nombre2, "Vuelos de primavera",d , 10, fA, "");
			controladorPaquete.agregarRutaAPaquete(nomRuta, nombre2, TipoAsiento.TURISTA, 1);
		} catch (PaqueteYaExisteException | RutaDeVueloRepetidaException | UsuarioNoEsAerolineaExcepcion | UsuarioRepetidoException | CiudadRepetidaException | CategoriaRepetidaException e) {
			fail(e);
		}
		try {
			controladorUsuario.altaCliente(nomCliente, "", "fsdfgsge", "", "", fA, "", DocumentType.CedulaIdentidad, "", "");
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
		try {
			controladorUsuario.clienteCompraPaquete(nomCliente, nombre2);
		} catch (UsuarioNoExisteException | PaqueteNoExisteException | PaqueteYaCompradoException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
		List<DTPaquete> paquetes = null;
		try {
			paquetes = controladorUsuario.listarPaquetesCompradosCliente(nomCliente);
		} catch (UsuarioNoExisteException | PaqueteNoExisteException e) {
			fail(e.getMessage());
		}
		assert(paquetes.size()==1);
		
	}
    
    
    @Test
	void testListarPaquetesCompradosClienteWeb() {
		
		Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		String nomRuta = "URaY-MX";
		String nomAero = "aearo5";
		Float costo = (float) 1;
		String pais = "BaR";
		String ciudad = "Londres";
		String claveCiudad = pais+"-"+ciudad;
		String[] cat = new String[2];
		cat[0] = "caat7";
		cat[1]="cata5";
		String nombre1 = "Paqueate5";
		String nombre2 = "Paqueate8";
		String nomCliente = "Unga1d";
		try {
			controladorCiudadCategoria.agregarCategoria(cat[0]);
			controladorCiudadCategoria.agregarCategoria(cat[1]);
			controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, nombre1, nomRuta, nomAero, fA);
			controladorUsuario.altaAereolinea(nomAero, "", "fsdfdasf", "", "", "", "");
			controladorRutaDeVuelo.agregarRutaDeVuelo(nomAero, nomRuta, "", "", LocalTime.NOON, costo, costo, costo, claveCiudad, claveCiudad, fA, cat, "","",0);
			controladorPaquete.crearPaqueteRutasDeVuelo(nombre1, "Vuelos de otono",d , 10, fA, "");
			controladorPaquete.crearPaqueteRutasDeVuelo(nombre2, "Vuelos de primavera",d , 10, fA, "");
			controladorPaquete.agregarRutaAPaquete(nomRuta, nombre2, TipoAsiento.TURISTA, 1);
		} catch (PaqueteYaExisteException | RutaDeVueloRepetidaException | UsuarioNoEsAerolineaExcepcion | UsuarioRepetidoException | CiudadRepetidaException | CategoriaRepetidaException e) {
			fail(e);
		}
		try {
			controladorUsuario.altaCliente(nomCliente, "", "fsdfadgsge", "", "", fA, "", DocumentType.CedulaIdentidad, "", "");
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
		try {
			controladorUsuario.clienteCompraPaquete(nomCliente, nombre2);
		} catch (UsuarioNoExisteException | PaqueteNoExisteException | PaqueteYaCompradoException e) {
			// TODO Auto-generated catch block
			fail(e);
		}
		DTPaqueteWeb[] paquetes = null;
		try {
			paquetes = controladorUsuario.listarPaquetesCompradosClienteWeb(nomCliente);
		} catch (UsuarioNoExisteException | PaqueteNoExisteException e) {
			fail(e.getMessage());
		}
		assert(paquetes.length>0);
		
	}
    
    @Test
    void testCompraPaquetes() {
    	
    	String nombre1 = "Paquedsadte5";
    	Duration d;
		d = Duration.ofSeconds(36000);
		LocalDate fA = LocalDate.now();
		List<DTPaquete> paquetes = null;
    	
		try {
			controladorPaquete.crearPaqueteRutasDeVuelo(nombre1, "Vuelos de otono",d , 10, fA, "");
		} catch (PaqueteYaExisteException e) {
			fail(e.getMessage());
		}
		controladorUsuario.compraPaquetes("c1", nombre1, fA, fA, 0.0f);
		try {
			paquetes = controladorUsuario.listarPaquetesCompradosCliente("c1");
		} catch (UsuarioNoExisteException | PaqueteNoExisteException e) {
			fail(e.getMessage());
		}
		assert(paquetes.size()==1);

    }
    
    @Test
	void testListarReservasObjOK() {
		String nickNameU = "surd";
		String nickNameU2 = "sudre";
		String nickNameA = "sudra";
        String nombre = "S";
        String emailU = "sfedwq@aa.com";
        String emailU2 = "su@saa.com";
        String emailA = "sa@ada.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        
        String nombreR = "UXd39";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Chdina";
		String ciudad = "Shangai";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "UX3d9874";
		String nomVuelo1 = "UX3ddsa9874";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Gldobal";
		cat[1]="Locsal";
		
		ArrayList<DTPasaje> pas = new ArrayList<DTPasaje>();
		pas.add(new DTPasaje("Jogse", "gVarela",0));
		pas.add(new DTPasaje("Pgedro", "Picagpiedra",0));
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaCliente(nickNameU, nombre, emailU, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaCliente(nickNameU2, nombre, emailU2, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaAereolinea(nickNameA, nombre, emailA, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo1, fechaAlta, dur, 20, 1, fechaAlta, "");
        	
        	}catch(Exception e){
        		fail(e);
        	}
        	try {
		    	controladorRutaDeVuelo.reservarVuelo(nickNameU, nomVuelo, TipoAsiento.EJECUTIVO, 1, 1, pas, fechaAlta);
		    	controladorRutaDeVuelo.reservarVuelo(nickNameU, nomVuelo1, TipoAsiento.EJECUTIVO, 1, 1, pas, fechaAlta);
		    	controladorRutaDeVuelo.reservarVuelo(nickNameU2, nomVuelo, TipoAsiento.TURISTA, 1, 1, pas, fechaAlta);
        	} catch(Exception e) {
        		fail(e);
        	}
        	List<Booking> res = null;
			try {
				res = controladorUsuario.listarReservasClienteObj(nickNameU);
			} catch (UsuarioNoExisteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReservaNoExisteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	assert(!res.isEmpty());
    }
    
    @Test
	void testListarReservasClienteWebOK() {
		String nickNameU = "surad";
		String nickNameU2 = "sudare";
		String nickNameA = "sudraa";
        String nombre = "S";
        String emailU = "sfeadwq@aa.com";
        String emailU2 = "su@asaa.com";
        String emailA = "sa@adaa.com";
        String descripcion = "Viajes";
        String sitioWeb = "https://www.s.com";
        
        String nombreR = "UXda39";
		String descripcionRuta = "Tiempo de vuelo aproximado de 12 horas con comidas incluidas";
		LocalTime hora = LocalTime.parse("12:20", DateTimeFormatter.ofPattern("HH:MM"));
		Float costoTurista = (float) 450;
		Float costoEjecutivo = (float) 950;
		Float costoEquipajeExtra = (float) 50;
		LocalDate fechaAlta = LocalDate.parse("19/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String pais = "Chdaina";
		String ciudad = "Shaangai";
		String claveCiudad = pais+"-"+ciudad;
		
		String nomVuelo = "UX3da9874";
		String nomVuelo1 = "UX3dadsa9874";
		Duration dur = Duration.ZERO;
		
		String[] cat = new String[2];
		cat[0] = "Gldaobal";
		cat[1]="Locsaal";
		
		ArrayList<DTPasaje> pas = new ArrayList<DTPasaje>();
		pas.add(new DTPasaje("Jogase", "gVaarela",0));
		pas.add(new DTPasaje("Pgaedro", "Picaagpiedra",0));
        
		
        try {
        	controladorCiudadCategoria.agregarCategoria(cat[0]);
        	controladorCiudadCategoria.agregarCategoria(cat[1]);
        	controladorUsuario.altaCliente(nickNameU, nombre, emailU, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaCliente(nickNameU2, nombre, emailU2, "", descripcion, null, sitioWeb, null, sitioWeb, "");
        	controladorUsuario.altaAereolinea(nickNameA, nombre, emailA, "", descripcion, sitioWeb, "");
        	controladorCiudadCategoria.crearNuevaCiudad(pais, ciudad, claveCiudad, descripcionRuta, sitioWeb, fechaAlta);
			controladorRutaDeVuelo.agregarRutaDeVuelo(nickNameA,nombreR,descripcionRuta, "", hora,costoTurista, costoEjecutivo, costoEquipajeExtra, claveCiudad, claveCiudad, fechaAlta, cat, "","",0);
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo, fechaAlta, dur, 1, 1, fechaAlta, "");
        	controladorRutaDeVuelo.altaVuelo(nombreR, nomVuelo1, fechaAlta, dur, 20, 1, fechaAlta, "");
        	
        	}catch(Exception e){
        		fail(e);
        	}
        	try {
		    	controladorRutaDeVuelo.reservarVuelo(nickNameU, nomVuelo, TipoAsiento.EJECUTIVO, 1, 1, pas, fechaAlta);
		    	controladorRutaDeVuelo.reservarVuelo(nickNameU, nomVuelo1, TipoAsiento.EJECUTIVO, 1, 1, pas, fechaAlta);
		    	controladorRutaDeVuelo.reservarVuelo(nickNameU2, nomVuelo, TipoAsiento.TURISTA, 1, 1, pas, fechaAlta);
        	} catch(Exception e) {
        		fail(e);
        	}
        	DTReservaWeb[] res = null;
			try {
				res = controladorUsuario.listarReservasClienteWeb(nickNameU);
			} catch (UsuarioNoExisteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReservaNoExisteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	assert(res.length > 0);
    }
}
   
  

