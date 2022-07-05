import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.itinerant.entity.Localidad;

public class DbTest {

	public static void main(String[] args) {
		Localidad pueblo = new Localidad(46330, "Camporrobles", "Plana de Utiel-Requena", 1200/*, 
				"Mancomunidad del interior - Tierra del vino", null*/);
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ItinerantApp");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.persist(pueblo);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		
		System.out.println("Un objeto fue persistido");
	}

}
