package examsystem.exsys.views.createexam;

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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.backend.repositories.TeacherRepository;
import examsystem.exsys.views.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "createexam", layout = MainView.class)
@PageTitle("Vizsga létrehozása")
@CssImport("styles/views/vizsgalétrehozása/vizsgalétrehozása-view.css")
public class CreateExamView extends Div {

    private static final long serialVersionUID = 1L;
    public static final String NAME = "Secure";

    @Autowired
    TeacherRepository teacherRepository;

    private TextField examName = new TextField();
    private TextField examSubject = new TextField();
    private NumberField numberOfQuestions = new NumberField();

    private Button nextButton = new Button("Tovább");

    public CreateExamView() {
        setId("vizsgalétrehozása-view");
        VerticalLayout wrapper = createWrapper();

        createTitle(wrapper);
        Paragraph paragraph = new Paragraph("Itt hozhat létre új vizsgát. Először is adja meg a vizsga alap adatait" +
                " és azt, hogy mennyi kérdést fog tartalmazni a feladatlap. A Tovább gombra kattintva egy új lapon adhatja" +
                " majd meg a kérdéseket és a hozzájuk tartozó válaszokat, illetve a kérésekért járó pontok számát. A" +
                " ponthatárokat és minden egyéb szükséges adatot a kérdések felvitele után kell majd megadnia.");
        wrapper.add(paragraph);
        createFormLayout(wrapper);
        createButtonLayout(wrapper);

        wrapper.setWidth("90%");

        VerticalLayout container = new VerticalLayout();

        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        container.add(wrapper);

        nextButton.addClickListener(e -> {
            Notification.show("Not implemented");
        });

        add(container);
    }

    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Vizsga létrehozása");
        wrapper.add(h1);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(false);
        return wrapper;
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem examNameFormItem = addFormItem(wrapper, formLayout,
                examName, "Vizsga név");
        formLayout.setColspan(examNameFormItem, 2);
        FormLayout.FormItem examSubjectFormItem = addFormItem(wrapper, formLayout,
                examSubject, "Vizsga tárgy");
        formLayout.setColspan(examSubjectFormItem, 2);
        FormLayout.FormItem numberOfQuestionsFormItem = addFormItem(wrapper, formLayout,
                numberOfQuestions, "Kérdések száma");
        formLayout.setColspan(numberOfQuestionsFormItem, 1);

    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(nextButton);
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
