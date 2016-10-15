package com.rmordente.calculadoraGWT.shared;

import java.io.Serializable;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class InfoConversion implements Serializable{

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long id;
  @Persistent
  private int numero;
  @Persistent
  private String resultado;
  @Persistent  
  private Date fechaSolicitud;
  @NotPersistent
  private String errorGrabacion;

  public InfoConversion() 
  {    
  }
  
  public InfoConversion(int numero, String resultado, Date fecha) {    
    this.numero = numero;
    this.resultado = resultado;
	this.fechaSolicitud = fecha;
	this.errorGrabacion = "";
  }

  public Long getId() {
    return this.id;
  }

  public int getNumero() {
    return this.numero;
  }

  public String getResultado() {
    return this.resultado;
  }

  public Date getFechaSolicitud() {
    return this.fechaSolicitud;
  }
  
  public String getErrorGrabacion() {
	  return this.errorGrabacion;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public void setResultado(String resultado) {
    this.resultado = resultado;
  }
  
  public void setErrorGrabacion(String error) {
	  this.errorGrabacion = error;
  }
}