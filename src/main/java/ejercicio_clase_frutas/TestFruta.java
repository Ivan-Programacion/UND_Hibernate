package ejercicio_clase_frutas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestFruta {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Configuration config = new Configuration();
		SessionFactory sessionFactory = config.configure().buildSessionFactory();
		Session miSesion = sessionFactory.openSession();

//		System.out.println(miSesion.isConnected() ? "Conexión realizada" : "Conexión fallida");
		menuFrutas(sc, miSesion);
		System.out.println("Saliendo...");
		miSesion.close();
		sessionFactory.close();
		sc.close();
	}

	private static void menuFrutas(Scanner sc, Session miSesion) {
		int opcionUsuario = 0;
		while (opcionUsuario != 6) {
			if (opcionUsuario != 2) {
				listarFrutas(miSesion);
				System.out.println();
			}
			System.out.println("Selecciona una opcion (escribe el número correspondiente):");
			System.out.println("1. Añadir fruta");
			System.out.println("2. Ver lista de frutas");
			System.out.println("3. Modificar fruta");
			System.out.println("4. Eliminar fruta");
			System.out.println("5. Vaciar lista de frutas");
			System.out.println("6. Salir");
			opcionUsuario = sc.nextInt();
			System.out.println();
			opciones(sc, opcionUsuario, miSesion);
			if (opcionUsuario != 6)
				System.out.println();
		}
	}

	private static void opciones(Scanner sc, int opcionUsuario, Session miSesion) {
		Transaction transaction = null;
		transaction = miSesion.beginTransaction();
		if (opcionUsuario == 1)
			pedirFruta(sc, miSesion);
		else if (opcionUsuario == 2)
			listarFrutas(miSesion);
		else if (opcionUsuario == 3)
			modificarDatos(sc, miSesion);
		else if (opcionUsuario == 4)
			eliminarFruta(sc, miSesion);
		else if (opcionUsuario == 5)
			eliminarListaFruta(sc, miSesion);
		transaction.commit();
	}

	private static void modificarDatos(Scanner sc, Session miSesion) {
		Long id = 0L;
		System.out.println("Escribe el ID de la fruta que quieres modificar:");
		id = sc.nextLong();
		Fruta frutaSeleccionada = miSesion.find(Fruta.class, id);
		if (frutaSeleccionada != null) {
			System.out.println("Fruta seleccionada:");
			System.out.printf("ID: %d | Fruta: %s | Peso: %d | Número color: %d | Estado: %s",
					frutaSeleccionada.getId(), frutaSeleccionada.getNombre(), frutaSeleccionada.getPeso(),
					frutaSeleccionada.getColor(), frutaSeleccionada.isBuenAspecto() ? "Buen estado" : "Mal estado");
			System.out.println("Cambiar datos:");
			System.out.println("Fruta:");
			frutaSeleccionada.setNombre(sc.next());
			System.out.println("Peso:");
			frutaSeleccionada.setPeso(sc.nextInt());
			System.out.println("Número de color:");
			frutaSeleccionada.setColor(sc.nextInt());
			System.out.println("Tiene buen aspecto? (Seleccione una opción)");
			System.out.println("1. Sí");
			System.out.println("2. No");
			int respuesta = sc.nextInt();
			frutaSeleccionada.setBuenAspecto(respuesta == 1 ? true : false);
			System.out.println("Modificando fruta de la lista...");
			miSesion.merge(frutaSeleccionada);
			System.out.println("Fruta modificada con éxtio");
		}
	}

	private static void eliminarListaFruta(Scanner sc, Session miSesion) {
		int respuesta = 0;
		List<Fruta> lista = miSesion.createQuery("from Fruta", Fruta.class).list();
		System.out.println("Estás seguro de que queires eliminar toda la lista?");
		System.out.println("1. Sí");
		System.out.println("2. No");
		respuesta = sc.nextInt();
		if (respuesta == 1) {
			System.out.println("Eliminando lista entera...");
			for (Fruta fruta : lista)
				miSesion.remove(fruta);
			System.out.println("Lista eliminada");
		} else
			System.out.println("Volviendo al menú...");
	}

	private static void eliminarFruta(Scanner sc, Session miSesion) {
		String respuesta = "";
		Long id = 0L;
		System.out.println("Escribe la fruta que quieras eliminar:");
		respuesta = sc.next();
		List<Fruta> listaFrutas = miSesion.createQuery("from Fruta", Fruta.class).list();
		List<Fruta> listaFrutasEncontrada = new ArrayList<>();
		for (Fruta fruta : listaFrutas)
			if (fruta.getNombre().equalsIgnoreCase(respuesta))
				listaFrutasEncontrada.add(fruta);
		if (listaFrutasEncontrada.size() > 1) {
			System.out.println("Fruta " + respuesta + " encontradas:");
			for (Fruta fruta : listaFrutasEncontrada)
				System.out.println("ID: " + fruta.getId() + " | " + "Peso: " + fruta.getPeso() + " | "
						+ "Número color: " + fruta.getColor() + " | " + "Estado: "
						+ (fruta.isBuenAspecto() ? "Buen estado" : "Mal estado"));
			System.out.println("Escribe el id de quien quieras eliminar:");
			id = sc.nextLong();
		} else
			for (Fruta fruta : listaFrutasEncontrada)
				id = fruta.getId();
		System.out.println("Eliminando " + respuesta + " con ID " + id + "...");
		for (Fruta fruta : listaFrutasEncontrada)
			if (fruta.getId() == id)
				miSesion.remove(fruta);
		System.out.println("Fruta eliminada");

	}

	private static void listarFrutas(Session miSesion) {
		System.out.println("LISTA FRUTAS");
		List<Fruta> listaFrutas = miSesion.createQuery("from Fruta", Fruta.class).list();
		if (listaFrutas.isEmpty()) {
			System.out.println("(vacío)");
		} else {
			for (Fruta fruta : listaFrutas) {
				System.out.printf("ID: %d | Fruta: %s | Peso: %d | Número color: %d | Estado: %s | Bicho: %s",
						fruta.getId(), fruta.getNombre(), fruta.getPeso(), fruta.getColor(),
						fruta.isBuenAspecto() ? "Buen estado" : "Mal estado",
						fruta.getBicho() != null ? fruta.getBicho().getTipo() : "No tiene");
				System.out.println();
			}
		}
	}

	private static void pedirFruta(Scanner sc, Session miSesion) {
		Fruta nuevaFruta = new Fruta();
		System.out.println("Fruta:");
		nuevaFruta.setNombre(sc.next());
		System.out.println("Peso:");
		nuevaFruta.setPeso(sc.nextInt());
		System.out.println("Número de color:");
		nuevaFruta.setColor(sc.nextInt());
		System.out.println("Tiene buen aspecto? (Seleccione una opción)");
		System.out.println("1. Sí");
		System.out.println("2. No");
		int respuesta = sc.nextInt();
		nuevaFruta.setBuenAspecto(respuesta == 1 ? true : false);
		// Añadiendo Bicho
		System.out.println("La fruta tiene bicho?");
		System.out.println("1. Sí");
		System.out.println("2. No");
		respuesta = sc.nextInt();
		Bicho bicho;
		if (respuesta == 1) {
			bicho = new Bicho();
			System.out.println("Tipo de bicho:");
			sc.nextLine();
			String respuesta2 = sc.nextLine();
			bicho.setTipo(respuesta2);
			System.out.println("Tamaño:");
			respuesta = sc.nextInt();
			bicho.setTamanio(respuesta);
			System.out.println("Está vivo?");
			System.out.println("1. Sí");
			System.out.println("2. No");
			respuesta = sc.nextInt();
			bicho.setVivo(respuesta == 1 ? true : false);
		} else
			bicho = null;
		nuevaFruta.setBicho(bicho);
		System.out.println("Añadiendo fruta a la lista...");
		miSesion.persist(nuevaFruta);
		if (bicho != null)
			miSesion.persist(bicho);
		System.out.println("Fruta añadida con éxtio");
	}
}
