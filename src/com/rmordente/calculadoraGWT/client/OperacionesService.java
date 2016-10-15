package com.rmordente.calculadoraGWT.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.rmordente.calculadoraGWT.shared.InfoConversion;


@RemoteServiceRelativePath("operaciones")
public interface OperacionesService extends RemoteService
{
	InfoConversion convertirABinario(int numero, Date fechaSolicitud);  
}