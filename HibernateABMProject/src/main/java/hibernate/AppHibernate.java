
package hibernate;

import hibernateDao.PersonaDAO;
import hibernateDao.VentaDAO;
import hibernateDto.PersonaEntity;
import hibernateDto.VentaEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

public class AppHibernate {

	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Scanner sc = new Scanner(System.in);
		int opcion = mostrarMenu(sc);

		while (opcion != 0) {

			switch (opcion) {

			case 1:

				darAlta(sc);

				break;

			case 2:

				modificar(sc);

				break;

			case 3:

				darBaja(sc);

				break;

			case 4:

				mostrarLista();

				break;

			// FALTA AGREGAR case :buscar EnLista(sc);

			case 5:

				generarVenta(sc);

				break;

			case 6:
				buscarVenta(sc);
				break;

			case 0:

				break;

			default:

				System.out.println("Opcion incorrecta");

				break;

			}
			opcion = mostrarMenu(sc);
		}

	}

	// ------------------------MOSTRAR MENU-----------------------------

	private static int mostrarMenu(Scanner sc) {

		System.out.println("BIENVENIDO AL SISTEMA DE PERSONAS (ABM)");
		System.out.println("=========================");
		System.out.println("¿QUE DESEA HACER EN EL SISTEMA");
		System.out.println("");

		System.out.println("1: DAR DE ALTA ");
		System.out.println("2: MODIFICAR ");
		System.out.println("3: DAR DE BAJA");
		System.out.println("4: MOSTRAR LISTADO");
		System.out.println("5: GENERAR VENTA");
		System.out.println("6: BUSCAR VENTA");
		System.out.println("0: SALIR DEL SISTEMA");

		int opcion = 0;

		opcion = sc.nextInt();

		return opcion;

	}

	// ---------------------------DAR ALTA------------------------------------

	private static void darAlta(Scanner sc) {

		
		System.out.println("=====ALTA DE PERSONA =====");
		System.out.println("");
		System.out.println("Ingrese NOMBRE");

		try {
			PersonaEntity perEn = new PersonaEntity();
			String nombre = sc.next();
			perEn.setNombre(nombre);
			System.out.println("Ingrese FECHA DE NACIMIENTO  AAAA-MM-DD");
			String fechaNacimiento = sc.next();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date fechaNac = sdf.parse(fechaNacimiento);
			int edad = calcularEdad(fechaNac);
			perEn.setFechaNacimiento(fechaNac);
			perEn.setEdad(edad);
			PersonaDAO perDao = new PersonaDAO();
			perDao.insertPersona(perEn);

		} catch (ParseException e1) {
			e1.printStackTrace();
			System.out.println("Error en la fecha de nacimiento");
		}

		System.out.println("Usuario agregado con exito");
		System.out.println("==========================");
		System.out.println("¿QUE DESEA HACER EN EL SISTEMA)");
		System.out.println("1.Agregar otra persona");
		System.out.println("2.Mostrar mas opciones");
		System.out.println("0.Salir del sistema");
		int opcion = sc.nextInt();

		switch (opcion) {
		case 1:
			darAlta(sc);
			break;

		case 2:
			mostrarMasOpciones(sc);

		case 0:
			System.out.println("GRACIAS POR USAR EL SISTEMA");
			break;

		default:
			System.out.println("OPCION INVALIDA");
			break;
		}

	}

	// ------------------------------MODIFICAR----------------------------------

	private static void modificar(Scanner sc) {

		PersonaDAO perD = new PersonaDAO();
		System.out.println("===MODIFICACION===");
		System.out.println("");
		System.out.println("Ingrese el numero de ID del usuario a modificar");
		int personaId = sc.nextInt();
		perD.getPersona(personaId);
		System.out.println("¿QUE DESEA MODIFICAR DEL USUARIO?");
		System.out.println("ingrese una opcion");
		System.out.println("1.NOMBRE");
		System.out.println("2.FECHA DE NACIMIENTO");
		System.out.println("3.AMBAS");

		PersonaEntity per = perD.getPersona(personaId);
		if (per == null) {
			System.out.println("El ID no existe elija una nuevo ID");
			modificar(sc);

		} else {

			int opcion = sc.nextInt();

			switch (opcion) {

			case 1:

				System.out.println("=MODIFICAR NOMBRE=");
				System.out.println("Ingrese nuevo nombre");
				String nuevoNombre = sc.next();
				per.setNombre(nuevoNombre);
				perD.insertPersona(per);

				System.out.println("Su modificacion ha sido realizada exitosamente");
				mostrarMasOpciones(sc);
				break;

			case 2:

				System.out.println("=MODIFICAR FECHA DE NACIMIENTOD=");
				System.out.println("Ingrese nueva fecha de nacimiento (formato: yyyy/MM/dd");

				try {

					String nuevaFecha = sc.next();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					Date fechaNac = sdf.parse(nuevaFecha);
					per.setFechaNacimiento(fechaNac);

					int edad = 0;
					edad = calcularEdad(fechaNac);
					per.setEdad(edad);
					PersonaDAO perDao = new PersonaDAO();
					perDao.insertPersona(per);

				} catch (ParseException e1) {
					e1.printStackTrace();
					System.out.println("Error en la fecha de nacimiento");
				}

				System.out.println("Su modificacion ha sido realizada exitosamente");
				mostrarMasOpciones(sc);

				break;

			case 3:

				System.out.println("=MODIFICAR NOMBRE Y FECHA DE NACIMIENTO=");
				System.out.println("Ingrese nuevo nombre");
				System.out.println("Ingrese nueva fecha de nacimiento YYYY/MM/DD");

				String nuevoNom = sc.next();
				String nuevaFechaN = sc.next();
				SimpleDateFormat sdf = new SimpleDateFormat(nuevaFechaN);
				per.setNombre(nuevoNom);

				try {

					Date nuevaFechaNac = sdf.parse(nuevaFechaN);
					per.setFechaNacimiento(nuevaFechaNac);

					int nuevaEdad = 0;

					nuevaEdad = calcularEdad(nuevaFechaNac);

					per.setEdad(nuevaEdad);
				}

				catch (ParseException e1) {
					e1.printStackTrace();
					System.out.println("Error en la modificacion");
				}

				PersonaDAO perDAO = new PersonaDAO();
				perDAO.insertPersona(per);
				System.out.println("Su modificacion ha sido realizada exitosamente");
				mostrarMasOpciones(sc);

			}
		}

	}

	// --------------------------DAR BAJA------------------------------

	private static void darBaja(Scanner sc) {

		PersonaDAO perD = new PersonaDAO();
		System.out.println("===DAR DE BAJA===");
		System.out.println("");
		System.out.println("Ingrese el numero de ID del usuario a dar de bajar");
		int nIde = sc.nextInt();
		PersonaEntity per = perD.getPersona(nIde);
		perD.deletePersona(per);

		System.out.println("Baja realizada con exito");
		System.out.println("==========================");
		System.out.println("¿QUE DESEA HACER EN EL SISTEMA)");
		System.out.println("1.Dar de baja otra persona");
		System.out.println("2.Mostrar mas opciones");
		System.out.println("0.Salir del sistema");
		int opcion = sc.nextInt();

		switch (opcion) {
		case 1:
			darBaja(sc);
			break;

		case 2:
			mostrarMasOpciones(sc);

		case 0:
			System.out.println("GRACIAS POR USAR EL SISTEMA");
			break;

		default:
			System.out.println("OPCION INVALIDA");
			break;

		}
	}

	// --------------------------MOSTRAR LISTA--------------------------

	private static void mostrarLista() {

		PersonaDAO perDAO = new PersonaDAO();
		System.out.println("=====LISTA=====");
		System.out.println("");
		System.out.println("ID===NOMBRE===EDAD===FECHA DE NACIMIENTO");
		perDAO.getAllPersona();
	}

	// ---------------GENERAR VENTA-----------

	private static void generarVenta(Scanner sc) {

		VentaEntity ven = new VentaEntity();
		PersonaDAO perD = new PersonaDAO();
		VentaDAO venDAO = new VentaDAO();
		System.out.println("=====VENTA=====");
		System.out.println("");
		System.out.println("Ingrese ID del usuario");
		int personaId = sc.nextInt();
		PersonaEntity per = perD.getPersona(personaId);
		ven.setPersona(per);

		System.out.println("Ingrese Importe");
		float importe = sc.nextFloat();
		ven.setImporte(importe);

		Date fechaVenta = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nFechaVenta = sdf.format(fechaVenta);

		ven.setFechaVenta(fechaVenta);

		venDAO.insertVenta(ven);

		System.out.println("Venta agregada con exito");
		System.out.println("==========================");
		System.out.println("¿QUE DESEA HACER EN EL SISTEMA)");
		System.out.println("1.Agregar otra persona");
		System.out.println("2.Mostrar mas opciones");
		System.out.println("0.Salir del sistema");
		int opcion = sc.nextInt();

		switch (opcion) {
		case 1:
			darAlta(sc);
			break;

		case 2:
			mostrarMasOpciones(sc);

		case 0:
			System.out.println("GRACIAS POR USAR EL SISTEMA");
			break;

		default:
			System.out.println("OPCION INVALIDA");
			break;
		}

	}

	// ---------------------BUSCAR VENTA--------------------

	private static void buscarVenta(Scanner sc) {

		System.out.println("=====VENTA=====");
		System.out.println("");
		System.out.println("TIPO DE BUSQUEDA: 1.Por Persona /2.Lista completa");
		int opcion = sc.nextInt();

		switch (opcion) {

	
		case 1:

			VentaDAO ventaDAO = new VentaDAO();
			VentaEntity ventaE = new VentaEntity();
			System.out.println("===VENTAS POR PERSONA===");
			System.out.println("    ");
			System.out.println("Ingrese ID de la persona");
			int idPersona = sc.nextInt();
	ventaDAO.getVentaPERSONA(idPersona);
	if (idPersona!=0) {
		System.out.println("IMPORTE----FECHA");
		System.out.println(  ventaE.getImporte() + "   " + ventaE.getFechaVenta() );


			break;
	}
			


		case 2:

			VentaDAO venDAO = new VentaDAO();
			System.out.println("LISTA DE VENTAS");
			System.out.println("  ");
			System.out.println("ID-----FECHA_VENTA----IMPORTE");
			
			List<VentaEntity> ventasLista = venDAO.getAllVenta();
			for (VentaEntity venE : ventasLista) {
				PersonaEntity per = venE.getPersona();
				int id = per.getPersonaId();
				String nombre = per.getNombre();
				System.out.println(
						venE.getVentaId() + " " + venE.getFechaVenta() + " " + venE.getImporte() + " " + id + " " + nombre);
				
				
			}

			break;

		}
	}

	// -------------------MENU MAS OPCIONES--------------------

	private static void mostrarMasOpciones(Scanner sc) {
		System.out.println("OTRAS OPCIONES");
		System.out.println("1.Volver al menu inicial");
		System.out.println("0.Salir del sistema");
		int opcion = sc.nextInt();

		switch (opcion) {
		case 1:

			mostrarMenu(sc);
			break;

		case 0:
			System.out.println("GRACIAS POR USAR EL SISTEMA");
			break;

		default:
			System.out.println("OPCION INVALIDA");
			break;

		}
	}

	// ------------------CALCULO DE EDAD----------------------

	private static int calcularEdad(Date fechaNac) {
		GregorianCalendar fc = new GregorianCalendar();
		GregorianCalendar hoy = new GregorianCalendar();

		fc.setTime(fechaNac);

		int anActual = hoy.get(Calendar.YEAR);
		int anNac = fc.get(Calendar.YEAR);

		int mesActual = hoy.get(Calendar.MONTH);
		int mesNac = fc.get(Calendar.MONTH);

		int diaActual = hoy.get(Calendar.DATE);
		int diaNac = fc.get(Calendar.DATE);

		int dif = anActual - anNac;

		if (mesActual < mesNac) {

			dif = dif - 1;

		}

		else {

			if (mesActual == mesNac && diaActual < diaNac) {

				dif = dif - 1;
			}
		}
		return dif;
	}

}
