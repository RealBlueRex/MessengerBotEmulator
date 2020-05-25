package org.beuwi.simulator.platform.application.views.dialogs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import org.beuwi.simulator.platform.ui.dialog.IDialogBoxType.DOCUMENT;
import org.beuwi.simulator.platform.ui.dialog.IDialogBoxView;
import org.beuwi.simulator.utils.ResourceUtils;

import java.net.MalformedURLException;

public class ShowEventDialog extends IDialogBoxView
{
	@FXML private Label label;

	private String title, message;

	public ShowEventDialog(String title, String message) {
		super(DOCUMENT.EVENT);
		this.title = title;
		this.message = message;
	}

	public void display() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ResourceUtils.getForm("ShowEventDialog"));
		loader.setController(this);

		Region root = null;

		try
		{
			root = loader.load();
		}
		catch (Exception e)
		{
			new ShowErrorDialog(e).display();
		}

		initialize();

		setUseButton(true, false);
		setContent(root);
		setTitle(title);
		create();
	}

	private void initialize()
	{
		label.setText(message);
	}
}