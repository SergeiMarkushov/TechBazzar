package ru.bazzar.picture.util;

import org.springframework.stereotype.Service;

@Service
public class FileChecker {
    public boolean checkFile(String fileName){
        return !fileName.endsWith(".exe");
    }
}
