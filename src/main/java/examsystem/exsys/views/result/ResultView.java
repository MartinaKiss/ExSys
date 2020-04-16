package examsystem.exsys.views.result;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.views.main.MainView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Route(value = "result", layout = MainView.class)
@PageTitle("Eredmény")
@CssImport("styles/views/eredmény/eredmény-view.css")
public class ResultView extends Div {

    private String pattern = "yyyy-MM-dd hh:mm";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private Date examDate = simpleDateFormat.parse("2020-04-15 09:00");

    private String studentName = "Kiss Martina";
    private String studentNeptun = "W94PVA";
    private String studentEmail = "kissmartina97@gmail.com";
    private String examSubject = "Diszkrét Matematika II.";
    private String examName = "Félév végi zárthelyi dolgozat";
    private String teacherName = "Dr. Jenei Sándor";
    private int examResultId = 69420;
    private int attainedPoints = 69;
    private int maxPoints = 71;
    private int attainedMark = 5;

    private Button backToMain = new Button("Vissza a főoldalra");

    public ResultView() throws ParseException {
        setId("result-view");
        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();
        wrapper.setWidth("90%");
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        createTitle(wrapper,"Eredmény");
        createParagraph(wrapper, "Vizsgázó neve: ", studentName);
        createParagraph(wrapper, "Vizsgázó neptun kódja: ", studentNeptun);
        createParagraph(wrapper, "Vizsgázó email címe: ", studentEmail);
        createParagraph(wrapper, "Vizsga tárgy, neve: ", examSubject, "-", examName);
        createParagraph(wrapper, "Vizsga dátuma:", examDate.toString());
        createParagraph(wrapper, "Oktató neve: ", teacherName);
        createParagraph(wrapper, "Vizsga eredmény azonosítója: ", examResultId);
        createParagraph(wrapper, "Maximum elérhető pontok/Elért pontok: ", maxPoints, "/", attainedPoints);
        createParagraph(wrapper, "Elért jegy: ", attainedMark);
        Paragraph paragraph = new Paragraph("A fenti tanusítványt elküldtük az ön által megadott email címre. Kérjük őrízze meg az emailben kapott tanúsítványokat, hogy igazolni tudja a vizsgán való részvételét és az elért eredményét.");
        wrapper.add(paragraph);
        createButtonLayout(wrapper);
        container.add(wrapper);
        add(container);
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private void createParagraph(VerticalLayout wrapper, String text, int value){
        HorizontalLayout container = new HorizontalLayout();
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        Paragraph paragraph = new Paragraph(text);
        H4 h4 = new H4(String.valueOf(value));
        container.add(paragraph,h4);
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        wrapper.add(container);
    }

    private void createParagraph(VerticalLayout wrapper, String text, String value){
        HorizontalLayout container = new HorizontalLayout();
        Paragraph paragraph = new Paragraph(text);
        H4 h4 = new H4(String.valueOf(value));
        container.add(paragraph,h4);
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        wrapper.add(container);
    }

    private void createParagraph(VerticalLayout wrapper, String text, String value1, String value2, String value3){
        HorizontalLayout container = new HorizontalLayout();
        Paragraph paragraph = new Paragraph(text);
        H4 h4First = new H4(value1);
        H4 h4Second = new H4(value2);
        H4 h4Third = new H4(value3);
        container.add(paragraph, h4First, h4Second, h4Third);
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        wrapper.add(container);
    }

    private void createParagraph(VerticalLayout wrapper, String text, int value1, String text2, int value2){
        HorizontalLayout container = new HorizontalLayout();
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        Paragraph paragraph = new Paragraph(text);
        H4 h4Third = new H4(text2);
        H4 h4First = new H4(String.valueOf(value1));
        H4 h4Second = new H4(String.valueOf(value2));
        container.add(paragraph,h4First, h4Third, h4Second);
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        wrapper.add(container);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(false);
        return wrapper;
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidth("100%");
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        backToMain.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(backToMain);
        wrapper.add(buttonLayout);
    }
}
