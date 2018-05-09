package colusion;

import java.util.ArrayList;
import java.util.HashMap;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import java.lang.Math;
public class Consumidor {
	double utilidadReserva;
	Firma ultimaFirma;
    int IDconsumidor;
	static int maxIDconsumidor;
	ArrayList<Consumidor> redAmigos = new ArrayList<Consumidor>();
	private ContinuousSpace mercado;
	HashMap<Consumidor,Integer> redConsumidores = new HashMap<Consumidor,Integer>();
	boolean statusCompra;
	int compras;

	
	public Consumidor(ContinuousSpace space){
	  	
		this.mercado = space;
		this.IDconsumidor = ++maxIDconsumidor;
		this.utilidadReserva=Math.random()*50; //cambiaremos el 50 por algo más representativo
	

	}
	@ScheduledMethod(start=1,interval=1,shuffle=true,priority=100) 
	public void compra() {
		Firma firmaAlaqueSelecompra=null;
		Firma firmaCanditaaCompra=null;
		double precioUltimaFirma;
		firmaCanditaaCompra=this.ultimaFirma;
		precioUltimaFirma = mercado.getDistance(mercado.getLocation(this), mercado.getLocation(this.ultimaFirma)) +this.ultimaFirma.precio;
		double precioDeCompra=precioUltimaFirma;
		for(Consumidor i:this.redAmigos) {
			double precioAmigo=mercado.getDistance(mercado.getLocation(this), mercado.getLocation(i.ultimaFirma)) +i.ultimaFirma.precio;
			if(precioUltimaFirma<precioAmigo) {
				firmaCanditaaCompra=i.ultimaFirma;
				precioDeCompra=precioAmigo;
			}

		}
		if(this.utilidadReserva>=precioDeCompra) {
			firmaAlaqueSelecompra=firmaCanditaaCompra;
			//this.utilidadReserva=this.utilidadReserva-precioDeCompra; // ingreso constante
			this.statusCompra=true;
		}
		else {
			firmaAlaqueSelecompra=firmaCanditaaCompra;
			this.statusCompra=false;
		}
		
		this.ultimaFirma=firmaAlaqueSelecompra;
		System.out.printf("el consumior %s le compra a la firma %s al precio %s \n", this.IDconsumidor,this.ultimaFirma.IDfirma,precioDeCompra);
	}
	//@ScheduledMethod(start=1,interval=1,shuffle=true,priority=90) 
	public void actualizarRed() {  //aquí actualizamos la red quitando al amigo más ineficiente y colocando un nuevo amigo
		if(this.redAmigos.size()==0) { // si no tiene amigos
			
		}
		else {
		Consumidor amigoIneficiente=this.redAmigos.get(0);
		Consumidor amigoEficiente=this.redAmigos.get(0);
		double precioDeCompra=this.redAmigos.get(0).ultimaFirma.precio;
		double precioAmigo;
		double precioDeCompra2=precioDeCompra;
		for(Consumidor i:this.redAmigos) {
			precioAmigo=mercado.getDistance(mercado.getLocation(i.ultimaFirma), mercado.getLocation(i.ultimaFirma)) +i.ultimaFirma.precio;
			
			if(precioDeCompra<precioAmigo) {
				precioDeCompra=precioAmigo;
				//indiceIneficiente = i. 
						amigoIneficiente=i; //acá encontramos el más ineficiente
			}
			if(precioDeCompra2>precioAmigo) {
				precioDeCompra2=precioAmigo;
				amigoEficiente=i;
			}
			
		}
		for(Consumidor i:this.redAmigos) { //acá tomamos el más ineficiente y lo quitamos de la lista redAmigos y de la hashMap
			if(i==amigoIneficiente &&  amigoIneficiente.redConsumidores.containsKey(amigoIneficiente) == false && i != amigoIneficiente) {

				this.redAmigos.remove(this);			//lo eliminamos de la red de Amigos redAmigos
				this.redConsumidores.put(amigoIneficiente,0); 				//acá le ponemos un uno al hashmap
				amigoIneficiente.redConsumidores.put(i,0); 
			}
			if(i==amigoEficiente &&  amigoEficiente.redConsumidores.containsKey(amigoEficiente) == true && i != amigoEficiente) {
				int j =amigoEficiente.redAmigos.size();
				int l = (int) (Math.random()*j); //acá obtenemos el índice del amigo del amigo a agregar con prob normal
				Consumidor amigoNuevo= amigoEficiente.redAmigos.get(l); //obtenemos el nuevo amigo
				this.redAmigos.add(amigoNuevo);					// agregamos el nuevo amigo
				
				this.redConsumidores.put(amigoEficiente,1); 				//acá le ponemos un uno al hashmap
				amigoEficiente.redConsumidores.put(i,1); 					//igual pero en la red del amigo 
				System.out.printf("nuevo amigo %s de %s", amigoEficiente.IDconsumidor,i.IDconsumidor);
			}
		}
		}
	}

	//@ScheduledMethod(start=1,interval=1,shuffle=true,priority=90) 
	public void tester() {
	if(this.redAmigos.size()==0) { // si no tiene amigos
	}
	else {
		Consumidor amigoIneficiente=this.redAmigos.get(0);
		Consumidor amigoEficiente=this.redAmigos.get(0);
		double precioDeCompra=this.redAmigos.get(0).ultimaFirma.precio;
		double precioAmigo;
		double precioDeCompra2=precioDeCompra;
	}

}
	@ScheduledMethod(start=1,interval=1,shuffle=true,priority=100) 
	public void decisionutilidad() {
		this.utilidadReserva = Math.random()*20; //CAmbiar al método de establecimiento de precio.
	}
	@ScheduledMethod(start=1,interval=1,shuffle=true,priority=99)
	public void historicoCompras() {
		if(this.statusCompra==true) {
			this.compras++;
		}
	}
	
	public int getCompras() {
		return this.compras;
	}
	
    public Firma getultimaFirma() {
    	return this.ultimaFirma;
    }
    
    public int getIDconsumidor() {
    	return this.IDconsumidor;
    }
    
    public double getUtiliadDeReserva() {
    	return this.utilidadReserva;
    }
   
		// TODO Auto-generated method stub
		
//    	Firma elegida = firmas.get(0);
    //	System.out.printf("Consumidor le compra a la firma %s ", elegida.ID);
//    	for(Firma candidata:firmas) { 
//	            if (precioDeCompra(candidata)<precioDeCompra(elegida)) {
//	            	elegida = candidata;
//	            }
    //		System.out.printf("Firma le compra a la firma %s \n", elegida.ID);	
//    	}
    	// System.out.printf("Consumidor %s le compra a la firma %s ", this.ID, elegida.ID);
//    	this.ultimaFirma = elegida;
    	// System.out.printf("El menor precio es %2.2f firma %s\n", precioDeCompra(elegida), elegida.IDfirma);
    
//	@ScheduledMethod(start=1,interval=1,shuffle=true,priority=98) 
//	public void visitaALaFirma() {
//		Firma firma=getultimaFirma();
//		System.out.printf("este onsumidor %s visita a la firma %s", this.IDconsumidor,this.ultimaFirma.IDfirma);
//		this.mercado.moveByVector(this);
//	}
	
}