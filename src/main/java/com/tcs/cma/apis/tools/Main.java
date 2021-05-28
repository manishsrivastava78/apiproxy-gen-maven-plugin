package com.tcs.cma.apis.tools;

import java.io.BufferedReader;
import java.util.*;  
import java.io.*; 
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.converter.SwaggerConverter;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class Main {
	
	public static void main(String[] args) throws Exception{
		SwaggerConverter converter = new SwaggerConverter();
		String swagger  = readFile("C:\\manish\\cma-works\\testbuddy\\pet2.json", StandardCharsets.UTF_8);
		SwaggerParseResult results = converter.readContents(swagger, null, null);
		OpenAPI api = results.getOpenAPI();
		System.out.println(api.getInfo().getDescription());
	}
	
	private static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static void main1(String[] args) {
		Long startTime = System.currentTimeMillis();
		for(int i=0;i<1;i++) {
			genReport();
		}
		
		Long endTime = System.currentTimeMillis();
		
		System.out.println("Execution time of 100 API in seconds="+(endTime-startTime)/1000);
	}
	
	public static void createFile(String directoryPath ) {
		try {
			Properties p=new Properties();  
			p.setProperty("host","Sonoo Jaiswal");  
			p.setProperty("email","sonoojaiswal@javatpoint.com");  
			  
			p.store(new FileWriter(directoryPath+File.separatorChar+  "env"+File.separatorChar+"default"+File.separatorChar+"default.properties"),"Javatpoint Properties Example");  
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void createSpec(String directoryPath) {/*
		FormData formData  = new FormData();
		formData.setResourceTitle("Get tttt");
		formData.setBasePath("/commercial-companies/v1");
		formData.setResourcePath("/company");
		QueryParam q = new QueryParam();
		q.setParameterName("financialOptions");
		q.setParameterValue("All");
		List<QueryParam> list = new ArrayList<QueryParam>();
		list.add(q);
		q = new QueryParam();
		q.setParameterName("city");
		q.setParameterValue("ggn");
		//list.add(q);
		formData.setQueryParams(list);
		
		List<HttpHeader> headers = new ArrayList<>();
		
		HttpHeader header = new HttpHeader();
		header.setHeaderName("efx-client-correlation-id");
		header.setHeaderValue("AllDataBlocks");
		headers.add(header);
		formData.setHttpHeaders(headers);
		formData.setMethod("post");
		formData.setShowRequest(true);
		String body = "{\"name\":\"manish\"}";
		formData.setBody(body);
		new SpecTemplate(formData).writeSpecs(directoryPath);
	*/}
	
	public static void genReport() {
		try {
			
			
			//UUID uuid = UUID. randomUUID(); 
			
			String directoryPath = "c:\\manish\\gauge-reports\\"+"MyAPI-"+UUID.randomUUID().toString(); 
			
			System.out.println("Dir path = "+directoryPath);
			//;
		    Path path = Paths.get(directoryPath);

		    //java.nio.file.Files;
		    Files.createDirectories(path);

		    File source = new File("C:\\manish\\tutorial\\gauge\\project-structure");
		    File dest = new File(directoryPath);
		    try {
		        FileUtils.copyDirectory(source, dest);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    
		    createFile(directoryPath);
		    createSpec(directoryPath);
		   // test(directoryPath);
		   /* ProcessBuilder builder = new ProcessBuilder(
		            "cmd.exe", "/c", "cd "+"\\"+directoryPath+"\\" + "&& gauge run specs");
		        builder.redirectErrorStream(true);
		        Process p = builder.start();*/
		        
		        //Process process = Runtime.getRuntime().exec("cmd /c cd C:\\manish\\gauge-reports\\MyAPI-dae178f7-2fe0-4d5b-8536-1f45e0efcc83 gauge run specs"); 
		        
		 /*   Runtime rt=Runtime.getRuntime();

		    	Process p=rt.exec("cmd");
*/		        //System.out.println(p.exitValue());
		    /*
		    Runtime rt = Runtime.getRuntime();
		    try {
		        rt.exec(new String[]{"cmd.exe","/c","start"});

		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
*/		    
		    //String[] cmd = { "cmd", "-c", "cd C:\\Users; dir" };
		    //Process p = Runtime.getRuntime().exec(cmd);
		    
		    
		    //Process p1  = Runtime.getRuntime().exec("cmd -c cd "+directoryPath +" && gauge run specs");
		    
		    
		   // Process process = Runtime.getRuntime()
		     //       .exec("cmd /c dir", null, new File("c:\\manish\\gauge-reports\\MyAPI-42cecaa2-8987-439c-87e6-2e05d57d781d\\"));
		          //.exec("sh -c ls", null, new File("Pathname")); for non-Windows users
		    //printResults(p1);
		    
		  //  System.out.println("Directory is created!");

		  } catch (IOException e) {

		    System.err.println("Failed to create directory!" + e.getMessage());

		  }
	}
	
	public static void printResults(Process process) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    String line = "";
	    while ((line = reader.readLine()) != null) {
	        System.out.println(line);
	    }
	}
	
	public static void test(String f) {
		 ProcessBuilder processBuilder = new ProcessBuilder();
	        // Windows
	        processBuilder.command("cmd.exe", "/c", "cd "+f," && gauge run specs");

	        try {

	            Process process = processBuilder.start();

	            BufferedReader reader =
	                    new BufferedReader(new InputStreamReader(process.getInputStream()));

	            String line;
	            while ((line = reader.readLine()) != null) {
	                System.out.println(line);
	            }

	            int exitCode = process.waitFor();
	            System.out.println("\nExited with error code : " + exitCode);

	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	}
}
