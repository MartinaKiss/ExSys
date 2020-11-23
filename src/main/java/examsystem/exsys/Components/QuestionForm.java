package examsystem.exsys.Components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import examsystem.exsys.Entities.Exam;
import examsystem.exsys.Entities.Question;
import examsystem.exsys.Repositories.QuestionRepository;
import examsystem.exsys.Views.ViewComponents.Reloader;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


@SpringComponent
@UIScope
public class QuestionForm extends VerticalLayout {

    @Autowired
    private QuestionRepository questionRepository;

    private Question question;
    private Binder<Question> binder;
    private Reloader reloader;
    private TextField questionText;
    private TextField answer1;
    private TextField answer2;
    private TextField answer3;
    private TextField answer4;
    private TextField solution;
    private NumberField attainablePoints;

    @PostConstruct
    public void init() {
        binder = new Binder<>(Question.class);

        questionText = new TextField();
        attainablePoints = new NumberField();
        answer1 = new TextField();
        answer2 = new TextField();
        answer3 = new TextField();
        answer4 = new TextField();
        solution = new TextField();



        Button deleteButton = new Button();
        deleteButton.setText("Delete");
        deleteButton.setIcon(VaadinIcon.TRASH.create());
        deleteButton.addClickListener(buttonClickEvent ->
        {
            try {
                questionRepository.delete(question.getQuestionId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                reloader.reload();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Notification.show("Successfully removed!");
            setVisible(false);
        });

        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.setIcon(VaadinIcon.PENCIL.create());
        saveButton.addClickListener(buttonClickEvent ->
        {
            question.setSolution(BCrypt.hashpw(solution.getValue(), BCrypt.gensalt()));
            try {
                if (question.getQuestionId() == 0) {
                    questionRepository.save(question);

                } else {
                    questionRepository.update(question);

                }
                setVisible(false);
                reloader.reload();
            } catch (Exception e) {
                Notification.show("Caught exception: " + e);
                e.printStackTrace();
            }
            Notification.show("Successfully edited!");
            setVisible(false);
        });
        VerticalLayout container = new VerticalLayout();
        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout buttonWrapper = new HorizontalLayout();
        buttonWrapper.setAlignSelf(FlexComponent.Alignment.CENTER);
        buttonWrapper.setAlignItems(FlexComponent.Alignment.CENTER);
        buttonWrapper.add(deleteButton, saveButton);
        createFormLayout(container);
        container.add(buttonWrapper);
        add(container);
        setVisible(false);
        binder.bindInstanceFields(this);
    }

    public Question getQuestion(){
        return question;
    }

    public void setQuestion(Question question){
        if (question != null) {
            this.question = question;
            binder.setBean(this.question);
        }
        this.question = question;
    }

    public Reloader getReloader () {
        return reloader;
    }

    public void setReloader (Reloader reloader){
        this.reloader = reloader;
    }

    public void initSave (Exam exam) {
        question = new Question();
        question.setExam(exam);
        binder.setBean(question);
        setVisible(true);
    }

    public void initEdit (int id){

        try {
            this.question = questionRepository.findById(id);
            binder.setBean(question);
            setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem questionTextFormItem = addFormItem(wrapper, formLayout,
                questionText, "Kérdés szövege:");
        formLayout.setColspan(questionTextFormItem, 2);
        FormLayout.FormItem attainablePointsFormItem = addFormItem(wrapper, formLayout,
                attainablePoints, "Elérhető pontszám");
        formLayout.setColspan(attainablePointsFormItem, 1);
        FormLayout.FormItem answer1FormItem = addFormItem(wrapper, formLayout,
                answer1, "1. válasz:");
        formLayout.setColspan(answer1FormItem, 1);
        FormLayout.FormItem answer2FormItem = addFormItem(wrapper, formLayout,
                answer2, "2. válasz:");
        formLayout.setColspan(answer2FormItem, 1);
        FormLayout.FormItem answer3FormItem = addFormItem(wrapper, formLayout,
                answer3, "3. válasz:");
        formLayout.setColspan(answer3FormItem, 1);
        FormLayout.FormItem answer4FormItem = addFormItem(wrapper, formLayout,
                answer4, "4. válasz:");
        formLayout.setColspan(answer2FormItem, 1);
        FormLayout.FormItem solutionFormItem = addFormItem(wrapper, formLayout,
                solution, "Jó válasz szövege");
        formLayout.setColspan(solutionFormItem, 1);
    }

    private FormLayout.FormItem addFormItem(VerticalLayout wrapper,
                                            FormLayout formLayout, Component field, String fieldName) {
        FormLayout.FormItem formItem = formLayout.addFormItem(field, fieldName);
        field.getElement().getClassList().add("full-width");
        wrapper.add(formLayout);
        return formItem;
    }
}
