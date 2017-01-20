package application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import bean.City;
import db.Dao;

public class Model {

	private Dao dao= new Dao();
	private SimpleWeightedGraph<City, DefaultWeightedEdge> grafo ;
	
	public List<String> getGiorniCombo(){
		List<String> gg = new LinkedList<>();
		String s0 = "Domenica";
		String s1 = "Lunedi";
		String s2 = "Martedi";
		String s3 = "Mercoledi";
		String s4 = "Giovedi";
		String s5 = "Venerdi";
		String s6 = "Sabato";
		gg.add(s0);
		gg.add(s1);
		gg.add(s2);
		gg.add(s3);
		gg.add(s4);
		gg.add(s5);
		gg.add(s6);
		return gg;	
		
	}
	
	public int getNumeroGioro(String gg){
		int num=-1;
		if(gg.equals("Domenica")){
			num=0;
		}
		if(gg.equals("Lunedi")){
			num=1;
		}
		if(gg.equals("Martedi")){
			num=2;
		}
		if(gg.equals("Mercoledi")){
			num=3;
		}
		if(gg.equals("Giovedi")){
			num=4;
		}
		if(gg.equals("Venerdi")){
			num=5;
		}
		if(gg.equals("Sabato")){
			num=6;
		}
		return num;
	}
	
	public List<City > getCity(int gg){
		List<City> all= dao.getVentiCittaConPiuAscolti(gg);
		return all;
	}
	
	public List<String> getElenco(int gg){
		List<String> all= dao.getElenco(gg);
		return all;
	}

	public int getNumeCanzoni(int gg, int c1, int c2){      //credo funzioni
		int num = dao.getNumeroCanzoniInDueCitta(gg, c1, c2);
		//System.out.println(num);
		return num;
	}
	
	//è lento ma funziona
	
	public void buildGraph(int gg){
		grafo = new SimpleWeightedGraph<City, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo,  getCity(gg));    //le 20 citta x vertici
		for(City c1 : grafo.vertexSet()){
			for(City c2 : grafo.vertexSet()){
				if(!c1.equals(c2)){
					int peso = getNumeCanzoni(gg, c1.getId(), c2.getId());
					if(peso>0){
						Graphs.addEdge(grafo,c1, c2 , peso);
					}
					//se il peso è 0 non metto l'arco
				}
			}
		}
		System.out.println(grafo.toString());
	}
	
	
	public double getTotPesoArchi(City city){
		double tot=0;
		Set<DefaultWeightedEdge> archi = grafo.edgesOf(city);
		for(DefaultWeightedEdge a : archi){
			tot += grafo.getEdgeWeight(a);
		}
		return tot;
	}
	
	
	
	public Map<Integer, City> getMappe(){
		int max = 0;
		Map< Integer, City> mappa = new HashMap<>();
		for(City c : grafo.vertexSet()){
			double peso = getTotPesoArchi(c);
			if(peso > max){
				max = (int) peso;
				if(mappa.size() < 3 ){
		    		mappa.put(max, c);
		    	}
		    }
		}
		System.out.println(mappa.toString());       //ne mette solo 2
		return mappa;
	}
	
	
	public void treCittaMax(){
		
		//1 analizzo vertice per vertice
		
		//2 ottengo gli archi del vertice che sto analizzando
		
		//3 calcolo la somma dei pesi
		
		//4 guardo se ho già tre città 
		// 4.1 no--> aggiungo questa città ed il num calcolato alla mappa
		//4.2 si--> guardo se il num calcolato è più grande di uno degli altri 3 eventualm tolgo il minore e metto quello attuale
		
		//torno a punto 2
		
	}
	
	public static void main(String [] args){
		Model m = new Model();
		m.buildGraph(2);
		m.getMappe();
	}
}
