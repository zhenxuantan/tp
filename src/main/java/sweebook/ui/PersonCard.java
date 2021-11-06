package sweebook.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import sweebook.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane groups;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Hyperlink telegram;
    @FXML
    private Hyperlink github;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);

        person.getGroups().stream()
                .sorted(Comparator.comparing(group -> group.group))
                .forEach(group -> groups.getChildren().add(new Label(group.group)));

        telegram.setText("Telegram: " + person.getTelegram().toString());
        String telegramUrl = person.getTelegram().toUrl();
        telegram.setOnAction(e -> {
            openWebpage(telegramUrl);
        });

        github.setText("GitHub: " + person.getGitHub().toString());
        String githubUrl = person.getGitHub().toUrl();
        github.setOnAction(e -> {
            openWebpage(githubUrl);
        });
    }

    /**
     * Loads a webpage by the specified url on the user's default browser
     * Adapted from https://stackoverflow.com/a/5226244
     *
     * @param url The url to load.
     */
    public static void openWebpage(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
