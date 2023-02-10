//package com.galaxe.logging;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//
//
//@Builder
//@Data
//@AllArgsConstructor
//
//public class LoggerMessage {
//
//	public LoggerMessage() {
//		// TODO Auto-generated constructor stub
//	}
//	private String className;
//	private String methodName;
//	private String methodArgs;
//	private Long elapsedTimeInMills;
//	private Long elapsedTimeInMicros;
//	private Object result;
//	@Override
//	public String toString() {
//		try {
//			return "LoggerMessage [className=" + className + ", methodName=" + methodName + ", elapsedTimeInMills="
//					+ elapsedTimeInMills + ", elapsedTimeInMicros=" + elapsedTimeInMicros + ", result=" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString( this.result) + "]";
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			return e.getMessage();
//		}
//	}
//	
//
//}
