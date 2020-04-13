package examsystem.exsys.views.register;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.backend.repositories.TeacherRepository;
import examsystem.exsys.views.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "register", layout = MainView.class)
@PageTitle("Regisztráció")
@CssImport("styles/views/vizsgalétrehozása/vizsgalétrehozása-view.css")
public class RegisterView extends Div {

    @Autowired
    TeacherRepository teacherRepository;

    private TextField fullName = new TextField();
    private TextField neptuneCode = new TextField();
    private TextField email = new TextField();
    private PasswordField password = new PasswordField();


    private Button loginButton = new Button("Bejelentkezés");

    public RegisterView() {
        setId("bejelentkezés-view");

        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();

        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        container.add(wrapper);

        createTitle(wrapper, "Regisztráció");
        createParagraph(wrapper, "Az alábbi adatlap kitöltésével regisztrálhat az ExSys online vizsgarendszerbe.");
        createFormLayout(wrapper);
        createButtonLayout(wrapper);

        wrapper.setWidth("90%");

        loginButton.addClickListener(e -> {
            Notification.show("Not implemented");
        });


        add(container);
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private void createParagraph(VerticalLayout wrapper, String text){
        Paragraph paragraph = new Paragraph(text);
        wrapper.add(paragraph);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(true);
        return wrapper;
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem fullNameFormItem = addFormItem(wrapper, formLayout,
                fullName, "Teljes Név");
        formLayout.setColspan(fullNameFormItem, 2);
        FormLayout.FormItem emailFormItem = addFormItem(wrapper, formLayout,
                email, "Email");
        formLayout.setColspan(emailFormItem, 2);
        FormLayout.FormItem neptuneCodeFormItem = addFormItem(wrapper, formLayout,
                neptuneCode, "Neptun kód");
        formLayout.setColspan(neptuneCodeFormItem, 1);
        FormLayout.FormItem passwordFormItem = addFormItem(wrapper, formLayout,
                password, "Jelszó");
        formLayout.setColspan(passwordFormItem, 1);
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidth("100%");
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(loginButton);
        wrapper.add(buttonLayout);
    }

    private FormLayout.FormItem addFormItem(VerticalLayout wrapper,
            FormLayout formLayout, Component field, String fieldName) {
        FormLayout.FormItem formItem = formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
        return formItem;
    }

}
