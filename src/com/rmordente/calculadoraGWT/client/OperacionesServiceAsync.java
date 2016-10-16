package com.rmordente.calculadoraGWT.client;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rmordente.calculadoraGWT.shared.InfoConversion;

public interface OperacionesServiceAsync
{
   void convertirABinario(int numero, AsyncCallback<InfoConversion> callback);   
   void obtenerConversiones(AsyncCallback<InfoConversion[]> callback);
}