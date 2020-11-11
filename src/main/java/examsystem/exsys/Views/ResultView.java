package examsystem.exsys.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.style.Color;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.vaadin.olli.FileDownloadWrapper;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Route(value = "result", layout = MainTemplateView.class)
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

    public ResultView() throws ParseException, TransformerException, ParserConfigurationException {
        setId("result-view");
        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();
        wrapper.setWidth("90%");
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        createTitle(wrapper,"Eredmény");
        createParagraph(wrapper, "Vizsgázó neve: ", studentName);
        createParagraph(wrapper, "Vizsgázó neptun kódja: ", studentNeptun);
        createParagraph(wrapper, "Vizsgázó email címe: ", studentEmail);
        createParagraph(wrapper, "Vizsga tárgya, neve: ", examSubject, "-", examName);
        createParagraph(wrapper, "Vizsga dátuma:", examDate.toString());
        createParagraph(wrapper, "Oktató neve: ", teacherName);
        createParagraph(wrapper, "Vizsga eredmény azonosítója: ", examResultId);
        createParagraph(wrapper, "Maximum elérhető pontok / Elért pontok: ", maxPoints, "/", attainedPoints);
        createParagraph(wrapper, "Elért jegy: ", attainedMark);

        Paragraph paragraph1 = new Paragraph("A fenti tanusítványt kérjük töltse le az alábbi gombra kattintva:");
        wrapper.add(paragraph1);

        Button download = new Button("Tanusítvány letöltése");
        download.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(
//                new StreamResource("foo.txt", () -> new ByteArrayInputStream("foo.txt".getBytes())));
                new StreamResource(String.valueOf(examResultId).concat(".pdf"), () -> {
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

        stringList.add("Vizsgázó neve: " + studentName);
        stringList.add("Vizsgázó emailcíme: " + studentEmail);
        stringList.add("Vizsga tárgya, neve: " + examSubject + " - " + examName);
        stringList.add("Vizsga dátuma: " + examDate);
        stringList.add("Oktató neve: " + teacherName);
        stringList.add("Vizsga eredmény azonosítója: " + examResultId);
        stringList.add("Maximum elérhető pontok / Elért pontok: " + maxPoints + " / " + attainedPoints);
        stringList.add("Elért jegy: " + attainedMark);

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
