package com.rmordente.calculadoraGWT.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.rmordente.calculadoraGWT.client.tipos.*;
import com.rmordente.calculadoraGWT.shared.InfoConversion;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
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
	private AutoProgressMessageBox progreso;
	
	public Vista()
	{
		this.setHeading("Calculadora Básica");		

		visor = new TextField();
		visor.setWidth(190);
		visor.setReadOnly(true);
		visor.setText("0");
		
		BoxLayoutData bld = new BoxLayoutData(new Margins(5, 10, 5, 10));
		
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
		hbl4.add(crearBoton("Binario", TipoBoton.CONVERTIR_BINARIO, "120px"), bld);
		hbl4.add(crearBoton("=", TipoBoton.IGUAL), bld);		
		
		VBoxLayoutContainer c = new VBoxLayoutContainer();
		c.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);				
		
		c.add(hbl0);
		c.add(hbl1);
		c.add(hbl2);
		c.add(hbl3);
		c.add(hbl4);		
		
		//listar resultados		
		c.add(crearBoton("Ver resultados", TipoBoton.LISTAR_RESULTADOS, "190px"), new BoxLayoutData(new Margins(30)));
		
		progreso = new AutoProgressMessageBox("");		
		
		this.add(c, new BoxLayoutData(new Margins(30)));
	}
	
	private TextButton crearBoton(String etiqueta, TipoBoton tipo)
	{		
		return crearBoton(etiqueta, tipo, "50px");
	}
	
	private TextButton crearBoton(String etiqueta, TipoBoton tipo, String width)
	{
		TextButton boton = new TextButton(etiqueta);		
		
		boton.setWidth(width);		
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
				
			case CONVERTIR_BINARIO:
				if (Validador.esEntero(this.visor.getText()))
				{
					this.mostrarProgreso("Calculando operación...");
					controlador.procesarConvertirABinario(Integer.parseInt(this.visor.getText()));
				}
				else
					this.mostrarMensaje("Debe introducir un número entero", "Error");
				break;
			case LISTAR_RESULTADOS:
				this.mostrarProgreso("Cargando datos...");
				controlador.obtenerConversionesRealizadas();
				break;
		}	    
	}
	
	public void mostrarMensaje(String texto, String titulo)
	{
		MessageBox mensaje = new MessageBox(titulo);				
		mensaje.setMessage(texto);
		mensaje.show();
	}
	
	public void mostrarProgreso(String mensaje)
	{				
		progreso.setProgressText(mensaje);	
		progreso.auto();
		progreso.show();
	}
	
	public void ocultarProgreso()
	{
		progreso.hide();
	}
	
	public void mostrarConversiones(InfoConversion[] lista)
	{
		Dialog cuadroDialogo = new Dialog();
		
		DateTimeFormat formato = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm:ss");
		
		FlexTable tabla = new FlexTable();		
		
		tabla.setText(0, 0, "Decimal");
		tabla.setText(0, 1, "Binario");
		tabla.setText(0, 2, "Fecha y hora");
		
		tabla.addStyleName("tabla");
		tabla.getRowFormatter().addStyleName(0, "cabecera");		
		
		for (int i = 0; i < lista.length; i++) {
			InfoConversion info = lista[i];
			
			tabla.setText(i+1, 0, String.valueOf(info.getNumero()));
			tabla.setText(i+1, 1, info.getResultado());
			tabla.setText(i+1, 2, formato.format(info.getFechaSolicitud()));
			
			tabla.getCellFormatter().addStyleName(i+1, 0, "columnaNumerica");
			tabla.getCellFormatter().addStyleName(i+1, 1, "columnaNumerica");
			tabla.getCellFormatter().addStyleName(i+1, 2, "columnaFecha");
			
			if (i%2 != 0)
				tabla.getRowFormatter().addStyleName(i+1, "filaSombreada");
		}
       
		cuadroDialogo.setHeading("Conversiones a binario realizadas");
		cuadroDialogo.setPixelSize(450, -1);		  		  
		cuadroDialogo.setResizable(true);		
		cuadroDialogo.setBodyBorder(false);
		cuadroDialogo.setHideOnButtonClick(true);
		cuadroDialogo.add(tabla);
		cuadroDialogo.show();
	}
}
