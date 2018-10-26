package com.xaamruda.bbm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@SuppressWarnings({ "rawtypes"})
public interface IWebServiceController {
	
	public ResponseEntity usersEntryPoint(@RequestBody String jsonEvents);
	public ResponseEntity offersEntryPoint(@RequestBody String jsonEvents);
	
}

