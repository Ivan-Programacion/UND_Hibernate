package ejercicio_clase_frutas;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Fruta implements Serializable{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private int peso;
	private int color;
	private boolean buenAspecto;
	@OneToOne (cascade = { CascadeType.ALL })
	private Bicho bicho;

	public Bicho getBicho() {
		return bicho;
	}

	public void setBicho(Bicho bicho) {
		this.bicho = bicho;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public boolean isBuenAspecto() {
		return buenAspecto;
	}

	public void setBuenAspecto(boolean buenAspecto) {
		this.buenAspecto = buenAspecto;
	}
}
