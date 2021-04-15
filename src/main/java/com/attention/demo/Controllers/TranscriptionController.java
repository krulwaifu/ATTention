package com.attention.demo.Controllers;

import com.attention.demo.Models.Audio;
import org.springframework.stereotype.Controller;

@Controller
public class TranscriptionController {
    String Convert(String file_name){
//converts audio to text
        return "";
    }
    String EditText(String text,String file_name){
//changes transcription by file_name in db
        return text;
    }

}
