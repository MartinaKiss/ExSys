package examsystem.exsys.views.login;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import examsystem.exsys.backend.repositories.TeacherRepository;
import examsystem.exsys.views.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "login", layout = MainView.class)
@PageTitle("Bejelentkezés")
@CssImport("styles/views/vizsgalétrehozása/vizsgalétrehozása-view.css")
public class LoginView extends Div {

    private static final long serialVersionUID = 1L;
    public static final String NAME = "";

    @Autowired
    TeacherRepository teacherRepository;

    private TextField email = new TextField();
    private PasswordField password = new PasswordField();


    private Button loginButton = new Button("Bejelentkezés");

    public LoginView() {
        setId("bejelentkezés-view");

        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();

        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        container.add(wrapper);

        createTitle(wrapper, "Bejelentkezés");
        createFormLayout(wrapper);
        createButtonLayout(wrapper);

        wrapper.setWidth("90%");

        loginButton.addClickListener(e -> {
//            private static final long serialVersionUID = 1L;
//            if(VaadinloginUI.AUTH.authenticate(email.getValue(), password.getValue())){
//                VaadinSession.getCurrent().setAttribute("user", email.getValue());
//                getUI().getNavigator().addView(SecurePage.NAME, SecurePage.class);
//                getUI().getNavigator().addView(OtherSecurePage.NAME, OtherSecurePage.class);
//                Page.getCurrent().setUriFragment("!"+SecurePage.NAME);
//            }else{
//                Notification.show("Invalid credentials", Notification.Type.ERROR_MESSAGE);
//            }
        });


        add(container);
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(true);
        return wrapper;
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem emailFormItem = addFormItem(wrapper, formLayout,
                email, "Email");
        formLayout.setColspan(emailFormItem, 2);
        FormLayout.FormItem passwordFormItem = addFormItem(wrapper, formLayout,
                password, "Jelszó");
        formLayout.setColspan(passwordFormItem, 2);
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
