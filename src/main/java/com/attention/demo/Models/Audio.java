package com.attention.demo.Models;

import javax.persistence.*;

@Entity
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    private String file_name;
    private String music_name;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Transcription transcription;

    public Audio(Long id, String file_name, User user, Transcription transcription) {
        this.id = id;
        this.file_name = file_name;
        this.music_name = music_name;
        this.user = user;
        this.transcription = transcription;
    }
    public Audio( String file_name, User user, Transcription transcription) {
        this.file_name = file_name;
        this.user = user;
        this.transcription = transcription;
    }
    public Audio( String file_name,String music_name, User user) {
        this.file_name = file_name;
        this.music_name = music_name;
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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
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
