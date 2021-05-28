package com.tcs.cma.apis.tools.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.tcs.cma.apis.tools.ApiProxyGenResolver;

/**
 * 
 * @author Manish Srivastava 15 Sep 2019
 */
public class ApiProxyGeneratorResolverTest {
	
	//Logger for logging
	final static Logger logger = Logger.getLogger(ApiProxyGeneratorResolverTest.class);
	@Rule
	public MojoRule mojoRule = new MojoRule();
	
	File testDir;

	/**
	 * Test pom file
	 */
	private File pomFile = null;

	/**
	 * 
	 */
	private final String FILE_SEPARATOR = File.separator;

	private ApiProxyGenResolver getApiProxyGeneratorResolver() {
		try {
			pomFile = new
			File("src"+FILE_SEPARATOR+"test"+FILE_SEPARATOR+"resources"+FILE_SEPARATOR+"test-pom.xml");
			//pomFile = new File(
					//"C:\\manish\\cma-works\\workspace\\apiproxy-gen-maven-plugin\\src\\test\\resources\\test-pom.xml");
			ApiProxyGenResolver apiProxyGenResolver = (ApiProxyGenResolver) mojoRule.lookupMojo("apiproxy-gen", pomFile);
			return apiProxyGenResolver;
		} catch (Exception e) {
			logger.error("API PROXY GEN Error: " + e.getLocalizedMessage());
			logger.error(e);
			return null;
		}
	}

	@Test
	public void test() throws Exception {
		ApiProxyGenResolver apiProxyGenResolver = getApiProxyGeneratorResolver();
		if (null != apiProxyGenResolver) {
			logger.info("API PROXY GEN: Get resolver...");
			apiProxyGenResolver.execute();
		} else {
			logger.error("API PROXY GEN: Unable to get resolver...");
		}
	}
	
	@Before
	public void setUp() throws IOException{
		testDir = new File("Proxy-Test");
	}
	
	@After
	public void tearDown() throws IOException{
		if(testDir.exists()) {
			logger.info("API PROXY GEN: Deleting file "+testDir.getAbsolutePath());
			FileUtils.deleteDirectory(testDir);
		}else {
			logger.info("API PROXY GEN: File does not exist...");
		}
	}
}
