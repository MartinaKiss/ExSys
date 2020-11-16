package examsystem.exsys.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import examsystem.exsys.Entities.Teacher;
import examsystem.exsys.ExamElements.ExamResult;
import examsystem.exsys.Repositories.ResultRepository;
import examsystem.exsys.Repositories.TeacherRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Route(value = "result", layout = SecondaryTemplateView.class)
@PageTitle("Eredmény")
@CssImport("styles/views/eredmény/eredmény-view.css")
public class ResultView extends Div implements HasUrlParameter<String>, AfterNavigationObserver {

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    TeacherRepository teacherRepository;

    private ExamResult examResult;
    private Teacher teacher;

    private VerticalLayout wrapper;

    private Button backToMain = new Button("Vissza a főoldalra");

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        try {
            examResult = resultRepository.findById(Integer.parseInt(s));
            teacher = teacherRepository.findById(examResult.getTeacherId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        try {
            createTitle(wrapper,"Eredmény");
            createParagraph(wrapper, "Vizsgázó neve: ", examResult.getStudentName());
            createParagraph(wrapper, "Vizsgázó neptun kódja: ", examResult.getStudentNeptun());
            createParagraph(wrapper, "Vizsgázó email címe: ", examResult.getStudentEmail());
            createParagraph(wrapper, "Vizsga tárgya, neve: ", examResult.getSubject() + " - " + examResult.getExamName());
            createParagraph(wrapper, "Vizsga dátuma: ", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(examResult.getTimeOfSubmission()));
            if(teacher.getTeacherTitle().equals("")) {
                createParagraph(wrapper, "Oktató neve: ", teacher.getTeacherLastName() + " " + teacher.getTeacherFirstName());
            }
            else {
                createParagraph(wrapper, "Oktató neve: ", teacher.getTeacherTitle() + " " + teacher.getTeacherLastName() + " " + teacher.getTeacherFirstName());
            }
            createParagraph(wrapper, "Maximum elérhető pontok / Elért pontok: ", examResult.getSumOfMaxPoints() + "/" + examResult.getSumOfAttainedPoints());
            createParagraph(wrapper, "Elért jegy: ", examResult.getAttainedGrade());

            Paragraph paragraph1 = new Paragraph("A fenti tanusítványt kérjük töltse le az alábbi gombra kattintva:");
            wrapper.add(paragraph1);

            Button download = new Button("Tanusítvány letöltése");
            download.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(
                    new StreamResource(String.valueOf(examResult.getExamResultId()).concat(".pdf"), () -> {
                        try {
                            return new ByteArrayInputStream(getPDF());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
            );

            buttonWrapper.wrapComponent(download);

            wrapper.add(buttonWrapper);

            Paragraph paragraph2 = new Paragraph("A letöltött tanusítványt őrízze meg a jegy rögzítéséig, hogy igazolni tudja a vizsgán való részvételét és az elért eredményét.");
            wrapper.add(paragraph2);
            createButtonLayout(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultView() {
        setId("result-view");
        wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();
        wrapper.setWidth("90%");
        container.setAlignItems(FlexComponent.Alignment.CENTER);
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

    private byte[] getPDF() throws IOException {

        List<String> stringList = new ArrayList<>();

        stringList.add("Vizsgázó neve: " + examResult.getStudentName());
        stringList.add("Vizsgázó neptun kódja: " + examResult.getStudentNeptun());
        stringList.add("Vizsgázó email címe: " + examResult.getStudentEmail());
        stringList.add("Vizsga tárgya, neve: " + examResult.getSubject() + " - " + examResult.getExamName());
        stringList.add("Vizsga dátuma: " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(examResult.getTimeOfSubmission()));
        if(teacher.getTeacherTitle().equals("")) {
            stringList.add("Oktató neve: " + teacher.getTeacherLastName() + " " + teacher.getTeacherFirstName());
        }
        else {
            stringList.add("Oktató neve: " + teacher.getTeacherTitle() + " " + teacher.getTeacherLastName() + " " + teacher.getTeacherFirstName());
        }
        stringList.add("Maximum elérhető pontok / Elért pontok: " + examResult.getSumOfMaxPoints() + "/" + examResult.getSumOfAttainedPoints());
        stringList.add("Elért jegy: " + examResult.getAttainedGrade());

        PDDocument document = new PDDocument();
        PDPage page= new PDPage();
        File sansFontFile = new File("src/main/java/examsystem/exsys/Views/ttf/DejaVuLGCSans.ttf");
        File boldFontFile = new File("src/main/java/examsystem/exsys/Views/ttf/DejaVuLGCSans-Bold.ttf");
        PDImageXObject logoImage = PDImageXObject.createFromFile("src/main/webapp/frontend/logoLightMode.png", document);
        PDFont dejaVuSansFont = PDType0Font.load(document, sansFontFile);
        PDFont dejaVuBoldFont = PDType0Font.load(document, boldFontFile);
        int mainFontSize = 12;
        int titleFontSize = 20;
        int footerFontSize = 8;
        
        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            document.addPage(page);
            contentStream.drawImage(logoImage, 50f, 700f, 103.5f, 40.45f);
            contentStream.setStrokingColor(0, 43, 77);
            contentStream.setNonStrokingColor(0, 43, 77);
            contentStream.setLineWidth(3f);
            contentStream.moveTo(50f, 690f);
            contentStream.lineTo(550f, 690f);
            contentStream.stroke();

            contentStream.moveTo(50f, 100f);
            contentStream.lineTo(550f, 100f);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.setFont(dejaVuBoldFont, titleFontSize);
            contentStream.newLineAtOffset(160.5f, 708.5f);
            contentStream.setLeading(35f);
            contentStream.showText("Vizsga Eredmény");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(dejaVuSansFont, mainFontSize);
            contentStream.newLineAtOffset(50, 650);
            contentStream.setLeading(35f);
            for (String line:stringList) {
                contentStream.showText(line);
                contentStream.newLine();
            }
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(dejaVuSansFont, footerFontSize);
            contentStream.newLineAtOffset(50, 80);
            contentStream.setLeading(14f);
            contentStream.showText("Kérjük örizze meg a tanusítványt ameddig a vizsgán elért jegy helyesen rögzítésre nem kerül a tanulmányi rendszerben.");
            contentStream.newLine();
            contentStream.showText("Ez a tanusítvány igazolja az fent említett hallgató viszgán való részvételét és a vizsgán elért eredményét.");
            contentStream.close();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            document.save(os);
            return os.toByteArray();

        }catch (Exception e){
            System.out.println("Caught exception :" + e);
        }
        return null;
    }
}
