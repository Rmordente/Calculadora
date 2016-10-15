package com.rmordente.calculadoraGWT.client;

/**
 * Clase para hacer validaciones
 * @author rmordente
 *
 */
public class Validador 
{
	/**
	 * Comprueba si un numero es entero
	 * @param numero
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
