package com.attention.demo.Repository;

import com.attention.demo.Models.Audio;
import com.attention.demo.Models.Transcription;
import com.attention.demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IAudioRepo extends JpaRepository<Audio,Long> {
    Audio getAudioByFile_name(String file_name);
    Audio getAudioById(Long id);
    Set<Audio> getAllByUser_Id(Long id);
}
