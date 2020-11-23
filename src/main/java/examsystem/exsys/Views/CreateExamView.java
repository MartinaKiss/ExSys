package examsystem.exsys.Views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import examsystem.exsys.Entities.Teacher;
import examsystem.exsys.Entities.Exam;
import examsystem.exsys.Repositories.ExamRepository;
import examsystem.exsys.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route(value = "createexam", layout = MainTemplateView.class)
@PageTitle("Vizsga létrehozása")
@CssImport("styles/views/vizsgalétrehozása/vizsgalétrehozása-view.css")
public class CreateExamView extends Div implements HasUrlParameter<String> {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    ExamRepository examRepository;

    private Teacher teacher;
    private Exam newExam;
    private TextField examName = new TextField();
    private TextField examSubject = new TextField();
    private Button nextButton = new Button("Tovább");

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
        if(s != null) {
            try {
                newExam = examRepository.findById(Integer.parseInt(s));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            newExam = new Exam();
        }
    }

    @PostConstruct
    public void init() {
        int teacherId = (int) UI.getCurrent().getSession().getAttribute("teacher");
        teacher = teacherRepository.findById(teacherId);

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
            newExam.setTeacher(teacher);
            newExam.setExamName(examName.getValue());
            newExam.setSubject(examSubject.getValue());
            if(newExam.getExamId() == 0) {
                examRepository.save(newExam);
            }
            else{
                examRepository.update(newExam);
            }
            UI.getCurrent().navigate("addquestions/" + newExam.getExamId());
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
