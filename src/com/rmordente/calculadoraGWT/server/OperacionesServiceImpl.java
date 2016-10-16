package com.rmordente.calculadoraGWT.server;

import com.rmordente.calculadoraGWT.client.rpc.OperacionesService;
import com.rmordente.calculadoraGWT.shared.*;
import java.util.Date;
import java.util.List;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class OperacionesServiceImpl extends RemoteServiceServlet implements OperacionesService {

	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");	
	
    public InfoConversion convertirABinario(int numero)
	{    	
    	InfoConversion info = new InfoConversion(numero, Integer.toBinaryString(numero), new Date());
    	
    	if (!grabarConversionBinario(numero, info.getResultado(), info.getFechaSolicitud()))
    		info.setErrorGrabacion("No se ha podido grabar el resultado de la conversión");    		
    		
    	return info;
    }
    
    @SuppressWarnings("unchecked")
	public InfoConversion[] obtenerConversiones()
    {
    	PersistenceManager pm = PMF.getPersistenceManager();
        List<InfoConversion> conversiones = null;
        
        try 
        {
        	Query q = pm.newQuery(InfoConversion.class);
        	q.setOrdering("fechaSolicitud desc");        	
        	
        	conversiones = (List<InfoConversion>) q.execute(); 
        	conversiones = (List<InfoConversion>) pm.detachCopyAll(conversiones);        	
        }        
        finally {        	
        	if (pm != null)
        		pm.close();
        }        

        return conversiones.toArray(new InfoConversion[conversiones.size()]);
    }
    
    /**
     * Graba la información de conversión en base de datos
     * 
     * @param decimal número en base decimal 
     * @param binario número en binario
     * @param fecha fecha y hora en la que se hace la solicitud de conversión
     * @return Devuelve true si ha conseguido grabar la información y false en caso contrario
     */
	private boolean grabarConversionBinario(int decimal, String binario, Date fecha)
	{
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