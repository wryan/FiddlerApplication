package com.deloitte.fiddler.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaClient;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import com.deloitte.fiddler.model.StandardProjectInformationSchema;
import com.deloitte.fiddler.model.StandardTaskSchema;
import com.deloitte.fiddler.model.StandardTeamSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

@Service
public class JSONvalidator {

	public <T> boolean validateJSON(Class<?> t, Object json) throws ValidationException {
		ObjectMapper mapper = new ObjectMapper();
		SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();

		try (InputStream inputStream = getClass().getResourceAsStream(this.getJson(t))) {
			mapper.acceptJsonFormatVisitor(t, visitor);
			String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			JSONObject rawSchema = new JSONObject(result);
			SchemaLoader schemaLoader = SchemaLoader.builder().schemaClient(SchemaClient.classPathAwareClient())
					.schemaJson(rawSchema).resolutionScope("http://example.org/") // setting the default resolution
																					// scope
					.build();
			Schema schema = schemaLoader.load().build();
			schema.validate(new JSONObject(mapper.writeValueAsString(json)));
			return true;
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
			e.getCausingExceptions().stream().map(ValidationException::getMessage).forEach(System.out::println);
			throw e;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;

//	       ObjectMapper mapper = new ObjectMapper();
//	        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
//	    
//	        try {
//				mapper.acceptJsonFormatVisitor(t, visitor);
//		        JsonSchema jsonSchema = visitor.finalSchema();
//		        JSONObject rawSchema = new JSONObject(mapper
//		                .writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
//		        Schema schema = SchemaLoader.load(rawSchema);
//		        
//		        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
//		        System.out.println(new JSONObject(mapper.writeValueAsString(json)));
//
//		        schema.validate(new JSONObject(mapper.writeValueAsString(json))); // throws a ValidationException if this object is invalid
//		        System.out.println("sadf");
//		        return true;
//	        } catch (JsonMappingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JsonProcessingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        return false;

	}

	private String getJson(Class<?> t) {

		if (t == (StandardProjectInformationSchema.class)) {
			return "/schema/standardProjectInformationSchema.json";
		} else if (t == (StandardTeamSchema.class)) {
			return "/schema/standardTeamSchema.json";
		} else if (t == (StandardTaskSchema.class)) {
			return "/schema/standardTaskSchema.json";
		}
		return null;
	}

}
