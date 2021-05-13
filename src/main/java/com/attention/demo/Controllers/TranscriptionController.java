package com.attention.demo.Controllers;

import com.attention.demo.Models.Audio;
import com.attention.demo.Models.Transcription;
import com.attention.demo.Repository.IAudioRepo;
import com.attention.demo.Repository.ITranscriptionRepo;
import org.apache.catalina.realm.SecretKeyCredentialHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TranscriptionController {
    @Autowired
    ITranscriptionRepo transcriptionRepository;
    IAudioRepo audioRepository;
    SecretKeyCredentialHandler secretKeyCredentialHandler;
    SpeechSettings
    @PostMapping("/transcription/{id}")
    String Convert(@PathVariable Long id){

        String file_name = audioRepository.getAudioById(id).getFileName();
        String file_url = "C:\\Users\\super\\IdeaProjects\\ATTention\\src\\main\\resources\\uploads\\"+file_name;

        return "";
    }
    @PostMapping("/transcription/edit/{id}")
    String EditText(@RequestParam(name = "text") String new_text, @PathVariable Long id, Model model){
        Transcription transcriptionForm = transcriptionRepository.getTranscriptionByAudio_Id(id);
        transcriptionForm.setText(new_text);
        model.addAttribute("message","You have been registered!");
        return "transcription";
    }

}
