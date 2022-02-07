package model.customer;

import lombok.Builder;
import lombok.Data;

@Builder
public @Data class Address{
	private String ciudad;
	private String calle;
	private String cP;
}