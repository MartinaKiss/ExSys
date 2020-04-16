package examsystem.exsys.views.contact;

import com.vaadin.flow.component.Component;
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
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.views.main.MainView;

@Route(value = "contact", layout = MainView.class)
@PageTitle("Kapcsolat")
@CssImport("styles/views/kapcsolat/kapcsolat-view.css")
public class ContactView extends Div {

    private TextField fullName = new TextField();
    private TextField email = new TextField();
    private TextArea message = new TextArea();

    private Button send = new Button("Küldés");

    public ContactView() {
        setId("kapcsolat-view");
        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();

        createTitle(wrapper);
        createParagraph(wrapper, "Ha akármilyen problémája, kérdése merül fel, akkor érdeklődjön az alább megadott elérhetőségeken vagy küldjön nekünk üzenetet az adatlap kitöltésével.");
        createParagraph(wrapper,"Email: info@exsys.hu");
        createParagraph(wrapper,"Telefon: +36 30 556 8315");
        createFormLayout(wrapper);
        createButtonLayout(wrapper);
        wrapper.setWidth("90%");
        container.add(wrapper);
        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(container);
    }

    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Kapcsolat");
        wrapper.add(h1);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(false);
        return wrapper;
    }
    private void createParagraph(VerticalLayout wrapper, String text){
        Paragraph paragraph = new Paragraph(text);
        wrapper.add(paragraph);
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem nameFormItem = addFormItem(wrapper, formLayout,
                fullName, "Név");
        formLayout.setColspan(nameFormItem, 1);
        FormLayout.FormItem emailFormItem = addFormItem(wrapper, formLayout,
                email, "Email");
        formLayout.setColspan(emailFormItem, 1);
        FormLayout.FormItem messageFormItem = addFormItem(wrapper, formLayout,
                message, "Üzenet");
        formLayout.setColspan(messageFormItem, 2);
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(send);
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
