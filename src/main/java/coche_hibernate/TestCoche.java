package coche_hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestCoche {

	public static void main(String[] args) {
		// Primero declaramos la transaccion por la que vamos a mandar las cosas a la BD
		// desde Hibernate
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
		Coche c1 = new Coche();
		c1.setMatricula("8550JDJ");
		c1.setMarca("Nissan");
		c1.setModelo("Quasquai");
		c1.setPotencia(140);
		// Añadimos el objeto nuevo a la BD
		miSesion.persist(c1);
		// Hacemos commit para que guarde los datos definitivamente
		transaction.commit();
		// Cerramos recursos
		miSesion.close();
		sesionFactory.close();
	}

}
