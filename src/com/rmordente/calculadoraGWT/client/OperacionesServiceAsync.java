package com.rmordente.calculadoraGWT.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rmordente.calculadoraGWT.shared.InfoConversion;

public interface OperacionesServiceAsync
{
   void convertirABinario(int numero, Date fechaSolicitud, AsyncCallback<InfoConversion> callback);   
}