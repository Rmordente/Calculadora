package com.rmordente.calculadoraGWT.client.utils;

/**
 * Clase para hacer validaciones
 * @author rmordente
 *
 */
public class Validador 
{
	/**
	 * Comprueba si un número es entero
	 * @param numero número a comprobar
	 * @return true si es entero, false si no lo es
	 */
	public static boolean esEntero(String numero)
	{	
		try
		{
			Integer.parseInt(numero);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}
}
