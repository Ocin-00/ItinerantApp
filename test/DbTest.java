import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.itinerant.entity.LocalidadAvant;

public class DbTest {

	public static void main(String[] args) {
		LocalidadAvant pueblo = new LocalidadAvant();
		pueblo.setCodPostal(46300);
		pueblo.setNombre("Utiel");
		pueblo.setComarca("Plana de Utiel-Requena");
		pueblo.setPoblacion(11000);
		pueblo.setMancomunidad("Mancomunidad del interior - Tierra del vino");
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
