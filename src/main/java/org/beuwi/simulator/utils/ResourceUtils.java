package org.beuwi.simulator.utils;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ResourceUtils
{
	public static InputStream getFont(String name)
	{
		try {
			return new FileInputStream(new File("src/main/resources/fonts/" + name + ".ttf"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Image getImage(String name) {
		try {
			return new Image((new File("src/main/resources/images/" + name + ".png").toURI()).toURL().toExternalForm());
		}catch (MalformedURLException e){
			return null;
		}
	}

	public static URL getForm(String name) {
		try {
			URL location = (new File("src/main/resources/forms/" + name + ".fxml").toURI()).toURL();
			return location;
		}catch (MalformedURLException e){
			return null;
		}
	}

	public static String getStyle(String name)
	{
		try {
			return (new File("src/main/resources/styles/" + name + ".css").toURI()).toURL().toExternalForm();
		}catch (MalformedURLException e){
			return null;
		}
	}

	public static String getTheme(String name)
	{
		try {
			return (new File("src/main/resources/themes/" + name + ".css").toURI()).toURL().toExternalForm();
		}catch (MalformedURLException e){
			return null;
		}
	}

	public static URL getSVG(String name)
	{
		try {
			return (new File("src/main/resources/svgs/" + name + ".svg").toURI()).toURL();
		}catch (MalformedURLException e){
			return null;
		}
	}
}