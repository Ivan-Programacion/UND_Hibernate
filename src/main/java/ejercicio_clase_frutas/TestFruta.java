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
	}

	private static void menuFrutas(Scanner sc, Session miSesion) {
		int opcionUsuario = 0;
		while (opcionUsuario != 5) {
			listarFrutas(miSesion);
			System.out.println();
			System.out.println("Selecciona una opcion (escribe el número correspondiente):");
			System.out.println("1. Añadir fruta");
			System.out.println("2. Ver lista de frutas");
//			System.out.println("3. Eliminar fruta");
//			System.out.println("4. Vaciar lista de frutas");
			System.out.println("5. Salir");
			opcionUsuario = sc.nextInt();
			System.out.println();
			opciones(sc, opcionUsuario, miSesion);
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
			eliminarFruta(sc, miSesion);
		else if (opcionUsuario == 4)
			eliminarListaFruta(miSesion);
		transaction.commit();
	}

	private static void eliminarListaFruta(Session miSesion) {

	}

	private static void eliminarFruta(Scanner sc, Session miSesion) {
		String respuesta = "";
		System.out.println("Escribe la fruta que quieras eliminar:");
		respuesta = sc.next();
		List<Fruta> listaFrutas = miSesion.createQuery("from Fruta", Fruta.class).list();
		List<Fruta> listaFrutasEncontrada = new ArrayList<>();
		for (Fruta fruta : listaFrutas)
			if (fruta.getNombre().equalsIgnoreCase(respuesta))
				listaFrutasEncontrada.add(fruta);
		if (listaFrutasEncontrada.size() > 1) {
			for (Fruta fruta : listaFrutasEncontrada)
				System.out.println(fruta.getId() + " - " + fruta.getPeso() + " - " + fruta.getColor() + " - "
						+ (fruta.isBuenAspecto() ? "Buen estado" : "Mal estado"));
		} else {

		}

	}

	private static void listarFrutas(Session miSesion) {
		System.out.println("LISTA FRUTAS");
		List<Fruta> listaFrutas = miSesion.createQuery("from Fruta", Fruta.class).list();
		if (listaFrutas.isEmpty())
			System.out.println("(vacío)");
		else {
			for (Fruta fruta : listaFrutas) {
				System.out.printf("Fruta: %s | Peso: %d | Número color: %d | Estado: %s", fruta.getNombre(),
						fruta.getPeso(), fruta.getColor(), fruta.isBuenAspecto() ? "Buen estado" : "Mal estado");
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
		System.out.println("Añadiendo fruta a la lista...");
		miSesion.persist(nuevaFruta);
		System.out.println("Fruta añadida con éxtio");
	}
}
