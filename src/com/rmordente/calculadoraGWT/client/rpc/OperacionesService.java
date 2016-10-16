package com.rmordente.calculadoraGWT.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.rmordente.calculadoraGWT.shared.InfoConversion;


@RemoteServiceRelativePath("operaciones")
public interface OperacionesService extends RemoteService
{
	/**
	 * Convierte un número entero de base decimal a binario
	 * 
	 * @param numero número entero en base decimal a convertir
	 * @return Objeto de tipo InfoConversion con el resultado. Si ha habido error al grabar en BD se guarda un mensaje de error.  
	 */	
	InfoConversion convertirABinario(int numero);
	
	/**
     * Obtiene las conversiones a binario realizadas
     * @return Array de objetos de tipo InfoConversion
     */	
	InfoConversion[] obtenerConversiones();
}