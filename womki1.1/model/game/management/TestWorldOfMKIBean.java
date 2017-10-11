package game.management;

import java.util.HashMap;

public class TestWorldOfMKIBean {
public static void main(String[] args){
	WorldOfMKIBean game= new WorldOfMKIBean(3,3);
	HashMap<String,String> daten=game.getSpielfeldDaten();
	for(String s:daten.keySet()){
		System.out.println(daten.get(s));
	}
}
}
