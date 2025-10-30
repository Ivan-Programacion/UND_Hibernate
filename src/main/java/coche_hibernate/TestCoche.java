package coche_hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestCoche {

	public static void main(String[] args) {
		// Creamos la transacción por la que daremos persistencia a los datos
		Transaction transaction = null;
		// Creamos la conexión
		Configuration configuracion = new Configuration();
		SessionFactory sesionFactory = configuracion.configure().buildSessionFactory();
		// Abrimos la sesión
		Session miSesion = sesionFactory.openSession();
		System.out.println((miSesion.isConnected() ? "Conexión completada" : "Conexión fallida"));
		// La ejecutamos/empezamos
		transaction = miSesion.beginTransaction();
		// CREAR COCHE
		crearCoche(miSesion);
		// BUSCAR COCHE
		buscarCoche(miSesion);
		// MERGE
		accionMerge(miSesion);
		// LISTAMOS COCHES
		listarCoches(miSesion);
		// Hacemos commit para que guarde los datos definitivamente
		transaction.commit();
		// Cerramos recursos
		miSesion.close();
		sesionFactory.close();
	}

	private static void listarCoches(Session miSesion) {
		// Creamos una lista de coches que, mediante el createQuery en HQL, llamamos a
		// todos los coches, indicandole la entidad (Coche.class) y la transformamos en
		// una lista con list()
		List<Coche> coches = miSesion.createQuery("from Coche", Coche.class).list();
		for (Coche coche : coches) {
			System.out.println(coche.getMarca() + " - " + coche.getMatricula());
//			miSesion.remove(coche); // Elimianr todos los coches de la BD
		}
	}

	private static void accionMerge(Session miSesion) {
		// Creamos coche
		Coche cocheMerge = new Coche();
		// Cambiamos el valor del ID a un ID EXISTENTE en la base de datos.
		// Si dicho ID no existe, saltará excepción
		// Se puede realizar merge sin hacer un setID y actuará como un persist
		// Si existe el ID, cambiará los datos de dicho registro (UPDATE)
		cocheMerge.setId(1L);
		cocheMerge.setMatricula("0");
		cocheMerge.setMarca("H");
		cocheMerge.setModelo("j");
		cocheMerge.setPotencia(500);
		miSesion.merge(cocheMerge);
	}

	private static void buscarCoche(Session miSesion) {
		// El id del objeto que tenemos en la BD
		long cocheID = 1;
		// Creamos un coche nuevo buscándolo desder la BD. Indicamos la clase (entidad)
		// y el id por parámetro de find()
		Coche buscarCoche = miSesion.find(Coche.class, cocheID);
		if (buscarCoche != null) {
			System.out.println(buscarCoche);
			// Cambiamos valores
			buscarCoche.setPotencia(500);
			buscarCoche.setMarca("BMW");
			// Realizamos persist() y commit()
			miSesion.persist(buscarCoche);
			// Podemos eliminar un objeto de la BD buscado previamente (find)
//			miSesion.remove(buscarCoche);
		} else
			System.out.println("COCHE NO ENCONTRADO");
	}

	private static void crearCoche(Session miSesion) {
		// Creamos nuestro coche y añadimos los valores de los atributos
		Coche c1 = new Coche();
		c1.setMatricula("7171DJF");
		c1.setMarca("Honda");
		c1.setModelo("Jazz");
		c1.setPotencia(90);
		// Añadimos el objeto nuevo a la BD
		miSesion.persist(c1);
	}

}
