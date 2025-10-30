package coche_hibernate;

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
		// Creamos nuestro coche y añadimos los valores de los atributos

		// ----------------------- CREAR COCHE ----------------------- //
//		Coche c1 = new Coche();
//		c1.setMatricula("8550JDJ");
//		c1.setMarca("Nissan");
//		c1.setModelo("Quasquai");
//		c1.setPotencia(140);
//		// Añadimos el objeto nuevo a la BD
//		miSesion.persist(c1);
//		// Hacemos commit para que guarde los datos definitivamente
//		transaction.commit();
		// ----------------------- BUSCAR COCHE ----------------------- //
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
			transaction.commit();
		} else
			System.out.println("COCHE NO ENCONTRADO");
		// Cerramos recursos
		miSesion.close();
		sesionFactory.close();
	}

}
