package org.beuwi.simulator.platform.application.views.dialogs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import org.beuwi.simulator.platform.application.views.actions.RenameBotAction;
import org.beuwi.simulator.platform.ui.dialog.IDialogBoxType.DOCUMENT;
import org.beuwi.simulator.platform.ui.dialog.IDialogBoxView;
import org.beuwi.simulator.utils.ResourceUtils;

import java.net.MalformedURLException;

public class RenameBotDialog extends IDialogBoxView
{
	@FXML private Label label;
	@FXML private TextField field;

	private Button btnRename;
	private Button btnCancel;

	private String name;

	public RenameBotDialog(String name) throws MalformedURLException {
		super(DOCUMENT.EVENT);
		this.name = name;
	}

	public void display() throws MalformedURLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ResourceUtils.getForm("RenameBotDialog"));
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

		setUseButton(true, true);
		setContent(root);
		setTitle("Rename");
		create();
	}

	private void initialize()
	{
		btnRename = getOKButton();
        btnCancel = getNOButton();

        btnRename.setText("Rename");
        btnCancel.setText("Cancel");

        btnRename.setOnAction(event ->
        {
			try {
				action();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		});

        field.textProperty().addListener((observable, oldString, newString) ->
        {
            btnRename.setDisable(newString.isEmpty());
        });

        setOnKeyPressed(event ->
        {
            if (event.getCode().equals(KeyCode.ENTER))
            {
                if (!btnRename.isDisable())
                {
					try {
						action();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
            }
        });

		label.setText("Rename bot '" + name + "'?");
		field.setText(name);
      	field.requestFocus();
	}

	private void action() throws MalformedURLException {
		RenameBotAction.update(name, field.getText()); close();
	}
}