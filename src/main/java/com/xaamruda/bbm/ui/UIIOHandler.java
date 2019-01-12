package com.xaamruda.bbm.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UIIOHandler {

    public UIIOHandler(){}

    @PostMapping("/BBM/OFFERS")
    public ResponseEntity sendToApp (@RequestBody String jsonEvents){
        Object content = jsonEvents;
        return new ResponseEntity(content, HttpStatus.OK);
    }

}
