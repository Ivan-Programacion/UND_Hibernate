package practicando;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
	private static Transaction transaccion = null;
	private static Configuration configuracion;
	private static SessionFactory sessionFactory;
	private static Session miSesion = null;
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		conexion();
//		crearPersonaje();
		verPersonaje();
//		modifcarPersonaje();
		cerrarRecursos();
	}

	private static void modifcarPersonaje() {
		transaccion = miSesion.beginTransaction();
		System.out.println("Qué personaje quieres modificar? (escribe su ID):");
		boolean error = true;
		Long idSeleccionado = 0L;
		while (error) {
			while (!sc.hasNextInt()) {
				System.out.println("ERROR: Escribe un número correcto de ID:");
				sc.nextLine(); // Salto de línea
			}
			idSeleccionado = sc.nextLong();
			error = false;
			sc.nextLine(); // Salto de línea
		}
		Personaje personaje = miSesion.find(Personaje.class, idSeleccionado);
		if (personaje != null) {
			System.out.println("Escribe el nombre del personaje:");
			personaje.setNombre(sc.nextLine());
			System.out.println("Escribe el nivel de personaje:");
			while (!sc.hasNextInt()) {
				System.out.println("ERROR: Escribe un número que haga referencia al nivel:");
				sc.nextLine(); // Salto de línea
			}
			personaje.setNivel(sc.nextInt());
			System.out.println("Ataque del personaje (escribe valro correspondiente):");
			while (!sc.hasNextInt()) {
				System.out.println("ERROR: Escribe un número que haga referencia al ataque:");
				sc.nextLine(); // Salto de línea
			}
			personaje.setAtaque(sc.nextInt());
			System.out.println("Escribe el nivel de defensa del personaje:");
			while (!sc.hasNextInt()) {
				System.out.println("ERROR: Escribe un número que haga referencia a la defensa:");
				sc.nextLine(); // Salto de línea
			}
			personaje.setDefensa(sc.nextInt());
			System.out.println("Tiene arma inicial? (escribe el número correspondiente a la opción):");
			System.out.println("1. Sí");
			System.out.println("2. No");
			error = true;
			int opcion = 0;
			while (error) {
				while (!sc.hasNextInt()) {
					System.out.println("ERROR: Escribe un número correcto:");
					System.out.println("1. Sí");
					System.out.println("2. No");
					sc.nextLine(); // Salto de línea
				}
				opcion = sc.nextInt();
				if (opcion < 1 || opcion > 2) {
					System.out.println("ERROR: Escribe un número correcto:");
					System.out.println("1. Sí");
					System.out.println("2. No");
				} else
					error = false;
				sc.nextLine(); // Salto de línea
			}
			if (opcion == 1) {
				Arma arma = new Arma();
				System.out.println("Tipo de arma:");
				arma.setTipoArma(sc.nextLine());
				arma.setAtaque(5);
				personaje.setArma(arma);
			} else {
				if (personaje.getArma() != null)
					miSesion.remove(personaje.getArma());
				personaje.setArma(null);
			}
			miSesion.merge(personaje);
		} else
			System.out.println("El personaje NO existe");
		System.out.println("Fin modificación personajes");
		transaccion.commit();
	}

	private static void verPersonaje() {
		List<Personaje> lista = miSesion.createQuery("from Personaje", Personaje.class).list();
		for (Personaje personaje : lista) {
			System.out.printf("ID: %d -- Nombre: %s -- Daño: %d -- Defensa: %d -- Arma: %s", personaje.getId(),
					personaje.getNombre(), personaje.getAtaque(), personaje.getDefensa(),
					personaje.getArma() != null
							? personaje.getArma().getTipoArma() + " -- Daño arma: " + personaje.getArma().getAtaque()
							: "No tiene arma");
			System.out.println();
		}
	}

	private static void crearPersonaje() {
		transaccion = miSesion.beginTransaction();
		int numPersonajes = 0;
		System.out.println("Cuantos personajes quieres crear? (escribe un número):");
		while (!sc.hasNextInt()) {
			System.out.println("ERROR: Escriba un número:");
			sc.nextLine(); // Salto de línea
		}
		numPersonajes = sc.nextInt();
		sc.nextLine(); // Salto de línea
		for (int i = 1; i <= numPersonajes; i++) {
			Personaje personaje = new Personaje();
			System.out.println("Escribe el nombre del personaje " + i + ":");
			personaje.setNombre(sc.nextLine());
			personaje.setNivel(0);
			personaje.setAtaque(5);
			personaje.setDefensa(5);
			System.out.println("Tiene arma inicial? (escribe el número correspondiente a la opción):");
			System.out.println("1. Sí");
			System.out.println("2. No");
			boolean error = true;
			int opcion = 0;
			while (error) {
				while (!sc.hasNextInt()) {
					System.out.println("ERROR: Escribe un número correcto:");
					System.out.println("1. Sí");
					System.out.println("2. No");
					sc.nextLine(); // Salto de línea
				}
				opcion = sc.nextInt();
				if (opcion < 1 || opcion > 2) {
					System.out.println("ERROR: Escribe un número correcto:");
					System.out.println("1. Sí");
					System.out.println("2. No");
				} else
					error = false;
				sc.nextLine(); // Salto de línea
			}
			if (opcion == 1) {
				Arma arma = new Arma();
				System.out.println("Tipo de arma:");
				arma.setTipoArma(sc.nextLine());
				arma.setAtaque(5);
				personaje.setArma(arma);
			} else
				personaje.setArma(null);
			miSesion.persist(personaje);
		}
		System.out.println("Fin creacion personajes");
		transaccion.commit();
	}

	private static void conexion() {
		configuracion = new Configuration();
		sessionFactory = configuracion.configure().buildSessionFactory();
		miSesion = sessionFactory.openSession();
//		System.out.println(miSesion.isConnected());
	}

	private static void cerrarRecursos() {
		sc.close();
		miSesion.close();
		sessionFactory.close();
	}

}
