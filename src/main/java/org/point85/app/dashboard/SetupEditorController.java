package org.point85.app.dashboard;

import org.point85.app.AppUtils;
import org.point85.app.ImageManager;
import org.point85.app.Images;
import org.point85.app.LoaderFactory;
import org.point85.app.material.MaterialEditorController;
import org.point85.domain.collector.SetupEvent;
import org.point85.domain.persistence.PersistenceService;
import org.point85.domain.plant.Material;
import org.point85.domain.script.EventResolverType;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SetupEditorController extends EventEditorController {
	private SetupEvent setupEvent;

	// material editor
	private MaterialEditorController materialController;

	@FXML
	private Button btMaterialEditor;

	@FXML
	private Label lbMaterial;

	@FXML
	private TextField tfJob;

	public void initializeEditor(SetupEvent event) throws Exception {
		setupEvent = event;
		setupEvent.setResolverType(EventResolverType.MATL_CHANGE);

		// images for buttons
		setImages();

		getDialogStage().setOnShown((we) -> {
			displayAttributes();
		});
	}

	@Override
	protected void setImages() throws Exception {
		super.setImages();

		btMaterialEditor.setGraphic(ImageManager.instance().getImageView(Images.MATERIAL));
		btMaterialEditor.setTooltip(new Tooltip("Find material."));
	}

	@Override
	protected void saveRecord() throws Exception {
		// time period
		setTimePeriod(setupEvent);

		// reason
		if (setupEvent.getMaterial() == null) {
			throw new Exception("A material must be specified.");
		}
		
		// job
		setupEvent.setJob(tfJob.getText());

		PersistenceService.instance().save(setupEvent);
	}

	private void displayMaterial() {
		if (setupEvent.getMaterial() != null) {
			lbMaterial.setText(setupEvent.getMaterial().getDisplayString());
		} else {
			lbMaterial.setText(null);
		}
	}

	@FXML
	private void onShowMaterialEditor() {
		try {
			// display the material editor as a dialog
			if (materialController == null) {
				FXMLLoader loader = LoaderFactory.materialEditorLoader();
				AnchorPane page = (AnchorPane) loader.getRoot();

				// Create the dialog Stage.
				Stage dialogStage = new Stage(StageStyle.DECORATED);
				dialogStage.setTitle("Setup Material");
				dialogStage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(page);
				dialogStage.setScene(scene);

				// get the controller
				materialController = loader.getController();
				materialController.setDialogStage(dialogStage);
				materialController.initialize(null);
			}

			// Show the dialog and wait until the user closes it
			materialController.getDialogStage().showAndWait();

			Material material = materialController.getSelectedMaterial();
			setupEvent.setMaterial(material);
			displayMaterial();

		} catch (Exception e) {
			AppUtils.showErrorDialog(e);
		}
	}

	void displayAttributes() {
		// start date and time
		super.displayAttributes(setupEvent);
		
		// material
		displayMaterial();
		
		// job
		tfJob.setText(setupEvent.getJob());
	}
}