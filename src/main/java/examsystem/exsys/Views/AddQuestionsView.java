package examsystem.exsys.Views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import examsystem.exsys.Components.QuestionForm;
import examsystem.exsys.ExamElements.Exam;
import examsystem.exsys.ExamElements.Question;
import examsystem.exsys.Repositories.ExamRepository;
import examsystem.exsys.Repositories.QuestionRepository;
import examsystem.exsys.Views.ViewComponents.Reloader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Route(value = "addquestions", layout = SecondaryTemplateView.class)
@PageTitle("Kérdések hozzáadása")
@CssImport("styles/views/tvkérdésekhozzáadása/t-vkérdésekhozzáadása-view.css")
public class AddQuestionsView extends Div implements HasUrlParameter<String>, Reloader, AfterNavigationObserver{

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionForm form;

    private Grid<Question> grid;

    private Exam exam;

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
        VerticalLayout container = new VerticalLayout();

        HorizontalLayout buttonContainer = new HorizontalLayout();
        VerticalLayout buttonWrapper1 = new VerticalLayout();
        VerticalLayout buttonWrapper2 = new VerticalLayout();

        grid = new Grid<>(Question.class);
        setHeightFull();
        grid.removeAllColumns();
        grid.addColumn(Question::getQuestionText).setHeader("Kérdés szövege").setAutoWidth(true);
        grid.addColumn(Question::getAttainablePoints).setHeader("Elérhető pontszám").setAutoWidth(true);
        grid.addColumn(Question::getAnswer1).setHeader("1. válasz").setAutoWidth(true);
        grid.addColumn(Question::getAnswer2).setHeader("2. válasz").setAutoWidth(true);
        grid.addColumn(Question::getAnswer3).setHeader("3. válasz").setAutoWidth(true);
        grid.addColumn(Question::getAnswer4).setHeader("4. válasz").setAutoWidth(true);
        grid.addColumn(Question::getSolution).setHeader("Megoldás").setAutoWidth(true);


        grid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() != null) {
                form.initEdit(e.getValue().getQuestionId());
            }
        });
        Button addButton = new Button("Új kérdés hozzáadása", VaadinIcon.PLUS.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClickListener(event -> form.initSave(exam));

        Button nextButton = new Button("Következő", VaadinIcon.ARROW_RIGHT.create());
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        nextButton.addClickListener(event -> {
            try {
                List<Question> questionList = new ArrayList<>(questionRepository.findAllByExamId(exam.getExamId()));
                double maxSumOfPoints = 0.0;
                for (Question question : questionList) {
                    maxSumOfPoints = maxSumOfPoints + question.getAttainablePoints();
                }
                System.out.println("Max sum of points: " + maxSumOfPoints);
                exam.setMaxSumOfPoints(maxSumOfPoints);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            examRepository.update(exam);
            UI.getCurrent().navigate("addotherdataview/" + exam.getExamId());
        });

        buttonWrapper1.add(addButton);
        buttonWrapper1.setAlignItems(FlexComponent.Alignment.START);
        buttonWrapper2.add(nextButton);
        buttonWrapper2.setAlignItems(FlexComponent.Alignment.END);
        buttonContainer.add(buttonWrapper1, buttonWrapper2);
        buttonContainer.setVerticalComponentAlignment(FlexComponent.Alignment.STRETCH);
        buttonContainer.setAlignItems(FlexComponent.Alignment.STRETCH);
        buttonContainer.setWidth("100%");
        form.setReloader(this);
        container.add(grid);
        container.add(buttonContainer);
        container.setAlignItems(FlexComponent.Alignment.CENTER);
        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setAlignSelf(FlexComponent.Alignment.CENTER);
        add(container);
        add(form);
    }

    @Override
    public void reload() throws Exception {
        grid.setItems(questionRepository.findAllByExamId(exam.getExamId()));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        try {
            grid.setItems(questionRepository.findAllByExamId(exam.getExamId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
