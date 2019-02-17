package com.deloitte.fiddler.util;

import java.util.stream.Collectors;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaClient;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;



@Service
public class JSONvalidator {

	public <T> boolean validateJSON(Class<T> t, Object json) throws ValidationException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);

	    JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(t);
	    Schema schema = null;
		try {
		    String jsonSchemaAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
		    System.out.println(jsonSchemaAsString);
		    SchemaLoader schemaLoader = SchemaLoader.builder().schemaClient(SchemaClient.classPathAwareClient())
					.schemaJson(new JSONObject(jsonSchemaAsString)).resolutionScope("http://example.org/") // setting the default resolution															// scope
					.build();
			schema = schemaLoader.load().build();
			schema.validate(new JSONObject(objectMapper.writeValueAsString(json)));
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			  e.getCausingExceptions().stream()
		      .map(ValidationException::getMessage)
		      .forEach(System.out::println);
			   String s =  e.getCausingExceptions().stream().map(a -> a.getMessage() + "<br>").collect(Collectors.joining());
			  
			  throw new ValidationException(schema, s, s, s);
			  
		}

	    return false;
	}

}
