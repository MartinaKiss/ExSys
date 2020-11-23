package examsystem.exsys.Views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.Entities.Exam;
import examsystem.exsys.Repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

@Route(value = "", layout = MainTemplateView.class)
@PageTitle("Főoldal")
@CssImport("styles/views/kapcsolat/kapcsolat-view.css")
public class MainView extends Div {

    @Autowired
    private ExamRepository examRepository;

    private TextField examCode = new TextField();
    private Button goToExam = new Button("Tovább a vizsgára!");

    public MainView() {
        setId("főoldal-view");
        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();

        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        createTitle(wrapper,"Üdv az ExSys online vizsgarendszer oldalán");
        createLabel(wrapper, "A bejelentkezés menüpontra kattinva bejelenkezhet tanárként, ha pedig diákként szeretne vizsgázni, akkor azt megteheti alább a vizsga kódjának megadásával.");

        VerticalLayout fieldContainer = new VerticalLayout();
        fieldContainer.getElement().getStyle().set("padding-left", "0");
        VerticalLayout buttonContainer = new VerticalLayout();
        buttonContainer.getElement().getStyle().set("padding-left", "0");
        createFormLayout(fieldContainer);
        createButtonLayout(buttonContainer);
        wrapper.add(fieldContainer, buttonContainer);
        wrapper.setWidth("90%");
        container.add(wrapper);
        goToExam.addClickListener(e -> {
            try {
                Exam destinationExam = examRepository.findByEnterExamCode(examCode.getValue());
                UI.getCurrent().navigate("takeexam/" + destinationExam.getEnterExamCode());
            }catch (EmptyResultDataAccessException exception){
                Notification.show("Helytelen beléptető kód.");
            }
        });
        add(container);
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private void createLabel(VerticalLayout wrapper, String text){
        Label label = new Label(text);
        label.getElement().getStyle()
                .set("font-size", "18px");
        wrapper.add(label);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(true);
        return wrapper;
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem examCodeFormItem = addFormItem(wrapper, formLayout,
                examCode, "Vizsga kódja:");
        formLayout.setColspan(examCodeFormItem, 1);
        wrapper.add(formLayout);
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        goToExam.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(goToExam);
        wrapper.add(buttonLayout);
    }

    private FormLayout.FormItem addFormItem(VerticalLayout wrapper,
            FormLayout formLayout, Component field, String fieldName) {
        FormLayout.FormItem formItem = formLayout.addFormItem(field, fieldName);
        field.getElement().getClassList().add("full-width");
        wrapper.add(formLayout);
        return formItem;
    }

}
