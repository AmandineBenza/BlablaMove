//package com.xaamruda.bbm.integrity.ddos;
//
//import java.util.Arrays;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.xaamruda.bbm.commons.communication.NetworkUtils;
//import com.xaamruda.bbm.users.model.User;
//
//@Service("userDetailsService")
//@Transactional
//public class MyUserDetailsService implements UserDetailsService {
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//  
//    @Autowired
//    private UserRepository userRepository;
//  
//    @Autowired
//    private RoleRepository roleRepository;
//  
//    @Autowired
//    private LoginAttemptService loginAttemptService;
//  
//    @Autowired
//    private HttpServletRequest request;
//  
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        String ip = NetworkUtils.getClientIP(request);
//        if (loginAttemptService.isBlocked(ip)) {
//            throw new RuntimeException("blocked");
//        }
//  
//        try {
//            User user = (User) userRepository.findByMail(email);
//            if (user == null) {
//                return new org.springframework.security.core.userdetails.User(
//                  " ", " ", true, true, true, true, 
//                  getAuthorities(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
//            }
//  
//            return new org.springframework.security.core.userdetails.User(
//              user.getMail(), user.getPassword(), user.isEnabled(), true, true, true, 
//              getAuthorities(user.getRoles()));
//            
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
