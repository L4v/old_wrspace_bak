package org.jrabbit.standard.game.world.camera.components;

import org.jrabbit.base.graphics.transforms.Vector2f;
import org.jrabbit.base.managers.window.WindowManager;
import org.jrabbit.base.math.vector.BaseVector2f;
import org.jrabbit.base.math.vector.VectorTransform;
import org.jrabbit.standard.game.managers.GameManager;
import org.jrabbit.standard.game.world.camera.Camera;

/*****************************************************************************
 * A SceneToCameraCoordTransform (yes, that's quite a mouthful) converts basic
 * scene coordinates (e.g., those of the Mouse) into coordinates in the viewed
 * gameworld. The method it uses explicitly depends upon using an active Camera.
 * 
 * @author Chris Molini
 *****************************************************************************/
public class SceneToCameraCoordTransform implements VectorTransform
{
	/*************************************************************************
	 * Converts the supplied "scene" coordinates into "world" coordinates.
	 * 
	 * @param vector
	 * 			  The Vector2f that indicates the coordinates to be transformed.
	 * 
	 * @return The coordinates in the gameworld that correspond to the supplied
	 *         location.
	 ***************************************************************/ @Override
	public Vector2f transform(BaseVector2f vector)
	{
		Camera camera = GameManager.camera();
		Vector2f result = camera.location().copy();
		float theta = camera.rotation().theta();
		float x = vector.x() - (WindowManager.controller().width() / 2);
		float y = vector.y() - (WindowManager.controller().height() / 2);
		float cos = (float) Math.cos(theta);
		float sin = (float) Math.sin(theta);
		result.add((cos * x - sin * y) / camera.scalar().xScale(), 
				(cos * y + sin * x) / camera.scalar().yScale());
		return result;
	}
}