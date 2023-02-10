//package com.galaxe.logging;
//
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AppPointCuts {
//
//	public AppPointCuts() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
//	public void controllerPointCut() {
//		// No-OP
//	}
//
//	@Pointcut("within(@org.springframework.stereotype.Service *)")
//	public void servicePointCut() {
//		// No-OP
//	}
//
//	@Pointcut("within(@org.springframework.stereotype.Repository *)")
//	public void repositoryPointCut() {
//		// No-OP
//	}
//
//	@Pointcut("controllerPointCut() || servicePointCut()|| repositoryPointCut()")
//	public void mainPointCut() {
//
//	}
//
//}
