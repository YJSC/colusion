package colusion;

import java.util.ArrayList;
import java.util.HashMap;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;

public class Firma {
	double costo;
	double agresividad;
	double precio = 10;
	double participacionMercado;
	double beneficiosAcumulados;
	int IDfirma=0;
	static int maxIDfirma=0;
	private ContinuousSpace mercado;
	HashMap<Firma,Integer> redFirmas = new HashMap<Firma,Integer>();
	
	
	public Firma(ContinuousSpace space) {
		this.mercado = space;
		this.IDfirma = ++maxIDfirma;
	}
	@ScheduledMethod(start=1,interval=1,shuffle=true,priority=110) 
	public void decisionPrecio() {
		this.precio= Math.random()*20; //CAmbiar al método de establecimiento de precio.
	}
    public int getIDfirma() {
    	return this.IDfirma;
    }
    
    public double getPrecio() {
    	return this.precio;
    }
}