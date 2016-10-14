package com.rmordente.calculadoraGWT.client;

import com.rmordente.calculadoraGWT.client.tipos.*;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;

public class Vista extends ContentPanel implements SelectHandler
{
	/**
	 * Información asociada al boton
	 */
	public static final String INFO_TIPO_BOTON = "tipo";
	
	private TextField visor;
	
	public Vista()
	{
		this.setHeading("Calculadora Básica");		

		visor = new TextField();
		visor.setWidth(170);
		visor.setReadOnly(true);
		visor.setText("0");
		
		BoxLayoutData bld = new BoxLayoutData(new Margins(5));
		
		HBoxLayoutContainer hbl0 = new HBoxLayoutContainer();		
		hbl0.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);		
		hbl0.add(visor, bld);
		hbl0.add(crearBoton("C", TipoBoton.CLEAR_C), bld);
		hbl0.add(crearBoton("CE", TipoBoton.CLEAR_CE), bld);
		
		HBoxLayoutContainer hbl1 = new HBoxLayoutContainer();		
		hbl1.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		hbl1.add(crearBoton("7", TipoBoton.DIGITO), bld);
		hbl1.add(crearBoton("8", TipoBoton.DIGITO), bld);
		hbl1.add(crearBoton("9", TipoBoton.DIGITO), bld);
		hbl1.add(crearBoton("+/-", TipoBoton.SIGNO), bld);
		hbl1.add(crearBoton("%", TipoBoton.PORCENTAJE), bld);
		
		HBoxLayoutContainer hbl2 = new HBoxLayoutContainer();
		hbl2.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		hbl2.add(crearBoton("4", TipoBoton.DIGITO), bld);
		hbl2.add(crearBoton("5", TipoBoton.DIGITO), bld);
		hbl2.add(crearBoton("6", TipoBoton.DIGITO), bld);
		hbl2.add(crearBoton("+", TipoBoton.SUMA), bld);
		hbl2.add(crearBoton("-", TipoBoton.RESTA), bld);
		
		HBoxLayoutContainer hbl3 = new HBoxLayoutContainer();
		hbl3.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		hbl3.add(crearBoton("1", TipoBoton.DIGITO), bld);
		hbl3.add(crearBoton("2", TipoBoton.DIGITO), bld);
		hbl3.add(crearBoton("3", TipoBoton.DIGITO), bld);
		hbl3.add(crearBoton("*", TipoBoton.MUL), bld);
		hbl3.add(crearBoton("/", TipoBoton.DIV), bld);
		
		HBoxLayoutContainer hbl4 = new HBoxLayoutContainer();
		hbl4.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		hbl4.add(crearBoton("0", TipoBoton.DIGITO), bld);
		hbl4.add(crearBoton(".", TipoBoton.PUNTO), bld);
		hbl4.add(crearBoton("=", TipoBoton.IGUAL), new BoxLayoutData(new Margins(5, 5, 5, 125)));		
		
		VBoxLayoutContainer c = new VBoxLayoutContainer();
		c.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
		c.setBorders(true);		
		
		c.add(hbl0);
		c.add(hbl1, new BoxLayoutData(new Margins(0, 300, 0, 300)));
		c.add(hbl2, new BoxLayoutData(new Margins(0, 300, 0, 300)));
		c.add(hbl3, new BoxLayoutData(new Margins(0, 300, 0, 300)));
		c.add(hbl4, new BoxLayoutData(new Margins(0, 300, 0, 300)));		
		
		this.add(c, new MarginData(100));
	}
	
	private TextButton crearBoton(String etiqueta, TipoBoton tipo)
	{
		TextButton boton = new TextButton(etiqueta);
		
		boton.setWidth("50px");		
		boton.setData(INFO_TIPO_BOTON, tipo);		
		boton.addSelectHandler(this);
		
		return boton;
	}
	
	public String getDatosVisor()
	{
		return this.visor.getText();
	}	
	
	public void setDatosVisor(String datos)
	{
		this.visor.setText(datos);
	}
	
	@Override
	public void onSelect(SelectEvent event)
	{
		TextButton boton = (TextButton)event.getSource();
		TipoBoton tipo = (TipoBoton) boton.getData(INFO_TIPO_BOTON);		
		
		Controlador controlador = Controlador.getControlador(this);
		
		switch (tipo)
		{
			case CLEAR_C:
				controlador.procesarClear();
				break;
				
			case CLEAR_CE:
				controlador.procesarCE();
				break;
				
			case DIGITO:
				controlador.procesarDigito(boton.getText());
				break;
				
			case PUNTO:				
				controlador.procesarPunto();
				break;
				
			case SIGNO:
				controlador.procesarSigno();
				break;
				
			case SUMA:
			case RESTA:
			case MUL:
			case DIV:
			case IGUAL:
				controlador.procesarOperador(tipo);
				break;
				
			case PORCENTAJE:
				controlador.procesarPorcentaje();
				break;
		}	    
	}
}
