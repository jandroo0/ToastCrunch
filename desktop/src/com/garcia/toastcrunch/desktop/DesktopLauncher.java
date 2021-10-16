package com.garcia.toastcrunch.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.garcia.toastcrunch.ToastCrunchGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Slash and Dash";
		config.useGL30 = true;
		config.width = 1440;
		config.height = 720;
		new LwjglApplication(new ToastCrunchGame(), config);
	}
}
