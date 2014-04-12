package gameMaster;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GameMaster {

	public static void main(String[] args) {
		
		Map<String,List<Integer>> scores = new HashMap<String,List<Integer>>();
		
		for(String name : PlayerTracker.playerList()){
			scores.put(name, new LinkedList<Integer>());
		}
		
		for(String name : GameTracker.getNames()){
			
			GameBase game = GameTracker.getGames(name);
			System.out.print(name );
			int nppg = game.numPlayersPerGame();
			int np = PlayerTracker.playerList().length;
			
			System.out.println(" " + nppg+ "," + np);
			
			String[] players = PlayerTracker.playerList();
			if(np > nppg){
				Map<String,Integer> gameScore = new HashMap<String,Integer>();
				for(String pname : PlayerTracker.playerList()){
					gameScore.put(pname, 0);
				}
				
				//MAKE DRAW
				List<List<Integer>> draw = new LinkedList<List<Integer>>();
				int[] mask = new int[nppg];
				for(int i=0;i<nppg;i++){
					mask[i] = i;
				}
				
				//scroll draw here
				boolean done = false;
				while(!done){
					List<Integer> slot = new LinkedList<Integer>();
					for(int i=0;i<nppg;i++){
						slot.add(mask[i]);
					}
					draw.add(slot);
					done = true;
					for(int i=0;i<nppg;i++){
						done &= (mask[i] == (np - nppg + i));
					}
					//progress mask
					for(int i=0;i<nppg;i++){
						if((mask[i] == np - nppg + i) && (i > 0)){
							mask[i] = mask[i-1]+1;
						}else if((i == nppg-1) || (mask[i+1] == np-nppg+(i+1))){
							mask[i] ++;
						}
					}
				}
				//DRAW COMPLETE HERE
				System.out.println("Draw done " + draw.size());
				for(List<Integer>i:draw){
					List<Player> labrats = new LinkedList<Player>();
					for(int j=0;j<nppg;j++){
						labrats.add(PlayerTracker.creator(players[i.get(j)]));
					}
					System.out.println("Game: " + i);
					game.init(labrats);
					while(game.progress());
					Map<Player,Integer> score = game.score();
					if(score != null){
						for(Entry<Player,Integer> j:score.entrySet()){
							String n = j.getKey().getClass().getName();
							gameScore.put(n, gameScore.get(n)+ j.getValue());
						}
					}
				}
				System.out.println("Game done");
				//Games done now append scores to table
				for(Entry<String,Integer>i:gameScore.entrySet()){
					scores.get(i.getKey()).add(i.getValue());
				}
			}
		}
		
		//put scores in .csv value
		for(Entry<String,List<Integer>>i:scores.entrySet()){
			System.out.print(i.getKey());
			for(Integer j:i.getValue()){
				System.out.print(","+j);
			}
			System.out.println();
		}
	}
}
