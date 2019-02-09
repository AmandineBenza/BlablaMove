package com.xaamruda.bbm.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xaamruda.bbm.chaos.ChaosManager;
import com.xaamruda.bbm.commons.communication.NetworkUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.communication.internal.FlowOrchestrationResult;
import com.xaamruda.bbm.communication.internal.IFlowOrchestrator;
import com.xaamruda.bbm.integrity.ddos.DDOSGuard;

@RestController
@RequestMapping("/BBM/")
@SuppressWarnings({"rawtypes", "unchecked"})
public class WebServiceController implements IWebServiceController {
	
	@Autowired
	private IFlowOrchestrator flowOrchestrator;
	
	@Autowired
	public ChaosManager chaosManager;
	
	@Autowired
	private DDOSGuard ddosChecker;
	
	public WebServiceController() {}
	
	
	/* Json format:
	 * 
	 * 	{
	 * 		"event" : "thing",
	 * 		"data" : { ... }
	 * 	}
	 */
	@CrossOrigin
	@Override
	@RequestMapping(value = "USERS", method = RequestMethod.POST)
	public ResponseEntity usersEntryPoint(@RequestBody String jsonEvents, HttpServletRequest request) {
		if(!ddosChecker.checkNewRequestAuthorization(request.getRemoteAddr())) {
			BBMLogger.infoln(request.getRemoteAddr() + " blocked by DDOS engine.");
			return new ResponseEntity("DDOS analyze prevent you from accessing BlablaMove.\n", HttpStatus.FORBIDDEN);
		}
		
		BBMLogger.infoln("------------------------------------");
		BBMLogger.infoln("Listened new event on \"BBM/USERS\".");
		BBMLogger.infoln("From " + NetworkUtils.getRemoteIpAddress(request));
		System.out.println(jsonEvents);
		FlowOrchestrationResult result = flowOrchestrator.orchestrateUsersEntryPoint(jsonEvents);
		BBMLogger.infoln("Response Received.");
		return new ResponseEntity(result.getContent(), result.getHttpStatus());
	}
	
	@CrossOrigin
	@Override
	@RequestMapping(value = "OFFERS", method = RequestMethod.POST)
	public ResponseEntity offersEntryPoint(@RequestBody String jsonEvents, HttpServletRequest request) {
		if(!ddosChecker.checkNewRequestAuthorization(request.getRemoteAddr())) {
			BBMLogger.infoln(request.getRemoteAddr() + " blocked by DDOS engine.");
			return new ResponseEntity("DDOS analyze prevent you from accessing BlablaMove.\n", HttpStatus.FORBIDDEN);
		}
		
		BBMLogger.infoln("------------------------------------");
		BBMLogger.infoln("Listened new event on \"BBM/OFFERS\".");
		BBMLogger.infoln("From " + NetworkUtils.getRemoteIpAddress(request));
		FlowOrchestrationResult result = flowOrchestrator.orchestrateOffersEntryPoint(jsonEvents);
		BBMLogger.infoln("Response received.");
		return new ResponseEntity(result.getContent(), result.getHttpStatus());
	}
	
	@CrossOrigin
	@Override
	@RequestMapping(value = "ADMIN", method = RequestMethod.POST)
	public ResponseEntity adminEntryPoint(@RequestBody String jsonEvents, HttpServletRequest request) throws IOException {
		// remove ? user should not be able to shoot admin entry
		if(!ddosChecker.checkNewRequestAuthorization(request.getRemoteAddr())) {
			BBMLogger.infoln(request.getRemoteAddr() + " blocked by DDOS engine.");
			return new ResponseEntity("DDOS analyze prevent you from accessing BlablaMove.\n", HttpStatus.FORBIDDEN);
		}
		
		BBMLogger.infoln("------------------------------------");
		BBMLogger.infoln("Listened new event on \"BBM/Admin\".");
		String result = chaosManager.handle(jsonEvents);
		chaosManager.lsPrint();
		BBMLogger.infoln("Response received.");
		
		return new ResponseEntity(result,HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "ADMIN", method = RequestMethod.GET)
	public String adminEntryPoint(HttpServletRequest request, Model model) throws IOException {
		// remove ? user should not be able to shoot admin entry
		if(!ddosChecker.checkNewRequestAuthorization(request.getRemoteAddr())) {
			BBMLogger.infoln(request.getRemoteAddr() + " blocked by DDOS engine.");
			return request.getRemoteAddr() + " blocked by DDOS engine.";
		}
		
		BBMLogger.infoln("------------------------------------");
		BBMLogger.infoln("Access to html admin page on \"BBM/Admin\".");
		model.addAttribute("x", "x");
		BBMLogger.infoln("Response received.");
		return "admin";
	}

}
