package com.example.assignment1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    final String[] THUMBNAILS = {
            "/Images/thumbnail.jpg",
            "/Images/thumbnail1.jpg",
            "/Images/thumbnail2.jpg",
            "/Images/thumbnail3.jpg",
            "/Images/thumbnail4.jpg",
            "/Images/thumbnail5.jpg",
            "/Images/thumbnail6.jpg",
            "/Images/thumbnail7.jpg",
            "/Images/thumbnail8.jpg"
    };
    final String[] FULL_IMAGES = {
            "/Images/image.jpg",
            "/Images/image1.jpg",
            "/Images/image2.jpg",
            "/Images/image3.jpg",
            "/Images/image4.jpg",
            "/Images/image5.jpg",
            "/Images/image6.jpg",
            "/Images/image7.jpg",
            "/Images/image8.jpg"
    };
    ImageView fullImageView;
    int currentImageIndex = 0;
    VBox vbox;
    Text thumbnailHeading;
    @Override
    public void start(Stage primaryStage) throws Exception {
        vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: lightgray;");

        // Heading for thumbnails
        thumbnailHeading = new Text("THUMBNAILS");
        thumbnailHeading.getStyleClass().add("heading");
        vbox.getChildren().add(thumbnailHeading);

        vbox.getChildren().add(displayThumbnails());

        Scene scene = new Scene(vbox, 800, 600);
        // Link CSS file
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Gallery");
        primaryStage.show();
    }
    private VBox displayThumbnails() {
        VBox thumbnailsBox = new VBox(10);
        thumbnailsBox.setAlignment(Pos.CENTER);

        HBox thumbnailsRow1 = new HBox(10);
        thumbnailsRow1.setAlignment(Pos.CENTER);
        HBox thumbnailsRow2 = new HBox(10);
        thumbnailsRow2.setAlignment(Pos.CENTER);
        HBox thumbnailsRow3 = new HBox(10);
        thumbnailsRow3.setAlignment(Pos.CENTER);

        for (int i = 0; i < THUMBNAILS.length; i++) {
            Image thumbnail = new Image(getClass().getResourceAsStream(THUMBNAILS[i]));
            ImageView thumbnailView = new ImageView(thumbnail);
            thumbnailView.getStyleClass().add("thumbnail-image");
            thumbnailView.setFitWidth(150); // Set width to 150 pixels
            thumbnailView.setPreserveRatio(true);
            int index = i;
            thumbnailView.setOnMouseClicked(event -> displayFullImage(FULL_IMAGES[index]));

            if (i < 3) {
                thumbnailsRow1.getChildren().add(thumbnailView);
            } else if (i < 6) {
                thumbnailsRow2.getChildren().add(thumbnailView);
            } else {
                thumbnailsRow3.getChildren().add(thumbnailView);
            }
        }
        thumbnailsBox.getChildren().addAll(thumbnailsRow1, thumbnailsRow2, thumbnailsRow3);
        return thumbnailsBox;
    }
    void displayFullImage(String imageUrl) {
        // Remove THUMBNAILS heading and thumbnails
        vbox.getChildren().remove(thumbnailHeading);

        // Remove full image view if it exists
        vbox.getChildren().removeIf(node -> node instanceof VBox);

        VBox fullImageBox = new VBox(10);
        fullImageBox.setAlignment(Pos.CENTER);

        // Heading for the full picture
        Text fullPictureHeading = new Text("FULL PICTURE");
        fullPictureHeading.getStyleClass().add("heading");
        fullImageBox.getChildren().add(fullPictureHeading);

        Image fullImage = new Image(getClass().getResourceAsStream(imageUrl));
        fullImageView = new ImageView(fullImage);
        fullImageView.getStyleClass().add("full-image");
        fullImageView.setFitWidth(600);
        fullImageView.setFitHeight(400);
        fullImageView.setPreserveRatio(true);
        fullImageBox.getChildren().add(fullImageView);

        // Navigation buttons
        Button prevButton = new Button("Previous");
        prevButton.getStyleClass().add("button");
        prevButton.setOnAction(event -> navigateToPreviousImage());
        Button nextButton = new Button("Next");

        nextButton.getStyleClass().add("button");
        nextButton.setOnAction(event -> navigateToNextImage());
        Button backButton = new Button("Back");

        backButton.getStyleClass().add("button");
        backButton.setOnAction(event -> {
            // Remove full image view
            vbox.getChildren().remove(fullImageBox);
            // Add back the THUMBNAILS heading
            vbox.getChildren().add(thumbnailHeading);
            // Add back the thumbnails
            vbox.getChildren().add(displayThumbnails());
        });

        HBox navButtonsBox = new HBox(10, prevButton, nextButton, backButton);
        navButtonsBox.setAlignment(Pos.CENTER);
        fullImageBox.getChildren().add(navButtonsBox);

        vbox.getChildren().add(fullImageBox);
    }

   void navigateToPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + FULL_IMAGES.length) % FULL_IMAGES.length;
        displayFullImage(FULL_IMAGES[currentImageIndex]);
    }

    void navigateToNextImage() {
        currentImageIndex = (currentImageIndex + 1) % FULL_IMAGES.length;
        displayFullImage(FULL_IMAGES[currentImageIndex]);
    }

    public static void main(String[] args) {
        launch();
    }
}
