package com.rmordente.calculadoraGWT.server;

import com.rmordente.calculadoraGWT.client.OperacionesService;
import com.rmordente.calculadoraGWT.shared.*;

import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class OperacionesServiceImpl extends RemoteServiceServlet implements OperacionesService {

	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	/**
	 * Convierte un entero de base decimal a binario
	 * 
	 * @param numero: número en base decimal a convertir
	 * @param fechaSolicitud: fecha y hora en la que se hace la solicitud
	 * @return Objeto InfoConversion con el resultado. Si ha habido error al grabar en BD se guarda un mensaje de error.  
	 */
    public InfoConversion convertirABinario(int numero, Date fechaSolicitud)
	{    	
    	InfoConversion info = new InfoConversion(numero, Integer.toBinaryString(numero), fechaSolicitud);
    	
    	if (!grabarConversionBinario(numero, info.getResultado(), fechaSolicitud))
    		info.setErrorGrabacion("No se ha podido grabar el resultado de la conversión");    		
    		
    	return info;
    }
	
    /**
     * Graba la información de conversión en BD
     * 
     * @param decimal: número en base decimal 
     * @param binario: número en binario
     * @param fecha: fecha y hora en la que se hace la solicitud de conversion
     * @return Devuelve true si ha conseguido grabar la información y false en caso contrario
     */
	private boolean grabarConversionBinario(int decimal, String binario, Date fecha)
	{
		//graba en BD la informacion: si puede grabar devuelve true sino false		
		PersistenceManager pm = PMF.getPersistenceManager();
		boolean grabado = true;
		
		try
		{
		  pm.makePersistent(new InfoConversion(decimal, binario, fecha));		  
		}
		catch (Exception e)
		{
			grabado = false;
		}
		finally {
			if (pm != null)
				pm.close();
		}
		
		return grabado;
	}
}