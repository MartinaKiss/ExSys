package examsystem.exsys.views.home;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.views.main.MainView;

@Route(value = "home", layout = MainView.class)
@PageTitle("Főoldal")
@CssImport("styles/views/kapcsolat/kapcsolat-view.css")
public class HomeView extends Div {

    private TextField fullName = new TextField();
    private TextField neptuneCode = new TextField();
    private TextField email = new TextField();
    private TextField examCode = new TextField();

    private Button goToExam = new Button("Tovább a vizsgára!");

    public HomeView() {
        setId("főoldal-view");
        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        createTitle(wrapper,"Üdv az ExSys online vizsgarendszer oldalán");
        createParagraph(wrapper, "A bejelentkezés menüpontra kattinva bejelenkezhet tanárként, ha pedig diákként szeretne vizsgázni, akkor azt megteheti az alábbi adatlap kitöltésével.");
        createFormLayout(wrapper);
        createButtonLayout(wrapper);
        wrapper.setWidth("90%");
        container.add(wrapper);
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
        wrapper.setSpacing(false);
        return wrapper;
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem emailFormItem = addFormItem(wrapper, formLayout,
                fullName, "Teljes Név");
        formLayout.setColspan(emailFormItem, 2);
        FormLayout.FormItem notesFormItem = addFormItem(wrapper, formLayout,
                email, "Email");
        formLayout.setColspan(notesFormItem, 2);
        addFormItem(wrapper, formLayout, neptuneCode, "Neptun Kód");
        addFormItem(wrapper, formLayout, examCode, "Vizsga Kódja");


    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        goToExam.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(goToExam);
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
