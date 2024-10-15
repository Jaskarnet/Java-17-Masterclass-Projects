package net.jaskar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import net.jaskar.music.Artist;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        for (String envName : env.keySet()) {
            if (envName.contains("DEVUSER_PASS")) {
                configOverrides.put("jakarta.persistence.jdbc.password", env.get(envName));
            }
        }
        try (var sessionFactory =
                     Persistence.createEntityManagerFactory("net.jaskar.music",
                             configOverrides);
             EntityManager entityManager = sessionFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            transaction.begin();
            Artist artist = entityManager.find(Artist.class, 202);
            System.out.println(artist);
            artist.addAlbum("The Best of Muddy Waters");
            System.out.println(artist);
//            entityManager.remove(artist);
//            entityManager.persist(new Artist("Muddy Water"));;
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
