package com.tcs.cma.apis.tools.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tcs.cma.apis.tools.constant.Constant;
import com.tcs.cma.apis.tools.models.Attribute;
import com.tcs.cma.apis.tools.models.Definition;
import com.tcs.cma.apis.tools.models.ResourceParameter;

import io.swagger.models.Model;
import io.swagger.models.RefModel;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.CookieParameter;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.parameters.RefParameter;
import io.swagger.models.parameters.SerializableParameter;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;

/**
 * 
 * @author 44745
 *
 */
public class ApiHelper implements Constant {

	/**
	 * 
	 */
	private static Map<String, Object> valueMap = null;

	/**
	 * 
	 */
	static {
		valueMap = new HashMap<>();
		valueMap.put("integer", 0);
		valueMap.put("string", "string");
		valueMap.put("number", 0);
		valueMap.put("boolean", true);
		valueMap.put("object", "{}");
		valueMap.put("array", "[]");
	}

	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public static ResourceParameter getResourceParameter(Parameter parameter) {
		ResourceParameter resourceParameter = new ResourceParameter();
		if (parameter instanceof BodyParameter) {
			BodyParameter bodyParameter = (BodyParameter) parameter;
			resourceParameter.setParameterType(BODY_PARAMETER);
			resourceParameter.setAccess(bodyParameter.getAccess());
			resourceParameter.setDescription(bodyParameter.getDescription());
			resourceParameter.setIn(bodyParameter.getIn());
			resourceParameter.setName(bodyParameter.getName());
			resourceParameter.setPattern(bodyParameter.getPattern());
			resourceParameter.setRequired(bodyParameter.getRequired());
			resourceParameter.setSchema(bodyParameter.getSchema());
			return resourceParameter;
		} else if (parameter instanceof CookieParameter) {
			CookieParameter cookieParameter = (CookieParameter) parameter;
			resourceParameter.setParameterType(COOKIE_PARAMETER);
			resourceParameter.setAccess(cookieParameter.getAccess());
			resourceParameter.setAllowEmptyValue(cookieParameter.getAllowEmptyValue());
			resourceParameter.setCollectionFormat(cookieParameter.getCollectionFormat());
			resourceParameter.setDescription(cookieParameter.getDescription());
			resourceParameter.setFormat(cookieParameter.getFormat());
			resourceParameter.setIn(cookieParameter.getIn());
			resourceParameter.setName(cookieParameter.getName());
			resourceParameter.setType(cookieParameter.getType());
			resourceParameter.setRequired(cookieParameter.getRequired());
			resourceParameter.setPattern(cookieParameter.getPattern());
			return resourceParameter;
		} else if (parameter instanceof FormParameter) {
			FormParameter formParameter = (FormParameter) parameter;
			resourceParameter.setParameterType(FORM_PARAMETER);
			resourceParameter.setAccess(formParameter.getAccess());
			resourceParameter.setAllowEmptyValue(formParameter.getAllowEmptyValue());
			resourceParameter.setDescription(formParameter.getDescription());
			resourceParameter.setFormat(formParameter.getFormat());
			resourceParameter.setIn(formParameter.getIn());
			resourceParameter.setName(formParameter.getName());
			resourceParameter.setPattern(formParameter.getPattern());
			resourceParameter.setRequired(formParameter.getRequired());
			resourceParameter.setType(formParameter.getType());
			return resourceParameter;
		} else if (parameter instanceof HeaderParameter) {
			HeaderParameter headerParameter = (HeaderParameter) parameter;
			resourceParameter.setParameterType(HEADER_PARAMETER);
			resourceParameter.setAccess(headerParameter.getAccess());
			resourceParameter.setAllowEmptyValue(headerParameter.getAllowEmptyValue());
			resourceParameter.setDescription(headerParameter.getDescription());
			resourceParameter.setFormat(headerParameter.getFormat());
			resourceParameter.setIn(headerParameter.getIn());
			resourceParameter.setName(headerParameter.getName());
			resourceParameter.setPattern(headerParameter.getPattern());
			resourceParameter.setRequired(headerParameter.getRequired());
			resourceParameter.setType(headerParameter.getType());
			return resourceParameter;
		} else if (parameter instanceof PathParameter) {
			PathParameter pathParameter = (PathParameter) parameter;
			resourceParameter.setParameterType(PATH_PARAMETER);
			resourceParameter.setAccess(pathParameter.getAccess());
			resourceParameter.setAllowEmptyValue(pathParameter.getAllowEmptyValue());
			resourceParameter.setDescription(pathParameter.getDescription());
			resourceParameter.setFormat(pathParameter.getFormat());
			resourceParameter.setIn(pathParameter.getIn());
			resourceParameter.setName(pathParameter.getName());
			resourceParameter.setPattern(pathParameter.getPattern());
			resourceParameter.setRequired(pathParameter.getRequired());
			resourceParameter.setType(pathParameter.getType());
			return resourceParameter;
		} else if (parameter instanceof QueryParameter) {
			QueryParameter queryParameter = (QueryParameter) parameter;
			resourceParameter.setParameterType(QUERY_PARAMETER);
			resourceParameter.setAccess(queryParameter.getAccess());
			resourceParameter.setAllowEmptyValue(queryParameter.getAllowEmptyValue());
			resourceParameter.setDescription(queryParameter.getDescription());
			resourceParameter.setFormat(queryParameter.getFormat());
			resourceParameter.setIn(queryParameter.getIn());
			resourceParameter.setName(queryParameter.getName());
			resourceParameter.setPattern(queryParameter.getPattern());
			resourceParameter.setRequired(queryParameter.getRequired());
			resourceParameter.setType(queryParameter.getType());
			return resourceParameter;
		} else if (parameter instanceof RefParameter) {
			RefParameter refParameter = (RefParameter) parameter;
			return resourceParameter;
		} else if (parameter instanceof SerializableParameter) {
			SerializableParameter serializableParameter = (SerializableParameter) parameter;
			return resourceParameter;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param swagger
	 * @return
	 */
	public static Map<String, Definition> getDefinitions(Swagger swagger) {
		Map<String, Definition> definitionMap = new HashMap<>();
		Map<String, Model> definitions = swagger.getDefinitions();
		Definition definition = null;
		List<Attribute> attributeList = null;
		Attribute attribute = null;
		for (Map.Entry<String, Model> modelSet : definitions.entrySet()) {
			definition = new Definition();
			attributeList = new ArrayList<>();
			String defName = modelSet.getKey();
			definition.setDefName(defName);
			Model defModel = definitions.get(defName);
			Map<String, Property> propertyMap = defModel.getProperties();
			for (Map.Entry<String, Property> propertySet : propertyMap.entrySet()) {
				attribute = new Attribute();
				String attributeName = propertySet.getKey();
				attribute.setAttributeName(attributeName);
				Property property = propertyMap.get(attributeName);
				if (property instanceof RefProperty) {
					RefProperty ref = (RefProperty) property;
					attribute.setRefName(ref.getSimpleRef());
				}
				attribute.setAttributeType(property.getType());
				attribute.setAttributeExample(property.getExample());
				attribute.setAttributeValue(getAttributeValue(property, attribute));
				attributeList.add(attribute);
			}
			definition.setAttributes(attributeList);
			definitionMap.put(defName, definition);
		}
		return definitionMap;
	}

	/**
	 * 
	 * @param property
	 * @param attribute
	 * @return
	 */
	private static Object getAttributeValue(Property property, Attribute attribute) {
		if (property.getExample() != null) {
			return property.getExample();
		} else if (property instanceof StringProperty) {
			StringProperty stringProperty = (StringProperty) property;
			if (stringProperty.getEnum() != null)
				return stringProperty.getEnum().get(0);
			return valueMap.get(attribute.getAttributeType());
		} else {
			return valueMap.get(attribute.getAttributeType());
		}

	}

	/**
	 * 
	 * @param definition
	 * @return
	 */
	public static String getJsonString(Definition definition, Map<String, Definition> definitionMap) {
		StringBuilder jsonString = new StringBuilder("{\n");
		List<Attribute> attributeList = definition.getAttributes();
		for (Attribute attribute : attributeList) {
			if (!attribute.getAttributeType().equals("ref")) {
				jsonString.append("\"" + attribute.getAttributeName() + "\"" + ":" + /*"\""
						+ attribute.getAttributeValue().toString() + "\","*/getAttributeVauleString(attribute));
			} else {
				jsonString.append(getRefJson(attribute.getAttributeName(), definitionMap.get(attribute.getRefName())));
			}

		}
		String result = jsonString.toString();
		if (result.length() > 2) {
			result = result.substring(0, result.length() - 1) + "\n}";
		}
		return result;
	}

	/**
	 * 
	 * @param attributeName
	 * @param definition
	 * @return
	 */
	private static String getRefJson(String attributeName, Definition definition) {
		StringBuilder refAttribute = new StringBuilder("\"" + attributeName + "\": {");
		List<Attribute> attributeList = definition.getAttributes();
		for (Attribute attribute : attributeList) {
			if (!attribute.getAttributeType().equals("ref")) {
				refAttribute.append("\"" + attribute.getAttributeName() + "\"" + ":"
						+ /* attribute.getAttributeValue().toString() + "\"," */getAttributeVauleString(attribute));
			} else {
				// refAttribute.append(getRefJson(attribute.getAttributeName(),definitionMap.get(attribute.getRefName())));
			}

		}
		String result = refAttribute.toString();
		if (result.length() > 2) {
			result = result.substring(0, result.length() - 1) + "},";
		}
		return result;
	}

	/**
	 * 
	 * @param attribute
	 * @return
	 */
	private static String getAttributeVauleString(Attribute attribute) {
		if (attribute.getAttributeType().equals("number") || attribute.getAttributeType().equals("integer")
				|| attribute.getAttributeType().equals("boolean") || attribute.getAttributeType().equals("array")) {
			return  attribute.getAttributeValue().toString() + ",";
		} else {
			return "\"" + attribute.getAttributeValue().toString() + "\",";
		}
	}
	
	/**
	 * 
	 * @param resourceParameter
	 * @param def
	 * @return
	 */
	public static String getResourceBodySchema(ResourceParameter resourceParameter, Map<String, Definition> def) {
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
