package com.rmordente.calculadoraGWT.client;

public class Operador {
	private static Operador instancia = null;
	
	private Operador()
	{
		
	}
	
	public static Operador getOperador()
	{
		if (instancia == null)
			instancia = new Operador();
		
		return instancia;
	}
	
	public double sumar(double sumando1, double sumando2)
	{
		return sumando1 + sumando2;
	}
	
	public double restar(double minuendo, double sustraendo)
	{
		return minuendo - sustraendo;
	}
	
	public double multiplicar(double multiplicando, double multiplicador)
	{
		return multiplicando * multiplicador;
	}
	
	public double dividir(double diviendo, double divisor)
	{		
		if (divisor == 0)
			return Double.NaN;
		else
			return diviendo / divisor;
	}
	
	public double porcentaje(double base, double porcentaje)
	{		
		return (porcentaje * base)/100;
	}
}
