package com.rmordente.calculadoraGWT.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.Viewport;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CalculadoraGWT implements EntryPoint 
{
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{		
		Vista calculadora = new Vista();
		
		Viewport vp = new Viewport();
		vp.setWidget(calculadora);
		
		RootPanel.get().add(vp);
	}
}
