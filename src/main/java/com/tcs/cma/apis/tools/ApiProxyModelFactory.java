package com.tcs.cma.apis.tools;

import java.net.URL;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tcs.cma.apis.tools.models.ApiProxyModel;
import com.tcs.cma.apis.tools.models.Definition;
import com.tcs.cma.apis.tools.models.Resource;
import com.tcs.cma.apis.tools.models.ResourceParameter;
import com.tcs.cma.apis.tools.util.ApiHelper;

import io.swagger.models.HttpMethod;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.RefModel;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.properties.Property;
import io.swagger.parser.OpenAPIParser;
import io.swagger.parser.Swagger20Parser;
import io.swagger.parser.SwaggerParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

/**
 * 
 * @author Manish Srivastava 22 Sep 2019
 */
public final class ApiProxyModelFactory {

	// Logger for logging
	final static Logger logger = Logger.getLogger(ApiProxyModelFactory.class);

	/**
	 * 
	 */
	private ApiProxyModelFactory() {

	}

	/**
	 * 
	 * @param apiSpec
	 * @return
	 */
	public static ApiProxyModel getApiModel(String apiSpec) {
		return parse3xSwagger(apiSpec);
	}

	/**
	 * 
	 * @param apiSpec
	 * @return
	 */
	private static ApiProxyModel parse3xSwagger(String apiSpec) {
		OpenAPI openApi = null;// new OpenAPIV3Parser().read(apiSpec);
		try {
			Swagger obj = new Swagger20Parser().parse(apiSpec);
			System.out.println(obj.getBasePath());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (openApi == null) {
			logger.info("API PROXY GEN: Unable to read file " + apiSpec);
			return null;
		}
		ApiProxyModel model = new ApiProxyModel();
		model.setApiDescription(openApi.getInfo().getDescription());
		if (openApi.getServers() != null) {
			try {
				URL url = new URL(openApi.getServers().get(0).getUrl());
				model.setApiBasePath(url.getPath());
			} catch (Exception e) {
				logger.error("API PROXY GEN Error: " + e.getLocalizedMessage());
				logger.error(e);
			}
		}
		model.setApiPaths(openApi.getPaths());
		model.setComponents(openApi.getComponents());
		return model;

	}

	public static void main(String[] args) {

		List<Resource> resourceList = new ArrayList<>();

		String path = "C:\\manish\\cma-works\\testbuddy\\pet2.json";
		// parse3xSwagger("C:\\manish\\cma-works\\testbuddy\\petstore.json");
		// SwaggerParseResult result = new
		// OpenAPIParser().readContents("./path/to/swagger.yaml", null, null);
		/// parse3xSwagger("C:\\manish\\cma-works\\testbuddy\\swagger2.json");
		// SwaggerParseResult res = new
		// OpenAPIParser().readContents("C:\\manish\\cma-works\\testbuddy\\swagger2.json",
		// null, null);
		// System.out.println(res);
		List<String> list = new ArrayList<String>();
		Swagger swagger = new SwaggerParser().read(path);
		//System.out.println(swagger);

		Map<String, Definition> def = ApiHelper.getDefinitions(swagger);

		Definition d = def.get("Pet");
		//System.out.println(d);

		//System.out.println(ApiHelper.getJsonString(d, def));

		// System.out.println(ApiHelper.getDefinitions(swagger).size());

		/*
		 * Map<String, Model> def = swagger.getDefinitions(); for (Map.Entry<String,
		 * Model> set1 : def.entrySet()) { //System.out.println(set.getKey() + " = " +
		 * set.getValue()); // System.out.println(set1.getKey()); String k =
		 * set1.getKey(); System.out.println("key="+k); Model m = def.get(k);
		 * //System.out.println(); System.out.println("ref="+m.getReference());
		 * 
		 * Map<String, Property> p = m.getProperties(); for (Map.Entry<String, Property>
		 * set12 : p.entrySet()) { String prop = set12.getKey();
		 * System.out.println("prop key="+prop); Property pp = p.get(prop);
		 * System.out.println(pp.getName()+" "+pp.getType());
		 * 
		 * }
		 * 
		 * }
		 */

		System.out.println("Basepath=" + swagger.getBasePath());
		System.out.println("Schema=" + swagger.getSchemes().get(0).name());
		System.out.println("Host=" + swagger.getHost());
		// System.out.println(swagger.getConsumes().get(0));
		// System.out.println(swagger.getInfo().getDescription() == null ? "NA" :
		// swagger.getInfo().getDescription());
		// System.out.println(swagger.getInfo().getTermsOfService() == null ? "NA" :
		// swagger.getInfo().getTermsOfService() );
		// System.out.println(swagger.getInfo().getTitle() == null ? "" :
		// swagger.getInfo().getTitle() );
		// System.out.println(swagger.getInfo().getVersion() == null ? "" :
		// swagger.getInfo().getVersion() );
		Map<String, Path> paths = swagger.getPaths();

		for (Map.Entry<String, Path> set : paths.entrySet()) {
			// System.out.println(set.getKey() + " = " + set.getValue());
			list.add(set.getKey());
		}

		Resource resource = null;
		//ResourceParameter resParam = null;
		List<ResourceParameter> resParamList = null;

		for (String key : list) {
			// resource = new Resource();
			// resource.setPath(key);
			Path path1 = paths.get(key);
			// System.out.println(path1.getParameters());
			Map<HttpMethod, Operation> opeMap = path1.getOperationMap();

			for (Map.Entry<HttpMethod, Operation> set : opeMap.entrySet()) {
				resource = new Resource();
				resource.setPath(key);
				// System.out.println(set.getKey() + " = " + set.getValue());
				// list.add(set.getKey());
				String method = set.getKey().name();
				Operation ope = opeMap.get(set.getKey());
				List<Parameter> paramList = ope.getParameters();
				List<Scheme> l = ope.getSchemes();
				//System.out.println(l);
				resParamList = new ArrayList<>();
				if (paramList != null) {
					for (Parameter p : paramList) {
						ResourceParameter resParameter = ApiHelper.getResourceParameter(p);
						resParameter.setJsonSchema(getResourceBodySchema(resParameter, def));
						resParamList.add(resParameter);
						/*
						 * PathParameter p1 = null; if(p instanceof PathParameter) p1 =
						 * (PathParameter)p;
						 * 
						 * FormParameter fp = (FormParameter)p;
						 * 
						 * fp.get resParam = new ResourceParameter();
						 * resParam.setDescription(p1.getDescription());
						 * resParam.setFormat(p1.getPattern()); resParam.setIn(p1.getIn());
						 * resParam.setName(p1.getName()); resParam.setRequired(p1.getRequired());
						 * resParamList.add(resParam);
						 */
						// System.out.println(p.getAccess()+" "+p.getDescription()+" "+p.getIn()+"
						// "+p.getName()+" "+p.getPattern()+" "+p.getRequired());
						// resParam.setType(p.get);
					}
				}
				resource.setMethod(method);
				resource.setParameters(resParamList);
				resourceList.add(resource);

			}

		}

		// System.out.println(resourceList.size());

		for (Resource r : resourceList) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("Resource ==>"+r.getPath());
			System.out.println(
					"Method=" + r.getMethod() + " Path=" + r.getPath() + " Parameters=" + r.getParameters().size());
			List<ResourceParameter> paramList = r.getParameters();
			System.out.println("Parameter List======>");
			for (ResourceParameter rp : paramList) {
				System.out.println("................................");
				System.out.println("ParamName=" + rp.getName() +"\nDesc=" + rp.getDescription() + "\nFormat=" + rp.getFormat() + "\nIn=" + rp.getIn()
						+ "\nJSONSchema=" + rp.getJsonSchema()  /*+ "\nParamType="
						+ rp.getParameterType()*/+"\nType="+rp.getType()+"\nRequired="+rp.isRequired());
				
			}
			System.out.println("*******************************************");

			// List<ResourceParameter> list1 = r.getParameters();

			/*
			 * for(ResourceParameter rp : list1) { if(rp.getIn().equals("body")){ Model m =
			 * rp.getSchema(); if(m instanceof RefModel) { RefModel refModel = (RefModel) m;
			 * System.out.println(ApiHelper.getJsonString(def.get(refModel.getSimpleRef()),
			 * def)); } //System.out.println(m); } }
			 */

		}

	}

	private static String getResourceBodySchema(ResourceParameter resourceParameter, Map<String, Definition> def) {
		if (resourceParameter.getIn().equals("body")) {
			Model model = resourceParameter.getSchema();
			if (model instanceof RefModel) {
				RefModel refModel = (RefModel) model;
				return ApiHelper.getJsonString(def.get(refModel.getSimpleRef()), def);
			}
			return null;
		} else {
			return null;
		}
	}
}
