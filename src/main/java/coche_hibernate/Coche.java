package coche_hibernate;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Debe ser un Java Bean:
// - Implementar Serializable
// - Atributos privados
// - Constructor vacío
// - Getters and Setters
// - Añadir las anotaciones

@Entity // Decimos que la clase es una entidad
public class Coche implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // Decimos que este atributo va a ser el identificativo, y además que sea de
						// autoincremento
	private String matricula;
	private String marca;
	private String modelo;
	private int potencia;

	public Coche() {

	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
