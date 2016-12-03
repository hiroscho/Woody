package de.woody.game;

import com.badlogic.gdx.math.Vector2;

/** Some simple functionality
 * 
 *  PLANNED TO CHANGE SPAWNPOINT FINDING THROUGH TILED LAYER
 */
public class Level {

	/** Returns the current spawn for the level and number of reached checkpoints.
	 * 
	 * @param level  current level
	 * @param checkpoint  last reached checkpoint
	 * @return  fitting spawnpoint
	 */
	public static Vector2 getCurrentSpawn(int level, int checkpoint) {
		switch (level) {
		case 1:
			switch (checkpoint) {
			case 0:
				return new Vector2(10, 1);
			}

		default:
			return new Vector2(0, 0);
		}
	}
}
