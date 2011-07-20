/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.catroid.test.content.brick;

import java.io.File;

import android.test.InstrumentationTestCase;
import at.tugraz.ist.catroid.ProjectManager;
import at.tugraz.ist.catroid.common.Consts;
import at.tugraz.ist.catroid.content.Project;
import at.tugraz.ist.catroid.content.Sprite;
import at.tugraz.ist.catroid.content.bricks.SetBrightnessBrick;
import at.tugraz.ist.catroid.io.StorageHandler;
import at.tugraz.ist.catroid.test.R;
import at.tugraz.ist.catroid.test.utils.TestUtils;
import at.tugraz.ist.catroid.utils.UtilFile;

public class SetBrightnessBrickTest extends InstrumentationTestCase {

	private double brightnessValue = 50.1;

	private static final int IMAGE_FILE_ID = R.raw.icon;

	private File testImage;
	private final String projectName = "testProject";

	@Override
	protected void setUp() throws Exception {

		File defProject = new File(Consts.DEFAULT_ROOT + "/" + projectName);

		if (defProject.exists()) {
			UtilFile.deleteDirectory(defProject);
		}

		Project project = new Project(getInstrumentation().getTargetContext(), projectName);
		StorageHandler.getInstance().saveProject(project);
		ProjectManager.getInstance().setProject(project);

		testImage = TestUtils.saveFileToProject(this.projectName, "testImage.png", IMAGE_FILE_ID, getInstrumentation()
				.getContext(), TestUtils.TYPE_IMAGE_FILE);
	}

	@Override
	protected void tearDown() throws Exception {
		File defProject = new File(Consts.DEFAULT_ROOT + "/" + projectName);

		if (defProject.exists()) {
			UtilFile.deleteDirectory(defProject);
		}
		if (testImage != null && testImage.exists()) {
			testImage.delete();
		}
	}

	public void testBrightnessEffect() {
		Sprite sprite = new Sprite("testSprite");
		assertEquals("Unexpected initial sprite scale value", 0.0, sprite.getBrightnessValue());

		SetBrightnessBrick brick = new SetBrightnessBrick(sprite, brightnessValue);
		brick.execute();
		assertEquals("Incorrect sprite scale value after SetGhostEffectBrick executed", brightnessValue,
				sprite.getBrightnessValue());
	}

	public void testNullSprite() {
		SetBrightnessBrick brick = new SetBrightnessBrick(null, brightnessValue);

		try {
			brick.execute();
			fail("Execution of SetGhostEffectBrick with null Sprite did not cause a NullPointerException to be thrown");
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	public void testNegativeBrighnessValue() {
		Sprite sprite = new Sprite("testSprite");
		SetBrightnessBrick brick = new SetBrightnessBrick(sprite, -brightnessValue);
		brick.execute();
		assertEquals("Incorrect sprite scale value after SetGhostEffectBrick executed", -brightnessValue,
				sprite.getBrightnessValue());
	}
}
