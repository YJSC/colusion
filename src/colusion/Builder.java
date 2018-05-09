package colusion;

import java.util.ArrayList;
import java.util.HashMap;


import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.RandomCartesianAdder;

public class Builder implements ContextBuilder<Object>  {

	@Override
	public Context build(Context<Object> context) {
		// TODO Auto-generated method stub
		
		context.setId("Colusion");
	
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		
	
			ContinuousSpace <Object> mercado = spaceFactory.createContinuousSpace(
				"mercado",                                                  // Nombre
				context,                                                    // Context es el equivalente al main
				new RandomCartesianAdder<Object>(),                         // Posición
				
				// new repast.simphony.space.continous.WrapAroundBorders(),                    // Aquí el mundo nunca termina 
				new repast.simphony.space.continuous.BouncyBorders(),                          // Aquí el mundo tiene fronteras
				40,
				40);
		
	
	    // Generar agentes
	
		Parameters parametros = RunEnvironment.getInstance().getParameters();    
		ArrayList<Firma> Firmas = new ArrayList<Firma>();
		ArrayList<Consumidor> Consumidores = new ArrayList<Consumidor>();


		// Generamos a los consumidores
		
 
		
		// Generamos a las firmas
		
		for(int i=0;i<parametros.getInteger("numFirmas");i++) {
			Firma estaFirma = new Firma(mercado);
			context.add(estaFirma);
			Firmas.add(estaFirma);
		}
		
		for(int i=0;i<parametros.getInteger("numConsumidores");i++) {
			Consumidor esteConsumidor = new Consumidor(mercado);
			context.add(esteConsumidor);
			Consumidores.add(esteConsumidor);
			
			//for(Firma fir:Firmas) {
				// System.out.printf("El precio que el consumidor paga a la firma %s es %2.3f \n" , fir.getIDfirma(), esteConsumidor.precioDeCompra(fir));
			// }
		//	esteConsumidor.firmaConMenorPrecio(Firmas);
		//	System.out.printf("El consumidor %s le compra a la firma %s \n",esteConsumidor.IDconsumidor,esteConsumidor.ultimaFirma.IDfirma);
		}
	

		double probabilidadAmigos = parametros.getDouble("probAmigo");
		
		for(Consumidor buscador:Consumidores) {
		                     
			for(Consumidor candidato:Consumidores) {
		
				double randomNumber = RandomHelper.nextDoubleFromTo(0,1);
				if(randomNumber < probabilidadAmigos && buscador.redConsumidores.containsKey(candidato) == false && buscador != candidato){
				  buscador.redConsumidores.put(candidato, 1);
				 buscador.redConsumidores.put(candidato, 1);
					candidato.redConsumidores.put(buscador, 1);
					System.out.printf(" %s es amigo de %s \n", buscador.IDconsumidor,candidato.IDconsumidor);
				}

			}	

		}
		
		
		for(Consumidor juan:Consumidores){
			Firma firmaAlaqueSelecompra=null;
			Firma firmaCanditaaCompra=Firmas.get(0);
			double precioCanditaaCompra = mercado.getDistance(mercado.getLocation(juan), mercado.getLocation(firmaCanditaaCompra)) +firmaCanditaaCompra.precio;
			double precioDeCompra=precioCanditaaCompra;
			for(Firma i:Firmas) {
				double precioFirma=mercado.getDistance(mercado.getLocation(juan), mercado.getLocation(i)) + i.precio;
				if(precioFirma<precioDeCompra) {
					firmaCanditaaCompra=i;
					precioDeCompra=precioFirma;
				}
				
			}
			
			if(juan.utilidadReserva>=precioDeCompra) {
				firmaAlaqueSelecompra=firmaCanditaaCompra;
				//this.utilidadReserva=this.utilidadReserva-precioDeCompra; // ingreso constante
				juan.ultimaFirma=firmaAlaqueSelecompra;
				juan.statusCompra=true;
				juan.compras++;
			}
			else {
				firmaAlaqueSelecompra=firmaCanditaaCompra;
				juan.ultimaFirma=firmaAlaqueSelecompra;
				juan.statusCompra=false;
			}
			//juan.ultimaFirma=firmaAlaqueSelecompra;
			
		}
		
		for(Consumidor juan:Consumidores) {
			if(juan.ultimaFirma!=null && juan.statusCompra==true) {
				System.out.printf("El consumidor %s le compra a la firma %s \n", juan.IDconsumidor,juan.ultimaFirma.IDfirma);
			}
		}
		
//@ScheduledMethod(start=1,interval=1,shuffle=true,priority=99)
	
		return context; 
	
	}
	
}
