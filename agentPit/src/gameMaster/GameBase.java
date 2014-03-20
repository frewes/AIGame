package gameMaster;

import java.util.List;
import java.util.Map;

public interface GameBase {
	public void init(List<Player> players);
	public boolean progress();
	public Map<Player,Integer> score();
	public int numPlayersPerGame();
}
