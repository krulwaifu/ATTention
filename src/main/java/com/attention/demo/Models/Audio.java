package com.attention.demo.Models;

import javax.persistence.*;

@Entity
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    private String fileName;
    private String musicName;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Transcription transcription;

    public Audio(Long id, String file_name,String musicName, User user, Transcription transcription) {
        this.id = id;
        this.fileName = file_name;
        this.musicName = musicName;
        this.user = user;
        this.transcription = transcription;
    }
    public Audio( String file_name, User user,String music_name , Transcription transcription) {
        this.fileName = file_name;
        this.user = user;
        this.transcription = transcription;
        this.musicName = music_name;
    }
    public Audio( String file_name,String music_name, User user) {
        this.fileName = file_name;
        this.musicName = music_name;
        this.user = user;
    }

    public Audio() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Transcription getTranscription() {
        return transcription;
    }

    public void setTranscription(Transcription transcription) {
        this.transcription = transcription;
    }
}
