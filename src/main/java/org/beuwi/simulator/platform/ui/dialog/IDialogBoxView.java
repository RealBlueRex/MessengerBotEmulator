package org.beuwi.simulator.platform.ui.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.beuwi.simulator.platform.ui.components.IButton;
import org.beuwi.simulator.platform.ui.dialog.IDialogBoxType.DOCUMENT;
import org.beuwi.simulator.platform.ui.window.IWindowType;
import org.beuwi.simulator.platform.ui.window.IWindowView;
import org.beuwi.simulator.utils.ResourceUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class IDialogBoxView extends StackPane implements Initializable
{
	@FXML private BorderPane brpRootPane;
	@FXML private ImageView  imvDialogIcon;
	@FXML private HBox	 	 hbxButtonBox;

	@FXML private IButton btnOK;
	@FXML private IButton btnNO;

	final IWindowView window;

	final Stage stage;

	IDialogBoxType type;
	DOCUMENT document;
	Region content;
	String title;

	// Document Type
	public IDialogBoxView(DOCUMENT document) {
		this(IDialogBoxType.DOCUMENT);

		this.document = document;
	}

	public IDialogBoxView(IDialogBoxType type) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ResourceUtils.getForm("DialogBoxView"));
		loader.setController(this);

		try
		{
			loader.load();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		this.type  = type;
		this.stage = new Stage();
		this.window = new IWindowView(stage);
	}

	public void setUseButton(boolean ok, boolean no)
	{
		if (!ok) hbxButtonBox.getChildren().remove(btnOK);
		if (!no) hbxButtonBox.getChildren().remove(btnNO);
	}

	public void setContent(Region content)
	{
		this.content = content;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public IButton getOKButton()
	{
		return btnOK;
	}

	public IButton getNOButton()
	{
		return btnNO;
	}

	public void create() {
		switch (type)
		{
			case APPLICATION :
				brpRootPane.getChildren().remove(brpRootPane.getLeft());
				setMinSize(content.getMinWidth(), content.getMinHeight() + 68);
				setPrefSize(content.getPrefWidth(), content.getPrefHeight() + 68);
				break;

			case DOCUMENT :

				imvDialogIcon.setImage
				(
					switch (document)
					{
						case ERROR   -> ResourceUtils.getImage("error_big");
						case WARNING -> ResourceUtils.getImage("warning_big");
						case EVENT   -> ResourceUtils.getImage("event_big");
						default 	 -> null;
					}
				);

				setMinSize(content.getMinWidth() + 70, content.getMinHeight() + 68);
				setPrefSize(content.getPrefWidth() + 70, content.getPrefHeight() + 68);
				break;
		};

		brpRootPane.setCenter(content);

		btnOK.setType(IButton.ACTION);

		getChildren().add(brpRootPane);
		getStyleClass().add("dialog");
		getStylesheets().add(ResourceUtils.getStyle("DialogBoxView"));

		stage.addEventFilter(KeyEvent.KEY_PRESSED, event ->
		{
			switch (event.getCode())
			{
				case ESCAPE : stage.close(); break;
			}
		});

		window.setContent(this);
		window.setType(IWindowType.DIALOG);
		window.setTitle(title);
		window.create();
		window.show();
	}

	public void close()
	{
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		btnOK.addEventHandler(ActionEvent.ACTION, event ->
		{
			stage.close();
		});

		btnNO.addEventHandler(ActionEvent.ACTION, event ->
		{
			stage.close();
		});
	}
}