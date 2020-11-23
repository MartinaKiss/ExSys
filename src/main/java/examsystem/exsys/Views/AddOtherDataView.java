package examsystem.exsys.Views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.Entities.Exam;
import examsystem.exsys.Repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route(value = "addotherdataview", layout = SecondaryTemplateView.class)
@PageTitle("EV kérdések hozzáadása")
@CssImport("styles/views/evkérdésekhozzáadása/e-vkérdésekhozzáadása-view.css")
public class AddOtherDataView extends Div implements HasUrlParameter<String> {

    @Autowired
    ExamRepository examRepository;

    private Checkbox isWrongAnswerMinusPoint  = new Checkbox();
    private NumberField valueOfMinusPoint = new NumberField();
    private NumberField gradeFivePointLimit = new NumberField();
    private NumberField gradeFourPointLimit = new NumberField();
    private NumberField gradeThreePointLimit = new NumberField();
    private NumberField gradeTwoPointLimit = new NumberField();
    private TextArea examDescription = new TextArea();

    private Exam exam;

    private Button save = new Button("Mentés", VaadinIcon.ARROW_RIGHT.create());

    private Binder<Exam> binder;


    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        try {
            exam = examRepository.findById(Integer.parseInt(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        setId("addotherdataview-view");

        binder = new Binder<>(Exam.class);

        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();

        createTitle(wrapper, "Egyéb adatok megadása");
        Paragraph description = new Paragraph("Itt tudja megadni a vizsga ponthatárait százalékban, azt, hogy a " +
                "helytelen válaszok pontlevonással járnak-e és a levont pontok számát. A Tájékozató mezőbe írhatja bele " +
                "a diákjainak szóló tájékoztatót a vizsgával kapcsolatban (fontos tudnivalók, mire érdemes figyelni, stb.). " +
                "A Mentés gombra kattintva a vizsga mentésre kerül (a vizsgákat bármikor szerkesztheti), a Vissza gombra " +
                "kattintva vissza léphet az előző oldalra és szerkesztheti az ott megadott adatokat.");
        wrapper.add(description);
        createFormLayout(wrapper);
        createButtonLayout(wrapper);
        wrapper.setWidth("90%");

        container.add(wrapper);
        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        save.addClickListener(e -> {
            exam.setWrongAnswerMinusPoint(isWrongAnswerMinusPoint.getValue());
            exam.setValueOfMinusPoint(valueOfMinusPoint.getValue());
            exam.setGradeFivePointLimit(gradeFivePointLimit.getValue());
            exam.setGradeFourPointLimit(gradeFourPointLimit.getValue());
            exam.setGradeThreePointLimit(gradeThreePointLimit.getValue());
            exam.setGradeTwoPointLimit(gradeTwoPointLimit.getValue());
            exam.setDescription(examDescription.getValue());
            examRepository.update(exam);
            UI.getCurrent().navigate(MyExamsView.class);
        });
        binder.bindInstanceFields(this);
        add(container);
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
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
        FormLayout.FormItem isWrongAnswerMinusPointFormItem = addFormItem(wrapper, formLayout,
                isWrongAnswerMinusPoint, "A rossz válaszért kerüljön pont levonásra?");
        formLayout.setColspan(isWrongAnswerMinusPointFormItem, 1);
        FormLayout.FormItem amountOfMinusPointFormItem = addFormItem(wrapper, formLayout,
                valueOfMinusPoint, "Levonásra kerülő pontok száma");
        formLayout.setColspan(amountOfMinusPointFormItem, 1);
        FormLayout.FormItem markFiveLimitFormItem = addFormItem(wrapper, formLayout,
                gradeFivePointLimit, "5-ös ponthatár");
        formLayout.setColspan(markFiveLimitFormItem, 1);
        FormLayout.FormItem markFourLimitFormItem = addFormItem(wrapper, formLayout,
                gradeFourPointLimit, "4-es ponthatár");
        formLayout.setColspan(markFourLimitFormItem, 1);
        FormLayout.FormItem markThreeLimitFormItem = addFormItem(wrapper, formLayout,
                gradeThreePointLimit, "3-as ponthatár");
        formLayout.setColspan(markThreeLimitFormItem, 1);
        FormLayout.FormItem markTwoLimitFormItem = addFormItem(wrapper, formLayout,
                gradeTwoPointLimit, "2-es ponthatár");
        formLayout.setColspan(markTwoLimitFormItem, 1);
        FormLayout.FormItem descriptionFormItem = addFormItem(wrapper, formLayout,
                examDescription, "Leírás");
        formLayout.setColspan(descriptionFormItem, 2);

        wrapper.add(formLayout);
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
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
