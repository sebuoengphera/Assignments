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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private static final String[] THUMBNAILS = {
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
    private static final String[] FULL_IMAGES = {
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
    private ImageView fullImageView;
    private int currentImageIndex = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        // Heading for thumbnail pictures
        Text thumbnailHeading = new Text("Thumbnail Pictures");
        thumbnailHeading.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        root.getChildren().add(thumbnailHeading);

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
            thumbnailView.setFitWidth(150); // Set width to 150 pixels
            thumbnailView.setPreserveRatio(true);
            int index = i; // For lambda expression
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
        root.getChildren().add(thumbnailsBox);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Gallery");
        primaryStage.show();
    }
    private void displayFullImage(String imageUrl) {
        Image fullImage = new Image(getClass().getResourceAsStream(imageUrl));
        fullImageView = new ImageView(fullImage);
        fullImageView.setFitWidth(600);
        fullImageView.setFitHeight(400);
        fullImageView.setPreserveRatio(true);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: lightgray;");

        // Heading for the full picture
        Text fullPictureHeading = new Text("The Full Picture");
        fullPictureHeading.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vbox.getChildren().addAll(fullPictureHeading, fullImageView);

        // Navigation buttons
        Button prevButton = new Button("Previous");
        prevButton.setOnAction(event -> navigateToPreviousImage());
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> navigateToNextImage());

        HBox navButtonsBox = new HBox(10, prevButton, nextButton);
        navButtonsBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(navButtonsBox);

        Stage stage = new Stage();
        stage.setScene(new Scene(vbox));
        stage.setTitle("Full Image");
        stage.show();
    }

    private void navigateToPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + FULL_IMAGES.length) % FULL_IMAGES.length;
        displayFullImage(FULL_IMAGES[currentImageIndex]);
    }

    private void navigateToNextImage() {
        currentImageIndex = (currentImageIndex + 1) % FULL_IMAGES.length;
        displayFullImage(FULL_IMAGES[currentImageIndex]);
    }
    public static void main(String[] args) {
        launch();
    }
}