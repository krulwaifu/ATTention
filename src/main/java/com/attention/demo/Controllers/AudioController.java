package com.attention.demo.Controllers;

import com.attention.demo.Models.Audio;
import com.attention.demo.Models.User;
import com.attention.demo.Repository.IAudioRepo;
import com.attention.demo.Repository.IUserRepo;
import com.attention.demo.Services.AudioFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AudioController {
    @Autowired
    AudioFileService storageService;
    @Autowired
    IAudioRepo audioRepository;
    @Autowired
    IUserRepo userRepository;
    @PostMapping("/upload")
    public String uploadFile(@RequestParam(name = "file") MultipartFile file,
                             @RequestParam(name = "music_name") String music_name,
                             Model model, Authentication authentication) {
        try {
            User currentUser = userRepository.findByUsername(authentication.getName());
            Audio audio = new Audio(file.getOriginalFilename(),music_name,currentUser);
            Audio isExist = audioRepository.findByMusicNameOrFileName(music_name,file.getOriginalFilename());
            if(isExist != null){
                model.addAttribute("error","Such audio name or file name already exists!");
                return "upload";
            }
            storageService.save(file);
            model.addAttribute("success","Uploaded the file successfully: " + file.getOriginalFilename());
            return "upload";
        } catch (Exception e) {
            model.addAttribute("error","Could not upload the file: " + file.getOriginalFilename());
            return "upload";
        }
    }

    @GetMapping("/files")
    public String getListFiles(Model model) {
        List<MultipartFile> files= new ArrayList<>();
        List<Audio> audioList = audioRepository.findAll();
        for (Audio audio : audioList) {
            MultipartFile file = (MultipartFile) storageService.load(audio.getFileName());
            files.add(file);
        }
        model.addAttribute("fileList",files);
        model.addAttribute("audioList",audioList);
        return "files";
    }

    @GetMapping("/files/{music_name}")
    public String getFile(@PathVariable String music_name, Model model) {

        MultipartFile file = (MultipartFile) storageService.load(audioRepository.findByMusicName(music_name).getFileName());
        model.addAttribute("file",file);
        return "files/filename";
    }
}
