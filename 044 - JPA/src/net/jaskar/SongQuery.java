package net.jaskar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import net.jaskar.music.Album;
import net.jaskar.music.Artist;
import net.jaskar.music.Song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongQuery {
    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        for (String envName : env.keySet()) {
            if (envName.contains("DEVUSER_PASS")) {
                configOverrides.put("jakarta.persistence.jdbc.password", env.get(envName));
            }
        }
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "net.jaskar.music", configOverrides);
             EntityManager em = emf.createEntityManager()) {
            String dashedString = "-".repeat(19);
            String word = "Soul";
            var matchesJPQL = getMatchedSongsJPQL(em, "%" + word + "%");
            System.out.printf("%-30s %-65s %s%n", "Artist", "Album", "Song Title");
            System.out.printf("%1$-30s %1$-65s %1$s%n", dashedString);

            matchesJPQL.forEach(a -> {
                String artistName = a.getArtistName();
                a.getAlbums().forEach(b -> {
                    String albumName = b.getAlbumName();
                    b.getPlayList().forEach(s -> {
                        String songTitle = s.getSongTitle();
                        if (songTitle.contains(word)) {
                            System.out.printf("%-30s %-65s %s%n",
                                    artistName, albumName, songTitle);
                        }
                    });
                });
            });

            System.out.printf("%-30s %-65s %s%n", "Artist", "Album", "Song Title");
            System.out.printf("%1$-30s %1$-65s %1$s%n", dashedString);
            var matchesCriteriaQuery = getMatchedSongsCriteriaQuery(em, "%" + word + "%");
            matchesCriteriaQuery.forEach(m -> System.out.printf("%-30s %-65s %s%n", m[0], m[1], m[2]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Artist> getMatchedSongsJPQL(EntityManager em, String matchedValue) {
        String jpql = "SELECT ar FROM Artist ar JOIN albums a JOIN playList p WHERE p.songTitle LIKE :titleFragment";
        var query = em.createQuery(jpql, Artist.class);
        query.setParameter("titleFragment", matchedValue);
        return query.getResultList();
    }

    private static List<Object[]> getMatchedSongsCriteriaQuery(EntityManager em, String matchedValue) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

        Root<Artist> root = query.from(Artist.class);
        Join<Artist, Album> albumJoin = root.join("albums", JoinType.INNER);
        Join<Album, Song> songJoin = albumJoin.join("playList", JoinType.INNER);

        query
                .multiselect(root.get("artistName"),
                        albumJoin.get("albumName"),
                        songJoin.get("songTitle")
                )
                .where(builder.like(songJoin.get("songTitle"), matchedValue))
                .orderBy(builder.asc(root.get("artistName")));

        return em.createQuery(query).getResultList();
    }

}
