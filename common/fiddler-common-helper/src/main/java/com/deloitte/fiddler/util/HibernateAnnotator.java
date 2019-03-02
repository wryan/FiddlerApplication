package com.deloitte.fiddler.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;

import javax.persistence.Cacheable;

import org.jsonschema2pojo.AbstractAnnotator;
import org.springframework.data.mongodb.core.mapping.DBRef;


public class HibernateAnnotator extends AbstractAnnotator {

    @Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
        super.propertyField(field, clazz, propertyName, propertyNode);

        // Note: does not have to be the propertyName, could be the field or propertyNode that is verified.
        if (propertyName.toUpperCase().indexOf("ID") > -1 && 
        		clazz.fullName().toUpperCase().indexOf(propertyName.toUpperCase().substring(0, propertyName.toUpperCase().indexOf("ID"))) > -1) {
        	field.name("id");
        	field.annotate(javax.persistence.Id.class);
//        	clazz.annotate(Cacheable.class).param("value", false);
        }
    }
}