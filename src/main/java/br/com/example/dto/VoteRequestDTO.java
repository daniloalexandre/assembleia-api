package br.com.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequestDTO {

	@NotNull
	@JsonProperty("favorable")
	private boolean favorable;

	@NotBlank
	@JsonProperty("cpf")
	private String cpf;

}
