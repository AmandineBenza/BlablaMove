package com.xaamruda.bbm.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@SuppressWarnings({ "rawtypes"})
public interface IWebServiceController {
	
	public ResponseEntity usersEntryPoint(@RequestBody String jsonEvents, HttpServletRequest request);
	public ResponseEntity offersEntryPoint(@RequestBody String jsonEvents, HttpServletRequest request);
	public ResponseEntity adminEntryPoint(@RequestBody String jsonEvents, HttpServletRequest request) throws IOException;
	
}

