import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

	public static void main(String[] args) {

		// Para realizar la conexión con la BD con Hibernate
		// Los imports desde org.hibernate
		Configuration configuracion = new Configuration();
		SessionFactory sesionFactory = configuracion.configure().buildSessionFactory();
		Session miSesion = sesionFactory.openSession();
		System.out.println((miSesion.isConnected() ? "Conexión completada" : "Conexión fallida"));
		miSesion.close();
		sesionFactory.close();
		
//		 miSesion.persist(object) --> Guarda un objeto en la BD (INSERT)
//		 miSesion.find(clase entityType, Object id) --> Encuentra el objeto (SELECT)
//		 miSesion.remove(object) --> Elimina un objeto (DELETE)
//		 miSesion.merge(X object) --> como un UPDATE
//		 miSesion.createQuery() --> Crear query
//		 miSesion.flush() --> Es como commit()
//		 miSesion.evict(object) --> corta la sincronización entre BD y un objeto
		 
	}

}
