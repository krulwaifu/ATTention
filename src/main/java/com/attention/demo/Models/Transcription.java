package com.attention.demo.Models;

import javax.persistence.*;

@Entity
public class Transcription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    private String text;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Audio audio;
    public Transcription(){

    }
    public Transcription(Long id, String text) {
        this.id = id;
        this.text = text;
    }
    public Transcription(String text) {
        this.text = text;
    }
    public Transcription(String text,Audio audio) {
        this.text = text;
        this.audio = audio;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }
}
