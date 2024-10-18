package net.jaskar.music;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song implements Comparable<Song> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private int songId;

    @Column(name = "track_number")
    private int trackNumber;

    @Column(name = "song_title")
    private String songTitle;

    public Song() {
    }

    public Song(int trackNumber, String songTitle) {
        this.trackNumber = trackNumber;
        this.songTitle = songTitle;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", trackNumber=" + trackNumber +
                ", songTitle='" + songTitle + '\'' +
                '}';
    }

    @Override
    public int compareTo(Song o) {
        return this.trackNumber - o.trackNumber;
    }
}
