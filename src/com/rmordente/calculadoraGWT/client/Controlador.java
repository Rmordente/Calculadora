package com.rmordente.calculadoraGWT.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rmordente.calculadoraGWT.client.rpc.OperacionesService;
import com.rmordente.calculadoraGWT.client.rpc.OperacionesServiceAsync;
import com.rmordente.calculadoraGWT.client.tipos.*;
import com.rmordente.calculadoraGWT.shared.InfoConversion;

public class Controlador
{	
	private static Controlador instancia = null;	
	
	private double acumulado;
	private TipoBoton ultimaOperacion;
	private boolean nuevoNumero;
	private Vista vista;
	private Operador operador;
	private OperacionesServiceAsync operacionesSvc; 
	
	private Controlador(Vista vista)
	{
		this.acumulado = 0;
		this.ultimaOperacion = null;
		this.nuevoNumero = false;		
		this.vista = vista;
		this.operacionesSvc = GWT.create(OperacionesService.class);
		this.operador = Operador.getOperador();
	}
	
	public static Controlador getControlador(Vista vista)
	{
		if (instancia == null)
			instancia = new Controlador(vista);
		
		return instancia;
	}
	
	public void procesarClear()
	{
		this.acumulado = 0;
		this.ultimaOperacion = null;
		procesarCE();
	}	
	
	public void procesarCE()
	{
		this.vista.setDatosVisor("0");
		this.nuevoNumero = true;
	}
	
	public void procesarDigito(String digito)
	{
		if (this.nuevoNumero)
		{
			this.vista.setDatosVisor(digito);
			this.nuevoNumero = false;
		}
		else
		{
			String numero = this.vista.getDatosVisor();
			
			if (numero.equals("0"))
				this.vista.setDatosVisor(digito);
			else
				this.vista.setDatosVisor(numero + digito);				
		}					
	}
	
	public void procesarPunto()
	{
		String numero = this.vista.getDatosVisor();
		
		if (this.nuevoNumero)
		{
			numero = "0.";
			this.nuevoNumero = false;
		}
		else
		{
			if (!numero.contains("."))
				numero += ".";
		}
		
		this.vista.setDatosVisor(numero);
	}
	
	public void procesarSigno()
	{		
		this.vista.setDatosVisor(String.valueOf(this.operador.multiplicar(Double.parseDouble(this.vista.getDatosVisor()), -1)));
	}
	
	public void procesarPorcentaje()
	{		
		String resultado = String.valueOf(this.operador.porcentaje(this.acumulado, Double.parseDouble(this.vista.getDatosVisor())));
		
		this.vista.setDatosVisor(resultado);				
	}	
	
	public void procesarOperador(TipoBoton tipo)
	{
		double numero = Double.parseDouble(this.vista.getDatosVisor());		
		
		if (!(this.nuevoNumero && this.ultimaOperacion != TipoBoton.IGUAL))
		{		    
			this.nuevoNumero = true;
			
			//el switch no es capaz de tratar nulos
			if (this.ultimaOperacion == null)
				this.acumulado = numero;
			else
			{
				switch (this.ultimaOperacion)
				{
					case SUMA:
						this.acumulado = this.operador.sumar(this.acumulado, numero);					
						break;
					case RESTA:
						this.acumulado = this.operador.restar(this.acumulado, numero);
						break;
					case MUL:
						this.acumulado = this.operador.multiplicar(this.acumulado, numero);
						break;
					case DIV:
						this.acumulado = this.operador.dividir(this.acumulado, numero);
						break;
					default:
						this.acumulado = numero;
						break;
				}
			}
			
			this.vista.setDatosVisor(String.valueOf(this.acumulado));
			this.ultimaOperacion = tipo;			
		}
	}
	
	/*
	 * Operaciones asíncronas
	 */
	
	public void procesarConvertirABinario(int decimal)
	{		
		AsyncCallback<InfoConversion> callback = new AsyncCallback<InfoConversion>() {				
			public void onFailure(Throwable caught) {			
				vista.ocultarProgreso();
				vista.mostrarMensaje(caught.getMessage(), "Error");
			}

			public void onSuccess(InfoConversion result) {
				vista.ocultarProgreso();
				vista.setDatosVisor(result.getResultado());				
				
				if (result.getErrorGrabacion() != "")
					vista.mostrarMensaje(result.getErrorGrabacion(), "Error");
			}
		};		
		
		this.operacionesSvc.convertirABinario(decimal, callback);
	}
	
	public void obtenerConversionesRealizadas()
	{
		AsyncCallback<InfoConversion[]> callback = new AsyncCallback<InfoConversion[]>() {				
			public void onFailure(Throwable caught) {			
				vista.ocultarProgreso();
				vista.mostrarMensaje(caught.getMessage(), "Error");
			}

			public void onSuccess(InfoConversion[] result) {				
				vista.ocultarProgreso();
				vista.mostrarConversiones(result);
			}
		};	
		
		this.operacionesSvc.obtenerConversiones(callback);
	}
}
