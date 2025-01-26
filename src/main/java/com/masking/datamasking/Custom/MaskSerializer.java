package com.masking.datamasking.Custom;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

// public class MaskSerializer extends JsonSerializer<String> {
public class MaskSerializer extends StdSerializer<Object> implements ContextualSerializer{
    private static final long serialVersionUID = -921393818067014798L;

	private String[] allowedRoiles;

	protected MaskSerializer(String[] allowedRoles) {
		this();
		this.allowedRoiles = allowedRoles;
	}

	public MaskSerializer() {
		super(Object.class);
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
		Optional<Mask> annotation = Optional.ofNullable(property).map(prop -> prop.getAnnotation(Mask.class));
		return new MaskSerializer(annotation.map(Mask::allowedRoles).orElse(new String[] { "ADMIN" }));
	}

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String data = value.toString();
		
		if (authentication != null) {
			List<String> roles = Arrays.asList(this.allowedRoiles);
			long count = authentication.getAuthorities().stream().filter(r -> roles.contains(r.getAuthority().substring(5))).count();
			
			if (count == 0) {
				data = data.replaceAll(".(?=.{4})", "X");
			}
		}
		
		gen.writeString(data);

	}

    // @Override
    // public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    //     // Replace with your masking logic
    //     String maskedValue = maskValue(value);
    //     gen.writeString(maskedValue);
    // }

    // private String maskValue(String value) {
    //     // Example: Mask all but the last four digits
    //     return value.replaceAll(".(?=.{4})", "*");
    // }
}
