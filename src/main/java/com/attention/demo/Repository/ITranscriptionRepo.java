package com.attention.demo.Repository;

import com.attention.demo.Models.Transcription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITranscriptionRepo extends JpaRepository<Transcription,Long> {
    Transcription getTranscriptionById(Long id);
    Transcription getTranscriptionByAudio_Id(Long id);
}
